package com.friendlyrideshare.activities.carowner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.friendlyrideshare.R;
import com.friendlyrideshare.activities.LoginActivity;
import com.friendlyrideshare.activities.admin.AllVehiclesDetailsActivity;

public class CarOwnerDashboardActivity extends AppCompatActivity {
    Button btnUpdateProfile,btnTrackVehicles,btnAddVehicle,btnBookingRequest,btnViewVehicle,btnNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_owner_dashboard);


        getSupportActionBar().setTitle("Owner Home");
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarOwnerDashboardActivity.this,CarOwnerProfileActivity.class));
            }
        });
        btnAddVehicle = findViewById(R.id.btnAddVehicle);
        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarOwnerDashboardActivity.this,AddVehicleActivity.class));
            }
        });
        btnViewVehicle = findViewById(R.id.btnViewVehicle);
        btnViewVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarOwnerDashboardActivity.this,VehicleDetailsActivity.class));
            }
        });

        btnBookingRequest = findViewById(R.id.btnBookingRequest);
        btnBookingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarOwnerDashboardActivity.this,BookingRequestsActivity.class));
            }
        });
        btnTrackVehicles = findViewById(R.id.btnTrackVehicles);
        btnTrackVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AllVehiclesDetailsActivity.class));
            }
        });

        btnNotifications = findViewById(R.id.btnNotifications);
        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarOwnerDashboardActivity.this,OwnersNotificationActivity.class));
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.logout){
            Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(logout);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}