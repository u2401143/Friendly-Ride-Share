package com.friendlyrideshare.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.activities.carowner.StartRideMapsActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.databinding.ActivityAddVehicleLocationMapsBinding;
import com.friendlyrideshare.model.NotifiyRadiusData;
import com.friendlyrideshare.model.VehicleTrackingData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackVehicleActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat=16.515099,lng=80.632095;
    private ActivityAddVehicleLocationMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddVehicleLocationMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        binding.btnConfirmLoc.setVisibility(View.GONE);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near East London.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //51.54533471115648, 0.009689958867871074
        // Add a marker in Sydney and move the camera
        //51.54533471115648, 0.009689958867871074
        LatLng defaultLoc = new LatLng(51.54533471115648, 0.009689958867871074);
        mMap.addMarker(new MarkerOptions().position(defaultLoc).title("Marker in East London"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLoc));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 16.0f));
        callEvery10Seconds();
       /* mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng newLoc)
            {
                lat = newLoc.latitude;
                lng = newLoc.longitude;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(newLoc).title("Marker in london"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 16.0f));
            }
        });*/
        /*double lng = Double.parseDouble(getIntent().getStringExtra("lng"));
        double lat = Double.parseDouble(getIntent().getStringExtra("lat"));

        LatLng TutorialsPoint = new LatLng(lng, lat);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12.0f));

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(getIntent().getStringExtra("sname"))
                .snippet("You have to Pay:"+getIntent().getStringExtra("fee")));*/
    }
    Timer _Request_Trip_Timer;
    private void callEvery10Seconds(){
        Timer _Request_Trip_Timer = new Timer();
        _Request_Trip_Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getLatestPosition();
            }
        }, 15000, 40000);
    }
    ProgressDialog pd;
    public  void getLatestPosition()
    {

        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<List<VehicleTrackingData>> call = apiService.getVehicleTracking(getIntent().getStringExtra("vehicle_number_plate"));
        call.enqueue(new Callback<List<VehicleTrackingData>>() {
            @Override
            public void onResponse(Call<List<VehicleTrackingData>> call, Response<List<VehicleTrackingData>> response) {

                if (response.body()!=null&&response.body().size()>0) {
                    LatLng newLoc = new LatLng(Double.parseDouble(response.body().get(0).lat),Double.parseDouble(response.body().get(0).lng));
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(newLoc).title("Marker in East London"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 16.0f));
                    //Toast.makeText(TrackVehicleActivity.this,start_lat+" "+ start_lng+ " "+notify_radius,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<VehicleTrackingData>> call, Throwable t) {
                Toast.makeText(TrackVehicleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _Request_Trip_Timer.cancel();
    }
}