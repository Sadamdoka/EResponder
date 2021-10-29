package com.esafeafrica.eresponder.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.eresponder.Adaptors.eventAdaptor;
import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Config.ProgressSync;
import com.esafeafrica.eresponder.Model.Emergency;
import com.esafeafrica.eresponder.Model.EmergencyList;
import com.esafeafrica.eresponder.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.eresponder.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ServerError;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Events extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private eventAdaptor adaptor;
    private ArrayList<Emergency> emergencyArrayList;
    private Context context;

    public Events() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Events.
     */
    // TODO: Rename and change types and number of parameters
    public static Events newInstance() {
        Events fragment = new Events();
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
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_events, container, false);
        InitView(rootview);
        return rootview;
    }

    public void InitView(View view) {
        recyclerView = view.findViewById(R.id.event_list);
        context = getActivity();
        progressSync = new ProgressSync(context);
        loadItems();
    }

    private void loadItems() {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<EmergencyList> emergencyListCall = apiInterface.allEmergency();
        emergencyListCall.enqueue(new Callback<EmergencyList>() {
            @Override
            public void onResponse(Call<EmergencyList> call, Response<EmergencyList> response) {
                if (response.isSuccessful()) {
                    progressSync.hideDialog();
                    emergencyArrayList = response.body().getEmergency();
                    //progressSync.hideDialog();

                    adaptor = new eventAdaptor(getActivity(), emergencyArrayList, new eventAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Emergency emergency) {
                            Log.d("Item Clicked", emergency.getName());
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    // recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adaptor);
                } else {
                    progressSync.hideDialog();
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Events", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<EmergencyList> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }

}
