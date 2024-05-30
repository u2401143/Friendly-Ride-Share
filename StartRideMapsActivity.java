package com.friendlyrideshare.activities.carowner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.NotifiyRadiusData;
import com.friendlyrideshare.model.ResponseData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.friendlyrideshare.databinding.ActivityStartRideMapsBinding;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartRideMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityStartRideMapsBinding binding;

 //   double lat=16.515099,lng=80.632095;
 double lat=51.54533471115648,lng=0.009689958867871074;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartRideMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getRadius();
    }


    AlertDialog.Builder builder;
    LatLng defaultLoc;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

         defaultLoc = new LatLng(51.54533471115648, 0.009689958867871074);
        mMap.addMarker(new MarkerOptions().position(defaultLoc).title("Marker in East London"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLoc));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 16.0f));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng newLoc)
            {
                lat = newLoc.latitude;
                lng = newLoc.longitude;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(newLoc).title("Marker in East London"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 16.0f));
                addVehicle();
                double dis = distanceBetween(defaultLoc,newLoc);
                if(Double.parseDouble(notify_radius+"")<dis){
                    showDialog();
                }

                //Toast.makeText(StartRideMapsActivity.this, "==>"+dis, Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("You crossed geofench line ?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addGeofenchCross();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert!");
        alert.show();
    }
    public static Double distanceBetween(LatLng point1, LatLng point2) {
        if (point1 == null || point2 == null) {
            return null;
        }

        return SphericalUtil.computeDistanceBetween(point1, point2)/1000;
    }
    ProgressDialog pd;
    public  void addVehicle()
    {
        pd= new ProgressDialog(StartRideMapsActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<ResponseData> call = apiService.addVehicleTracking(getIntent().getStringExtra("vehicle_number_plate"),""+lat,""+lng);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(StartRideMapsActivity.this, response.body().message, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(StartRideMapsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(StartRideMapsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }

    public  void addGeofenchCross()
    {
        pd= new ProgressDialog(StartRideMapsActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<ResponseData> call = apiService.addGeofenchCrossPoint(getIntent().getStringExtra("vehicle_number_plate"),""+lat,""+lng,sharedPreferences.getString("uname","-"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    //Toast.makeText(StartRideMapsActivity.this, response.body().message, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(StartRideMapsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(StartRideMapsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }

    double start_lat=0.0,start_lng=0.0;
    int notify_radius = 0;
    public  void getRadius()
    {
        pd= new ProgressDialog(StartRideMapsActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<List<NotifiyRadiusData>> call = apiService.getNotifyRadius(getIntent().getStringExtra("vehicle_number_plate"));
        call.enqueue(new Callback<List<NotifiyRadiusData>>() {
            @Override
            public void onResponse(Call<List<NotifiyRadiusData>> call, Response<List<NotifiyRadiusData>> response) {
                pd.dismiss();
                if (response.body()!=null&&response.body().size()>0) {
                    start_lat = Double.parseDouble(response.body().get(0).lat);
                    start_lng = Double.parseDouble(response.body().get(0).lng);
                    notify_radius = Integer.parseInt(response.body().get(0).notify_radius);
                    Toast.makeText(StartRideMapsActivity.this,start_lat+" "+ start_lng+ " "+notify_radius,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<NotifiyRadiusData>> call, Throwable t) {
                Toast.makeText(StartRideMapsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
}