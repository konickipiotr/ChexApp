package com.chex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chex.authentication.AuthenticationHandler;
import com.google.android.gms.location.FusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

    public EditText username, password;
    public Button signinBtn, registerBtn, forgotPassBtn;
    public TextView error_message;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSION_FINE_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        init();




        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.i("eeeee", "errrrprrr");
        }else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }

        String login = "user1";
        String pass = "11";
        new AuthenticationHandler(this).execute(login, pass);


        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);




        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                }
        }
    }

//
//    private final int REQUEST_LOCATION_PERMISSION = 1;
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case PERMISSION_FINE_LOCATION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                } else {
//                    Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//        }
//    }
//
//
    private void init() {
        username = findViewById(R.id.login_username_input);
        password = findViewById(R.id.login_password_input);
        signinBtn = findViewById(R.id.login_signin_btn);
        error_message = findViewById(R.id.login_error_message);
//        gpsBtn = findViewById(R.id.gps_btn);
//        gpsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Location location = null;
//                LocationCatcher ll = new LocationCatcher(MainActivity.this);
//                ll.startLocationUpdates();
//                //ll.mm();
//            }
//        });


        signinBtn.setOnClickListener(v -> {

            String login = username.getText().toString();
            String pass = password.getText().toString();
            new AuthenticationHandler(this).execute(login, pass);
        });
//
//        gpsBtn = findViewById(R.id.gps_btn);
//        gpsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("xxxx","xxxxxxxxxxxxxxxx");
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                Log.i("yyy","yyyyyyyyyyyyyyy");
//                fusedLocationClient.getLastLocation()
//                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
//                            @Override
//                            public void onSuccess(Location location) {
//                                Log.i("ddd", "dfdfdfdf " + location);
//                                // Got last known location. In some rare situations this can be null.
//                                if (location != null) {
//                                    Log.i("xxxx", "lat: " + location.getLatitude());
//                                }
//                            }
//                        });
//            }
//        });
    }
}