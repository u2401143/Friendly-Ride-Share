package com.friendlyrideshare.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.friendlyrideshare.R;
import com.friendlyrideshare.activities.LoginActivity;

public class AdminDashboardActivity extends AppCompatActivity {
    Button btnRegisteredCarOwners,btnRegisteredTourists,btnComplaints,btnTrackVehicles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        getSupportActionBar().setTitle("Admin Home");
        btnRegisteredCarOwners = findViewById(R.id.btnRegisteredCarOwners);
        btnRegisteredTourists = findViewById(R.id.btnRegisteredTourists);
        btnRegisteredCarOwners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterdCarOwnersActivity.class));
            }
        });
        btnRegisteredTourists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisteredTouristsActivity.class));

            }
        });

        btnComplaints = findViewById(R.id.btnComplaints);
        btnComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ComplaintsActivity.class));
            }
        });
        btnTrackVehicles = findViewById(R.id.btnTrackVehicles);
        btnTrackVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AllVehiclesDetailsActivity.class));
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