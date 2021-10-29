package com.esafeafrica.eresponder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Config.ProgressSync;
import com.esafeafrica.eresponder.Model.Cases;
import com.esafeafrica.eresponder.Model.CasesSingle;
import com.esafeafrica.eresponder.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.eresponder.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ServerError;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ZeroReturn;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CasesFragment extends Fragment {

    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private TextView covidcase, recovery, death, active, last;

    public CasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cases, container, false);
        Init(view);
        return view;
    }

    private void Init(View view) {
        covidcase = view.findViewById(R.id.covid_case);
        recovery = view.findViewById(R.id.covid_reco);
        death = view.findViewById(R.id.covid_dead);
        active = view.findViewById(R.id.covid_act);
        //last=view.findViewById(R.id.covid_last);
        getCases();

    }

    private void setValues(Cases cases) {
        covidcase.setText(cases.getCase());
        recovery.setText(cases.getRecovery());
        death.setText(cases.getDead());
        active.setText(String.valueOf(Integer.parseInt(cases.getCase()) - Integer.parseInt(cases.getRecovery())));
        // last.setText(cases.getLastUpdate());

    }

    private void getCases() {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<CasesSingle> singleCall = apiInterface.getCases("1");
        singleCall.enqueue(new Callback<CasesSingle>() {
            @Override
            public void onResponse(Call<CasesSingle> call, Response<CasesSingle> response) {
                if (response.isSuccessful()) {
                    CasesSingle casesSingle = response.body();
                    if (casesSingle.getCases().getCase() != null) {
                        setValues(casesSingle.getCases());
                    } else {
                        ZeroReturn(getContext(), getLayoutInflater());
                    }
                } else {
                    ServerError(getContext(), getLayoutInflater());
                }
            }

            @Override
            public void onFailure(Call<CasesSingle> call, Throwable t) {
                NoInternet(getActivity(), getLayoutInflater());
            }
        });

    }
}
