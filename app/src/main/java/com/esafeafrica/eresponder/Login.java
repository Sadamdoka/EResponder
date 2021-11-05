package com.esafeafrica.eresponder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.esafeafrica.eresponder.Adaptors.mainFragmentAdaptor;
import com.esafeafrica.eresponder.Fragments.Signin;
import com.esafeafrica.eresponder.Fragments.Signup;


public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitViews();
    }

    public void InitViews() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        mainFragmentAdaptor pagerAdapter = new mainFragmentAdaptor(getSupportFragmentManager());
        pagerAdapter.addFragmet(new Signin());
        //pagerAdapter.addFragmet(new Signup());
        viewPager.setAdapter(pagerAdapter);
    }

}
