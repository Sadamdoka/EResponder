package com.esafeafrica.eresponder.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Config.ProgressSync;
import com.esafeafrica.eresponder.Config.SessionManager;
import com.esafeafrica.eresponder.MainActivity;
import com.esafeafrica.eresponder.Model.Feedback;
import com.esafeafrica.eresponder.Model.User;
import com.esafeafrica.eresponder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.eresponder.Config.ErrorMgt.IncorrectDetails;
import static com.esafeafrica.eresponder.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ServerError;
import static com.esafeafrica.eresponder.Config.Validation.isFieldValid;
import static com.esafeafrica.eresponder.Config.Validation.isPasswordValid;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Signin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class
Signin extends Fragment {

    SessionManager session;
    private FirebaseAuth auth;
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private LinearLayout linearLayout;
    private Context context;
    private EditText email, password;
    private Button confirm;

    public Signin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Signin.
     */
    // TODO: Rename and change types and number of parameters
    public static Signin newInstance() {
        Signin fragment = new Signin();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_signin, container, false);

        InitView(root);
        return root;
    }

    private void InitView(View item) {
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        context = getActivity();
        session = new SessionManager(context);
        email = item.findViewById(R.id.et_email);
        password = item.findViewById(R.id.et_password);
        confirm = item.findViewById(R.id.btn_login);
        progressSync = new ProgressSync(context);

        if (auth.getCurrentUser() != null) {
            // startActivity(new Intent(this, MainActivity.class));
            //();
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
                //gotoMain();
            }
        });
    }

    private void Signin() {
        String mail = null, pass = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mail = Objects.requireNonNull(email.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pass = Objects.requireNonNull(password.getText()).toString();
        }
        int err = 0;
        if (!isFieldValid(mail)) {
            err++;
            email.setError("Invalid Email or Number");
        }
        if (!isPasswordValid(pass)) {
            err++;
            password.setError("Password too short");
        }
        if (err == 0) {
            //user = new User(null, null, null, mail, null, pass, null, mail, null);
            //Account account=new Account(null,null,mail,null,pass,mail,null,null,null);
            User responder = new User(mail, pass);
            Authentication(responder);
        }
    }

    private void Authentication(User responder) {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody emailreq = RequestBody.create(MultipartBody.FORM, responder.getEmail());
        RequestBody passreq = RequestBody.create(MultipartBody.FORM, responder.getPassword());
        Call<Feedback> call = apiInterface.Authenticate(emailreq, passreq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        progressSync.hideDialog();
                        //Log.d("Success", "Client Successs");
                        session.createLoginSession(responder.getEmail(), responder.getPassword());
                        // createFirebase(responder.getEmail(),responder.getPassword());
                        gotoMain(responder);
                    } else {
                        progressSync.hideDialog();
                        Log.d("Error", feedback.getErrorMsg());
                        IncorrectDetails(getContext(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressSync.hideDialog();
                    Log.d("Error", "Error Server");
                    ServerError(getContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {

                progressSync.hideDialog();
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

    public void gotoMain(User responder) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("responder", responder);
        startActivity(intent);
    }

    public void createFirebase(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Sign In Successfull", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getActivity(), MainActivity.class));
                    //finish();
                }
            }
        });
    }

}
