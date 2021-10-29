package com.esafeafrica.eresponder.Fragments;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Model.Emergency;
import com.esafeafrica.eresponder.Model.Feedback;
import com.esafeafrica.eresponder.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomAlert implements GoogleMap.InfoWindowAdapter {

    private ApiInterface apiInterface;
    private Context context;
    private CircleImageView circleImageView;
    private TextView name, number, email, addrres;
    private Button clear;
    //private MapBean mapBean;


    public CustomAlert(Context context) {
        this.context = context;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.custom_alert, null);
        //circleImageView = view.findViewById(R.id.logo);
        name = view.findViewById(R.id.alert_nam);
        number = view.findViewById(R.id.alert_num);
        email = view.findViewById(R.id.alert_mail);
        clear = view.findViewById(R.id.alert_button);
        //addrres = view.findViewById(R.id.address);

        Emergency emergency = (Emergency) marker.getTag();
        name.setText(emergency.getName());
        email.setText(emergency.getTopic());
        number.setText(emergency.getEvent());
        //circleImageView.setImageBitmap(ConvertImage(amnesty.getPic()));
        //number.setText(amnesty.());
        //email.setText(amnesty.getEmail());
        //addrres.setText(owners.getAddress());
        return view;
    }

    private void clearEmergency(Emergency emergency) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<Feedback> call = apiInterface.clearActive(emergency.getId());
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {

                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        Log.d("Success", "Client Successs");
                    } else {
                        Log.d("Error", feedback.getErrorMsg());
                        //IncorrectDetails(g, getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Error", "Error Server");
                    //ServerError(getApplicationContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                // NoInternet(getApplicationContext(), getLayoutInflater());
            }
        });
    }
}
