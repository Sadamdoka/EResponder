package com.esafeafrica.eresponder.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Config.ProgressSync;
import com.esafeafrica.eresponder.Model.Corona;
import com.esafeafrica.eresponder.Model.CoronaList;
import com.esafeafrica.eresponder.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.eresponder.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.eresponder.Config.Validation.toDouble;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Corona_Map extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private ProgressSync progressSync;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private ArrayList<Corona> coronaList;
    private Geocoder geocoder;
    private ApiInterface apiInterface;
    private EditText txtsearch;
    private ImageButton btnsearch;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private Marker mCurrLocationMarker;

    public Corona_Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_corona__map, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.cor_map_frag);
        // MapFragment mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);
        init(view);
        return view;
    }

    private void init(View view) {
        txtsearch = view.findViewById(R.id.cor_txt_search);
        btnsearch = view.findViewById(R.id.cor_btn_search);
        progressSync = new ProgressSync(getContext());
        btnsearch.setOnClickListener(v -> onMapSearch(txtsearch.getText().toString()));

    }

    private void getCoronaAlerts() {

        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<CoronaList> coronaListCall = apiInterface.allCorona();
        coronaListCall.enqueue(new Callback<CoronaList>() {
            @Override
            public void onResponse(Call<CoronaList> call, Response<CoronaList> response) {
                if (response.isSuccessful()) {
                    //progressSync.hideDialog();
                    Log.d("Alerts Maps", "Alerts Success");
                    coronaList = response.body().getCorona();
                    loadAlerts(coronaList);
                } else {
                    NoInternet(getContext(), getLayoutInflater());
                }
            }

            @Override
            public void onFailure(Call<CoronaList> call, Throwable t) {
                NoInternet(getContext(), getLayoutInflater());
            }
        });
    }


    private void loadAlerts(ArrayList<Corona> coronas) {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
        for (int i = 0; i < coronas.size(); i++) {
            final LatLng lng = new LatLng(toDouble(coronas.get(i).getLati()), toDouble(coronas.get(i).getLongi()));//Lat & long needs to fixed at API Level
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(lng)
                    .title(coronas.get(i).getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.virus));
            CustomCorona customCorona = new CustomCorona(getContext());
            mMap.setInfoWindowAdapter(customCorona);
            Marker m = mMap.addMarker(markerOptions);
            m.setTag(coronas.get(i));
            //m.showInfoWindow();
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(lng));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //getAddresses();
        //getAlerts();

        getCoronaAlerts();

        enableMyLocationIfPermitted();
        //mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        /**
         //addMarkers();
         //loadClient(getClient());
         try {
         enableMyLocationIfPermitted();
         } catch (IOException e) {
         e.printStackTrace();
         }
         mMap.setOnMyLocationClickListener(this);
         mMap.setOnMyLocationButtonClickListener(this);
         mMap.getUiSettings().setZoomControlsEnabled(true);
         mMap.getUiSettings().setRotateGesturesEnabled(true);
         */
    }

    /**
     * private Client getClient() {
     * Client client = new Client();
     * if (getArguments() != null) {
     * client = getArguments().getParcelable("client");
     * return client;
     * }
     * return client;
     * }
     **/


    private void onMapSearch(String loc) {
        List<Address> addressList = null;
        if (loc != null || !loc.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(loc, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Searched"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        }
    }


    private void enableMyLocationIfPermitted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (permissions.length == 1 && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //loadClient(getClient());
                enableMyLocationIfPermitted();

            } else {
                showDefaultLocation();
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(getContext(), "You", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current Location  is" + location, Toast.LENGTH_SHORT).show();
        mMap.setMinZoomPreference(12);
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(location.getLatitude(), location.getLongitude()));
        circleOptions.radius(200);
        circleOptions.fillColor(Color.RED);
        circleOptions.strokeColor(6);
        mMap.addCircle(circleOptions);
    }

    private void showDefaultLocation() {
        Toast.makeText(getContext(), "Location permission not granted showing default Location", Toast.LENGTH_SHORT).show();
        LatLng lng = new LatLng(0.330712, 32.606151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lng));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        //mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }
    }
}
