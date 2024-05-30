package com.friendlyrideshare.activities.tourists;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.activities.carowner.OwnersNotificationActivity;
import com.friendlyrideshare.activities.carowner.VehicleDetailsActivity;
import com.friendlyrideshare.activities.carowner.adapters.OwnerNotificationAdapter;
import com.friendlyrideshare.activities.carowner.adapters.VehiclesDetailsAdapter;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.GeoCrossPoint;
import com.friendlyrideshare.model.VehicledData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TouristsNotificationsActivity extends AppCompatActivity {
    ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourists_notifications);
        list_view = findViewById(R.id.list_view);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        serverData();
    }
    public void refresh(){
        serverData();
    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(TouristsNotificationsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<List<GeoCrossPoint>> call = service.getTouristsGeofenchCrossPoints(sharedPreferences.getString("uname","-"));
        call.enqueue(new Callback<List<GeoCrossPoint>>() {
            @Override
            public void onResponse(Call<List<GeoCrossPoint>> call, Response<List<GeoCrossPoint>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(TouristsNotificationsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    //getAllCars = response.body();
                    list_view.setAdapter(new OwnerNotificationAdapter(response.body(), TouristsNotificationsActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<GeoCrossPoint>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TouristsNotificationsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}