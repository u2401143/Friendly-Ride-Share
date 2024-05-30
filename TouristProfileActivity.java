package com.friendlyrideshare.activities.tourists;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.activities.LoginActivity;
import com.friendlyrideshare.activities.carowner.CarOwnerProfileActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.EditProfilePojo;
import com.friendlyrideshare.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TouristProfileActivity extends AppCompatActivity {
    EditText et_first_name,et_last_name,et_email,et_phone_no,et_password;
    Button bt_update;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_profile);
        getSupportActionBar().setTitle("Tourist Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_first_name=(EditText)findViewById(R.id.et_first_name);
        et_last_name=(EditText)findViewById(R.id.et_last_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone_no=(EditText)findViewById(R.id.et_phone_no);
        et_password=(EditText)findViewById(R.id.et_password);
        bt_update=(Button) findViewById(R.id.bt_update);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_first_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "First Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_last_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Last Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_phone_no.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Phone Number Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_phone_no.getText().toString().length()!=10) {
                    Toast.makeText(getApplicationContext(), "Please enter 10 digit Phone Number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!et_email.getText().toString().matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid emailID.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitTouristProfile();
            }
        });
        serverData();
    }
    ProgressDialog pd;
    public  void submitTouristProfile()
    {
        pd= new ProgressDialog(TouristProfileActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.touristsUpdateProfile(et_first_name.getText().toString(),et_last_name.getText().toString(),et_phone_no.getText().toString(),et_email.getText().toString(),et_password.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(TouristProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    finish();
                } else {
                    Toast.makeText(TouristProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(TouristProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(TouristProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<List<EditProfilePojo>> call = service.getTouristsProfile(sharedPreferences.getString("uname","-"));

        call.enqueue(new Callback<List<EditProfilePojo>>() {
            @Override
            public void onResponse(Call<List<EditProfilePojo>> call, Response<List<EditProfilePojo>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(TouristProfileActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    if(response.body()!=null && response.body().size()>0){
                        EditProfilePojo profile = response.body().get(0);
                        et_first_name.setText(profile.getFirst_name());
                        et_last_name.setText(profile.getLast_name());
                        et_email.setText(profile.getEmailid());
                        et_phone_no.setText(profile.getPhonenumber());
                        et_password.setText(profile.getPwd());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EditProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TouristProfileActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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