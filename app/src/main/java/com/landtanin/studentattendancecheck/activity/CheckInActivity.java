package com.landtanin.studentattendancecheck.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;
import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.ActivityCheckInBinding;
import com.landtanin.studentattendancecheck.util.TodayModule;

import io.realm.RealmResults;

public class CheckInActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ActivityCheckInBinding b;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private double douMyLat, moduleLat, douMyLng, moduleLng;

    private LatLngBounds checkInBounds;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_check_in);


        initInstance();

//        if (savedInstanceState==null) {
//
//            LocationFragment locationFragment = LocationFragment.newInstance();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.checkInContentContainer,
//                            locationFragment,
//                            "LocationFragment")
//                    .commit();
//
//            FaceRecogFragment faceRecogFragment = FaceRecogFragment.newInstance();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.checkInContentContainer,
//                            faceRecogFragment,
//                            "FaceRecogFragment")
//                    .detach(faceRecogFragment)
//                    .commit();
//
//
//
//        }


    }

    private void initInstance() {

        setSupportActionBar(b.checkInActivityToolbar);

        TodayModule todayModule = new TodayModule();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

        moduleLat = studentModuleDao.get(0).getLocLat();
        moduleLng = studentModuleDao.get(0).getLocLng();

//        LatLng moduleLocation = new LatLng(moduleLat, moduleLng);

//        checkInBounds = toBounds(moduleLocation, 300);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        b.clickToAddModuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.w("MY_LAT", String.valueOf(douMyLat));
                Log.w("MY_LNG", String.valueOf(douMyLng));
                Log.w("MO_LAT", String.valueOf(moduleLat));
                Log.w("MO_LNG", String.valueOf(moduleLng));

                LatLng moduleLocation = new LatLng(moduleLat, moduleLng);
                LatLng studentLocation = new LatLng(douMyLat, douMyLng);
                checkInBounds = toBounds(moduleLocation, 432);
                // 432 is the smallest radius from house to Coates

                Log.w("module Location", String.valueOf(moduleLocation));
                Log.w("student Location", String.valueOf(studentLocation));

//                if (checkInBounds.contains(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))) {
                if (checkInBounds.contains(studentLocation)) {

                    Toast.makeText(CheckInActivity.this, "INBOUND", Toast.LENGTH_SHORT).show();

                    // TODO: pop up you're in the right place
                    initiatePopupWindow();


                } else {

                    Toast.makeText(CheckInActivity.this, "OUTBOUND", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void initiatePopupWindow() {

        LayoutInflater inflater = (LayoutInflater) CheckInActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.popup, (ViewGroup) findViewById(R.id.popupElement));
        layout.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_fade_in));

        mPopupWindow = new PopupWindow(layout, 800, 1200, true);

//        mPopupWindow.setAnimationStyle();
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);


//        View parent = inflater.inflate(R.layout.activity_check_in, null);
//        mPopupWindow.setAnimationStyle(R.anim.zoom_fade_in);
        mPopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckInActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    // we define this ourself
    public LatLngBounds toBounds(LatLng center, double radius) {

        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);

        Log.w("southwest", String.valueOf(southwest));
        Log.w("northeast", String.valueOf(northeast));

        // return the boundary
        return new LatLngBounds(southwest, northeast);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.w("LOCATION", "CONNECTED");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling for permission request
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.w("LOCATION", "permissions request");
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);


        if (mLastLocation != null) {

            douMyLat = mLastLocation.getLatitude();
            douMyLng = mLastLocation.getLongitude();

        }

        Log.d("MyLatLng", String.valueOf(douMyLat) + " " + douMyLng);

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.w("LOCATION", "SUSPENDED");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.w("LOCATION", "FAILED");

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
