package com.esafeafrica.eresponder.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.MainActivity;
import com.esafeafrica.eresponder.Model.Emergency;
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
import static com.esafeafrica.eresponder.Config.General_Actions.sendNotfication;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement
 * to handle interaction events.
 * Use the {@link alertChooser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class alertChooser extends DialogFragment {
    MainActivity mainActivity;
    private Context context;
    private EditText details;
    private TextView name;
    private Button alert;
    private Spinner event, extra;
    private ApiInterface apiInterface;

    public alertChooser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment alertChooser.
     */
    // TODO: Rename and change types and number of parameters
    public static alertChooser newInstance() {
        alertChooser fragment = new alertChooser();
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
        View rootview = inflater.inflate(R.layout.fragment_alert_chooser, container, false);
        InitViews(rootview);
        return rootview;
    }

    private void InitViews(View view) {
        context = view.getContext();
        mainActivity = new MainActivity();
        name = view.findViewById(R.id.al_con_nam);
        event = view.findViewById(R.id.al_spi_event);
        extra = view.findViewById(R.id.al_spi_extra);
        details = view.findViewById(R.id.al_txt_det);
        alert = view.findViewById(R.id.al_btn_alert);
        ////Log.d("Emergency",getEmergency().getNames());
        setValues();
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emergencyAlert(setEmergency());
            }
        });
    }

    private Emergency getEmergency() {
        Emergency emergency = new Emergency();
        if (getArguments() != null) {
            emergency = getArguments().getParcelable("emergency");
            return emergency;
        }
        return emergency;
    }

    private void setValues() {
        //name.setText(getEmergency().getNames());
        String cond = getEmergency().getTopic();
        if (cond.equals("Police Alert")) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            event.setAdapter(adapter);
            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            extra.setAdapter(arrayAdapter);
        } else if (cond.equals("Health Alert")) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            event.setAdapter(adapter);
            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            extra.setAdapter(arrayAdapter);
        } else {
            return;
        }

    }

    public void emergencyAlert(Emergency emergency) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, emergency.getName());
        RequestBody latireq = RequestBody.create(MultipartBody.FORM, emergency.getLati());
        RequestBody longireq = RequestBody.create(MultipartBody.FORM, emergency.getLongi());
        RequestBody briefreq = RequestBody.create(MultipartBody.FORM, emergency.getTopic());
        RequestBody eventreq = RequestBody.create(MultipartBody.FORM, emergency.getEvent());
        RequestBody extrasreq = RequestBody.create(MultipartBody.FORM, emergency.getDetails());
        Call<Feedback> call = apiInterface.createEmergency(namereq, latireq, longireq, briefreq, eventreq, extrasreq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        sendNotfication(getContext(), emergency.getName(), emergency.getEvent());
                        dismiss();
                        Log.d("Success", "Client Successs");
                    } else {
                        Log.d("Error", feedback.getErrorMsg());
                        IncorrectDetails(getActivity(), getLayoutInflater());
                        dismiss();
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Error", "Error Server");
                    ServerError(getActivity(), getLayoutInflater());
                    dismiss();
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                NoInternet(getActivity(), getLayoutInflater());
                dismiss();
            }
        });
    }

    private Emergency setEmergency() {
        Emergency emergency = new Emergency();
        return emergency;
    }


}
