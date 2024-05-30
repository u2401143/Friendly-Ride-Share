package com.friendlyrideshare.activities.tourists;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.activities.admin.RegisteredTouristsActivity;
import com.friendlyrideshare.activities.admin.adapters.TouristsListAdapter;
import com.friendlyrideshare.activities.tourists.adapters.VehiclesListAdapter;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.EditProfilePojo;
import com.friendlyrideshare.model.VehicledData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllVehiclesActivity extends AppCompatActivity {
    ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_vehicles);
        list_view = findViewById(R.id.list_view);
        getSupportActionBar().setTitle("All Vehicles Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        serverData();
    }
    public void refresh(){
        serverData();
    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(ViewAllVehiclesActivity.this);
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
                    Toast.makeText(ViewAllVehiclesActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    //getAllCars = response.body();
                    list_view.setAdapter(new VehiclesListAdapter(response.body(), ViewAllVehiclesActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<VehicledData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewAllVehiclesActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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