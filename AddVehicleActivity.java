package com.friendlyrideshare.activities.carowner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.ResponseData;
import com.google.android.gms.vision.text.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleActivity extends AppCompatActivity {
    EditText et_vehicle_name,et_vehicle_number_plate,et_vehicle_model,et_notify_radius;
    Button bt_get_location,bt_update;
    TextView tv_get_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        getSupportActionBar().setTitle("Add Vehicle");
        et_vehicle_name = findViewById(R.id.et_vehicle_name);
        et_vehicle_number_plate = findViewById(R.id.et_vehicle_number_plate);
        et_vehicle_model = findViewById(R.id.et_vehicle_model);
        et_notify_radius = findViewById(R.id.et_notify_radius);
        tv_get_location = findViewById(R.id.tv_get_location);
        tv_get_location.setText("No Location Selected.");

        bt_get_location = findViewById(R.id.bt_get_location);
        bt_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddVehicleActivity.this, AddVehicleLocationMapsActivity.class),888);
            }
        });
        bt_update = findViewById(R.id.bt_update);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_vehicle_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vehicle Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_vehicle_number_plate.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vehicle Number Plate Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_vehicle_model.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vehicle Model Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_notify_radius.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Notify Radius Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tv_get_location.getText().toString().contains("Location")) {
                    Toast.makeText(getApplicationContext(), "Please select location", Toast.LENGTH_SHORT).show();
                    return;
                }
                addVehicle();
            }
        });
    }
    ProgressDialog pd;
    public  void addVehicle()
    {
        pd= new ProgressDialog(AddVehicleActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<ResponseData> call = apiService.addVehicleDetails(et_vehicle_name.getText().toString(),et_vehicle_number_plate.getText().toString(),et_vehicle_model.getText().toString(),lat,lng,et_notify_radius.getText().toString(),sharedPreferences.getString("uname","-"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(AddVehicleActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    finish();
                } else {
                    Toast.makeText(AddVehicleActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(AddVehicleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
    String lat="0.0",lng="0.0";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            lat = data.getStringExtra("lat");
            lng = data.getStringExtra("lng");
            tv_get_location.setText("Lat : "+lat +"  Lng : "+lng);
        }
    }
}