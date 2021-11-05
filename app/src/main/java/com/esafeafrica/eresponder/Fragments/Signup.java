package com.esafeafrica.eresponder.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;


import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Config.ProgressSync;
import com.esafeafrica.eresponder.Model.Feedback;
import com.esafeafrica.eresponder.Model.Organ;
import com.esafeafrica.eresponder.Model.OrganList;
import com.esafeafrica.eresponder.Model.OrganSingle;
import com.esafeafrica.eresponder.Model.User;
import com.esafeafrica.eresponder.R;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.eresponder.Config.ErrorMgt.DataError;
import static com.esafeafrica.eresponder.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ServerError;
import static com.esafeafrica.eresponder.Config.ErrorMgt.SuccessAccount;
import static com.esafeafrica.eresponder.Config.Validation.isFieldValid;
import static com.esafeafrica.eresponder.Config.Validation.isPasswordValid;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup extends Fragment {

    private ArrayList<Organ> organs;
    private ProgressSync progressSync;
    private EditText names, email, phone, pass, rpass;
    private Spinner spinner_organ;
    private Context context;
    private ApiInterface apiInterface;
    private Button create;
    private Organ organ_details;

    public Signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Signup.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup newInstance() {
        Signup fragment = new Signup();
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
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        InitView(root);
        return root;
    }

    private void InitView(View item) {
        context = getActivity();
        progressSync = new ProgressSync(context);
        names = item.findViewById(R.id.rt_name);
        email = item.findViewById(R.id.rt_email);
        phone = item.findViewById(R.id.rt_number);
        pass = item.findViewById(R.id.rt_pass);
        rpass = item.findViewById(R.id.rt_rpass);
        spinner_organ = item.findViewById(R.id.rt_organ);
        create = item.findViewById(R.id.btn_register);

        //getOrgan(new Organ("0", "null", "null", "3"));

        spinner_organ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("OrganID",organs.get(position).getOrganid());
                //getOrganSingle(new Organ("0", "null", organs.get(position).getOrganid(), "null"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String mail = null, password = null, rpassword = null, number = null, name = null, org = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mail = Objects.requireNonNull(email.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            number = Objects.requireNonNull(phone.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            name = Objects.requireNonNull(names.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            password = Objects.requireNonNull(pass.getText()).toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rpassword = Objects.requireNonNull(rpass.getText()).toString();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            org = Objects.requireNonNull(spinner_organ.getSelectedItem()).toString();
        }

        int err = 0;
        if (!isFieldValid(name)) {
            err++;
            names.setError("Check Your Names");
        }
        if (!isFieldValid(mail)) {
            err++;
            email.setError("Check Your Email");
        }
        if (!isFieldValid(number)) {
            err++;
            phone.setError("Check Your Number");
        }
        if (!isPasswordValid(password)) {
            err++;
            pass.setError("Password too short");
        }

        if (!isFieldValid(org)) {
            err++;
            rpass.setError("Password too short");
        }
        if (password.equals(rpassword)) {
            if (err == 0) {
                //Account account=new Account(null,null,mail,name,password,number,null,null,null);
                User responder = new User(null, null, null, mail, name, organ_details.getOrganid(), password, number, null, "2", "3");
                Create(responder);
            }
        } else {
            pass.setError("Passwords Dont Match");
            rpass.setError("Passwords Dont Match");
        }
    }

    private void getOrganSingle(Organ organ) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<OrganSingle> call = apiInterface.organSingle(organ.getId(), organ.getOrganid(), "organ.getType()", organ.getNames());
        call.enqueue(new Callback<OrganSingle>() {
            @Override
            public void onResponse(Call<OrganSingle> call, Response<OrganSingle> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        OrganSingle organSingle = response.body();
                        Log.d("Organ",organSingle.getOrgan().getNames());
                        //organ_details = new Organ(organSingle.getOrgan().getDatereg(), organSingle.getOrgan().getId(), organSingle.getOrgan().getStatus(), organSingle.getOrgan().getAddress(), organSingle.getOrgan().getEmail(), organSingle.getOrgan().getNames(), organSingle.getOrgan().getOrganid(), organSingle.getOrgan().getPhone(), "organSingle.getOrgan().getType()");
                    }
                } else {
                    ServerError(getContext(), getLayoutInflater());
                }
            }

            @Override
            public void onFailure(Call<OrganSingle> call, Throwable t) {

            }
        });
    }

    private void getOrgan(Organ organ) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<OrganList> organListCall = apiInterface.organ(organ.getId(), organ.getOrganid(), "organ.getType()", organ.getNames());
        organListCall.enqueue(new Callback<OrganList>() {
            @Override
            public void onResponse(Call<OrganList> call, Response<OrganList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //organs = response.body().getOrgan();
                        //progressSync.hideDialog();
                        organs = response.body().getOrgan();
                        ArrayList<String> organOne = new ArrayList<>();
                        for (int i = 0; i < organs.size(); i++) {
                            Log.d("ITEMS", organs.get(i).getNames());
                            organOne.add(organs.get(i).getNames());
                            //organOne.add(organs.get(i).getOrganid());
                        }
                        //organArrayList.add(response.body().getOrgan().get(2));
                        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, organOne);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_organ.setAdapter(arrayAdapter);
                    } else {
                        //recyclerView.setVisibility(View.GONE);
                        //EmptyList(getContext(),getLayoutInflater());
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add("No Organ Registered");
                        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_organ.setAdapter(arrayAdapter);
                    }
                } else {
                    spinner_organ.setVisibility(View.GONE);
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Organ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<OrganList> call, Throwable t) {
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

    private void Create(User responder) {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody orgreq = RequestBody.create(MultipartBody.FORM, responder.getOrgan());
        RequestBody typereq = RequestBody.create(MultipartBody.FORM, responder.getType());
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, responder.getName());
        RequestBody emailreq = RequestBody.create(MultipartBody.FORM, responder.getEmail());
        RequestBody phonereq = RequestBody.create(MultipartBody.FORM, responder.getPhone());
        RequestBody passreq = RequestBody.create(MultipartBody.FORM, responder.getPassword());
        RequestBody rolereq = RequestBody.create(MultipartBody.FORM, responder.getRole());

        Call<Feedback> call = apiInterface.createAccount(orgreq, typereq, namereq, emailreq, phonereq, passreq, rolereq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        progressSync.hideDialog();
                        //Log.d("Success", "Client Successs");
                        SuccessAccount(getContext(), getLayoutInflater(), responder.getEmail());
                    } else {
                        progressSync.hideDialog();
                        Log.d("Error", feedback.getErrorMsg());
                        DataError(getActivity(), getLayoutInflater());
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

    private Organ setOrgan(Organ organ) {
        return organ;
    }

}
