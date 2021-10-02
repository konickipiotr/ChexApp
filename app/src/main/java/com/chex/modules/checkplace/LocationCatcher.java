package com.chex.modules.checkplace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chex.Home;
import com.chex.R;
import com.chex.modules.checkplace.showreached.ShowReachedPlacesActivity;
import com.chex.utils.Coords;
import com.chex.utils.LoadingDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class LocationCatcher {

    private final Home activity;
    private final LocationRequest locationRequest;
    private final FusedLocationProviderClient fusedLocationClient;
    private final LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            LoadingDialog dialog = new LoadingDialog(activity);
            dialog.startLoadingDialog();
            Location lastLocation = locationResult.getLastLocation();
            Coords coords = new Coords(lastLocation);

            try {
                CheckPlaceResponse ret = new CheckPlaceAsync().execute(coords).get();
                dialog.dismissDialog();
                switch (ret.getResponseStatus()){
                    case FOUND:{
                        Intent intent = new Intent(activity, ShowReachedPlacesActivity.class);
                        intent.putExtra("places", (Serializable) ret.getCheckPlaceViewList());
                        intent.putExtra("achievements", (Serializable) ret.getAchievementShortViews());
                        activity.finish();
                        activity.startActivity(intent);
                    }break;
                    case NOTFOUND: Toast.makeText(activity.getApplicationContext(), R.string.there_is_no_place_in_area, Toast.LENGTH_LONG).show(); break;
                    case SERVERCONNECTIONERROR: Toast.makeText(activity.getApplicationContext(), R.string.server_connection_err, Toast.LENGTH_LONG).show();break;
                    case ALREADYEXISTS: Toast.makeText(activity.getApplicationContext(),"Już tu byłeś", Toast.LENGTH_LONG).show();break;
                    default:
                        Toast.makeText(activity.getApplicationContext(), R.string.unknown_error_occured, Toast.LENGTH_LONG).show();
                }

            }catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    };

    public LocationCatcher(Home activity) {
        super();
        this.activity = activity;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        locationRequest = LocationRequest
                .create()
                .setInterval(500)
                .setFastestInterval(500)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates(){
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }
}
