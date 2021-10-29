package com.esafeafrica.eresponder.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;


import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Model.Feedback;
import com.esafeafrica.eresponder.R;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.eresponder.Config.ErrorMgt.IncorrectDetails;
import static com.esafeafrica.eresponder.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ServerError;
import static com.esafeafrica.eresponder.Config.ErrorMgt.SuccessAccount;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MgAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MgAccount extends Fragment {
    private ApiInterface apiInterface;
    private EditText userid, name, mail, tel, pass, rpass;
    private Button edit;
    private ImageButton enable;
    private Context context;
    private String id;

    public MgAccount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MgAccount.
     */
    // TODO: Rename and change types and number of parameters
    public static MgAccount newInstance() {
        MgAccount fragment = new MgAccount();
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
        View rootview = inflater.inflate(R.layout.fragment_mg_account, container, false);
        InitView(rootview);
        return rootview;
    }

    public void InitView(View view) {
        context = view.getContext();
        userid = view.findViewById(R.id.edit_acc_id);
        name = view.findViewById(R.id.edit_acc_name);
        mail = view.findViewById(R.id.edit_acc_email);
        tel = view.findViewById(R.id.edit_acc_tel);
        pass = view.findViewById(R.id.edit_acc_pass);
        rpass = view.findViewById(R.id.edit_acc_rpass);
        edit = view.findViewById(R.id.btn_editaccount);
        enable = view.findViewById(R.id.img_btn_edit_acc);

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableStaff();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAccount(new Account());
            }
        });

    }


    private void enableStaff() {
        pass.setVisibility(View.VISIBLE);
        rpass.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
    }


    private void editAccount(Account account) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody idreq = RequestBody.create(MultipartBody.FORM, account.getId());
        RequestBody userreq = RequestBody.create(MultipartBody.FORM, account.getUserid());
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, account.getNames());
        RequestBody emailreq = RequestBody.create(MultipartBody.FORM, account.getEmail());
        RequestBody phonereq = RequestBody.create(MultipartBody.FORM, account.getPhone());
        RequestBody passreq = RequestBody.create(MultipartBody.FORM, account.getPassword());
        Call<Feedback> call = apiInterface.updateAccount(idreq, userreq, namereq, emailreq, phonereq, passreq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        //Log.d("Success", "Client Successs");
                        SuccessAccount(getContext(), getLayoutInflater(), account.getEmail());
                    } else {
                        Log.d("Error", feedback.getErrorMsg());
                        IncorrectDetails(getContext(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.d("Error", "Error Server");
                    ServerError(getContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

}
