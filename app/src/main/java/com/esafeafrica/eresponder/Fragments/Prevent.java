package com.esafeafrica.eresponder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.esafeafrica.eresponder.R;

import static com.esafeafrica.eresponder.Config.General_Actions.startWebView;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Prevent extends Fragment {
    private WebView web;

    public Prevent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prevent, container, false);
        Init(view);
        return view;
    }

    private void Init(View view) {
        web = view.findViewById(R.id.webprevent);
        //web.loadUrl("https://www.health.go.ug/covid/prevention/")
        startWebView(getActivity(), "https://www.health.go.ug/covid/prevention/", web);
    }

}
