package com.esafeafrica.eresponder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


import com.esafeafrica.eresponder.Config.SessionManager;
import com.esafeafrica.eresponder.Model.User;
import com.esafeafrica.eresponder.Notification.FCM_instance;

import java.util.HashMap;

public class Welcome extends AppCompatActivity {
    private static int Splash_time_out = 500;
    SessionManager session;
    FCM_instance fcm_instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        InitViews();
    }

    public void InitViews() {
        fcm_instance = new FCM_instance();
        session = new SessionManager(getApplicationContext());
        fcm_instance.subscribeTopicBasic(getApplicationContext());
        fcm_instance.Subscribe(getApplicationContext());
        fcm_instance.getToken(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPref();
            }
        }, Splash_time_out);

    }

    public void SharedPref() {
        session.checkLogin();
        if (!session.isLoggedIn()) {
            gotoLogin();
        } else {
            HashMap<String, String> userDetails = session.getUserDetails();
            User responder = new User(null, null, null, userDetails.get(SessionManager.KEY_EMAIL), null, null, userDetails.get(SessionManager.KEY_PASSWORD), null, null, null, null);
            //Log.d("email",user.getEmail());
            gotoMain(responder);
        }

    }

    public void gotoLogin() {
        Intent intent = new Intent(Welcome.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void gotoMain(User responder) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("responder", responder);
        startActivity(intent);
        finish();
    }
}
