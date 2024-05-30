package com.friendlyrideshare.activities.tourists;

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
import com.friendlyrideshare.activities.carowner.StartRideMapsActivity;
import com.friendlyrideshare.activities.tourists.adapters.VehiclesListAdapter;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.NotifiyRadiusData;
import com.friendlyrideshare.model.ResponseData;
import com.friendlyrideshare.model.VehicledData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.friendlyrideshare.databinding.ActivityStartRideMapsBinding;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ViewAllVehiclesMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityStartRideMapsBinding binding;

    double lat=16.515099,lng=80.632095;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartRideMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
    //vehicle_number_plate
    AlertDialog.Builder builder;
    LatLng defaultLoc;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //marker.getTag();
               // Toast.makeText(ViewAllVehiclesMapActivity.this,marker.getTag().toString(),Toast.LENGTH_LONG).show();
                //Using position get Value from arraylist
                int pos = Integer.parseInt(marker.getTag().toString());
                showCDialog(pos);
                return false;
            }
        });
        serverData();
    }
     void showCDialog(int pos){
        builder = new AlertDialog.Builder(this);
        String det = "Vehicle Name : "+data.get(pos).vehicle_name+"\n Vehicle Model : "+data.get(pos).vehicle_model+"\n"+"Vehicle Number Plate: "+data.get(pos).vehicle_number_plate;
        builder.setMessage(det+"\n\n Do you want to book this vehicle?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        book(data.get(pos));
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert!");
        alert.show();
    }
    public void book(VehicledData data){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<ResponseData> call = service.bookVehicle(data.vehicle_name,data.vehicle_number_plate,data.vehicle_model,data.rider_email,sharedPreferences.getString("uname","-"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ViewAllVehiclesMapActivity.this,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ViewAllVehiclesMapActivity.this,"Book Request has been sent to vehicle owner!",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewAllVehiclesMapActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ProgressDialog progressDialog;
    List<VehicledData> data;
    public void serverData(){
        progressDialog = new ProgressDialog(ViewAllVehiclesMapActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<VehicledData>> call = service.getAllVehicles();

        call.enqueue(new Callback<List<VehicledData>>() {
            @Override
            public void onResponse(Call<List<VehicledData>> call, Response<List<VehicledData>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ViewAllVehiclesMapActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    if(response.body()!=null&&response.body().size()>0){
                        data = response.body();
                        defaultLoc = new LatLng(Double.parseDouble(response.body().get(0).lat), Double.parseDouble(response.body().get(0).lng));
                        Marker m1 = mMap.addMarker(new MarkerOptions().position(defaultLoc).title("Marker in East London"));
                        m1.setTag(""+0);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLoc));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 16.0f));
                        for(int i=0;i<response.body().size();i++){
                            LatLng loc = new LatLng(Double.parseDouble(response.body().get(i).lat), Double.parseDouble(response.body().get(i).lng));
                            Marker m = mMap.addMarker(new MarkerOptions().position(loc).title("Vehicle No: "+response.body().get(i).vehicle_number_plate));
                            m.setTag(""+i);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<VehicledData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewAllVehiclesMapActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}