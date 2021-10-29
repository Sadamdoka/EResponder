package com.esafeafrica.eresponder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.esafeafrica.eresponder.Api.ApiInterface;
import com.esafeafrica.eresponder.Api.RetroClient;
import com.esafeafrica.eresponder.Config.ProgressSync;
import com.esafeafrica.eresponder.Config.SessionManager;
import com.esafeafrica.eresponder.Fragments.Corona_alert;
import com.esafeafrica.eresponder.Fragments.alertChooser;
import com.esafeafrica.eresponder.Model.Corona;
import com.esafeafrica.eresponder.Model.Emergency;
import com.esafeafrica.eresponder.Model.User;
import com.esafeafrica.eresponder.Model.UserSingle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.eresponder.Config.ErrorMgt.Aboutapp;
import static com.esafeafrica.eresponder.Config.ErrorMgt.Developer;
import static com.esafeafrica.eresponder.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ServerError;
import static com.esafeafrica.eresponder.Config.ErrorMgt.ZeroReturn;


public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    SessionManager session;
    int PERMISSION_ID = 44;
    private FirebaseAuth auth;
    private String latitude, longitude, names, email, tel_num;
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private AppBarConfiguration mAppBarConfiguration;
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
    }

    public void InitView() {
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //this.context=getApplicationContext();
        session = new SessionManager(getApplicationContext());


        //Getting account details
        //loadAccount(getResponder());
        //Signing in Firebase
        // if (auth.getCurrentUser() != null) {
        //createFirebase(getResponder().getEmail(),getResponder().getPassword());
        //}
        //Get Last Known Location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account, R.id.nav_events,
                R.id.nav_announce, R.id.nav_help, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    public void createFirebase(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Sign In Successfull", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getActivity(), MainActivity.class));
                    //finish();
                }
            }
        });
    }

    public User getResponder() {
        Bundle data = getIntent().getExtras();
        User responder = data.getParcelable("responder");
        return responder;
    }

    private Bundle setBasic(User responder) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("responder", responder);
        return bundle;
    }

    private void loadAccount(User responder) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<UserSingle> singleCall = apiInterface.getAccountbyEmail(responder.getEmail());
        singleCall.enqueue(new Callback<UserSingle>() {
            @Override
            public void onResponse(Call<UserSingle> call, Response<UserSingle> response) {
                if (response.isSuccessful()) {
                    UserSingle userSingle = response.body();
                    if (userSingle.getUser().getEmail() != null) {
                        names = userSingle.getUser().getName();
                        email = userSingle.getUser().getEmail();
                        tel_num = userSingle.getUser().getPhone();
                        setBasic(userSingle.getUser());
                    } else {
                        ZeroReturn(getApplicationContext(), getLayoutInflater());
                    }
                } else {
                    ServerError(getApplicationContext(), getLayoutInflater());
                }

            }

            @Override
            public void onFailure(Call<UserSingle> call, Throwable t) {
                NoInternet(getApplicationContext(), getLayoutInflater());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.cor_chat) {
            gotoChat(getResponder());
        }
        if (id == R.id.action_settings) {
            Aboutapp(getApplicationContext(), getLayoutInflater());
            return true;
        } else if (id == R.id.action_share) {
            Share();
            return true;
        } else if (id == R.id.action_developer) {
            Developer(getApplicationContext(), getLayoutInflater());
            return true;
        } else if (id == R.id.action_logout) {
            session.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void Share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hey try out this awesome apps");
        intent.putExtra(Intent.EXTRA_TEXT, "Url");
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = String.valueOf(location.getLatitude());
                                    longitude = String.valueOf(location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

    public void Notify(Context context, String title, String detail) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_uaera)
                .setContentTitle(title)
                .setContentText(detail)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //Add as Notifcation
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // manager.notify();
        manager.notify(001, builder.build());
    }

    private void CoronaAlert(Corona cor) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("corona", cor);
        FragmentManager manager = getSupportFragmentManager();
        Corona_alert corona = new Corona_alert();
        corona.setArguments(bundle);
        corona.show(manager, "CORONA VIRUS");

    }

    private void alertChooser(Emergency emergency) {
        Bundle bundle = new Bundle();
        // bundle.putParcelable("client", getClient());
        bundle.putParcelable("emergency", emergency);
        FragmentManager manager = getSupportFragmentManager();
        alertChooser selected = new alertChooser();
        selected.setArguments(bundle);
        selected.show(manager, "emergency");
    }

    public void gotoChat(User responder) {
        Intent intent = new Intent(getApplicationContext(), Chat.class);
        intent.putExtra("responder", responder);
        startActivity(intent);
    }

}
