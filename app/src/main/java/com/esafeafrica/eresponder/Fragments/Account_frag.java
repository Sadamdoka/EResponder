package com.esafeafrica.eresponder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.esafeafrica.eresponder.Adaptors.mainFragmentAdaptor;
import com.esafeafrica.eresponder.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Account_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    public Account_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided
     *
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account_frag newInstance() {
        Account_frag fragment = new Account_frag();
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
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        InitViews(root);
        return root;
    }

    public void InitViews(View item) {
        ViewPager viewPager = item.findViewById(R.id.accountpager);
        mainFragmentAdaptor pagerAdapter = new mainFragmentAdaptor(getFragmentManager());
        // pagerAdapter.addFragmet(new MgAccount());
        pagerAdapter.addFragmet(new Contacts());
        pagerAdapter.addFragmet(new AmnestyNo());
        viewPager.setAdapter(pagerAdapter);
    }


}
