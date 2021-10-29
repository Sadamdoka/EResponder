package com.esafeafrica.eresponder.Fragments;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Model.Corona;
import com.esafeafrica.eresponder.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.esafeafrica.eresponder.Config.Calculations.ToInt;


public class CustomCorona implements GoogleMap.InfoWindowAdapter {

    private ApiInterface apiInterface;
    private Context context;
    private CircleImageView circleImageView;
    private TextView name, number, email, addrres;
    private Button clear;
    //private MapBean mapBean;


    public CustomCorona(Context context) {
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

        Corona corona = (Corona) marker.getTag();
        name.setText(corona.getName());
        number.setText(Status(corona.getFever(), corona.getTired(), corona.getCough(), corona.getBreath(), corona.getSore(), corona.getPain(), corona.getNose()));
        email.setText(corona.getNumber() + TravelChecker(corona));
        //circleImageView.setImageBitmap(ConvertImage(amnesty.getPic()));
        //number.setText(amnesty.());
        //email.setText(amnesty.getEmail());
        //addrres.setText(owners.getAddress());
        return view;
    }

    private String TravelChecker(Corona corona) {
        String place = "";
        if (corona.getTravel().equals("1")) {
            return place = "\n Travelled:  " + corona.getWere();
        } else {
            return place;
        }
    }

    private String Status(String fever, String tire, String cough, String breath, String sore, String pain, String nose) {
        String a = "Low Alerts,No major Symptoms";
        if (ToInt(fever) != 0 && ToInt(tire) != 0 && ToInt(cough) != 0 && ToInt(breath) != 0 && ToInt(sore) != 0 && ToInt(pain) != 0 && ToInt(nose) != 0) {
            return a = "High Alert, All Symptoms";
        } else if (ToInt(fever) != 0 && ToInt(tire) != 0 && ToInt(cough) != 0) {
            return a = "Medium Alert,Major Symptoms";
        } else if (ToInt(cough) != 0 && ToInt(breath) != 0 && ToInt(sore) != 0 && ToInt(pain) != 0 && ToInt(nose) != 0) {
            return a = "Low Alert,Only Miner Symptoms";
        } else {
            return a;
        }
    }

}
