package com.friendlyrideshare.activities.tourists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.friendlyrideshare.R;
import com.friendlyrideshare.activities.LoginActivity;
import com.friendlyrideshare.activities.carowner.CarOwnerDashboardActivity;
import com.friendlyrideshare.activities.carowner.CarOwnerProfileActivity;

public class TouristDashboardActivity extends AppCompatActivity {
    Button btnRiseComplaint,btnUpdateProfile,btnViewVehicles,btnBookingHistory,btnMyRides,btnNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_dashboard);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        getSupportActionBar().setTitle("Tourist Dashboard");
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TouristDashboardActivity.this, TouristProfileActivity.class));
            }
        });
        btnViewVehicles = findViewById(R.id.btnViewVehicles);
        btnViewVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TouristDashboardActivity.this, ViewAllVehiclesMapActivity.class));
                //startActivity(new Intent(TouristDashboardActivity.this, ViewAllVehiclesActivity.class));
            }
        });
        btnBookingHistory = findViewById(R.id.btnBookingHistory);
        btnBookingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TouristDashboardActivity.this, MyBookingHistoryActivity.class));
            }
        });
        btnMyRides = findViewById(R.id.btnMyRides);
        btnMyRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TouristDashboardActivity.this, MyRidesActivity.class));
            }
        });
        btnNotifications = findViewById(R.id.btnNotifications);
        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TouristDashboardActivity.this, TouristsNotificationsActivity.class));
            }
        });
        btnRiseComplaint = findViewById(R.id.btnRiseComplaint);
        btnRiseComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TouristDashboardActivity.this, ViewAllVehiclesActivity.class));
            }
        });






        //
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