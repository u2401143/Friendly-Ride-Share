package com.friendlyrideshare.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.activities.admin.adapters.CarOwnersListAdapter;
import com.friendlyrideshare.activities.admin.adapters.ComplaintsAdapter;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.ComplaintsData;
import com.friendlyrideshare.model.EditProfilePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintsActivity extends AppCompatActivity {
    ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        list_view = findViewById(R.id.list_view);
        getSupportActionBar().setTitle("Complaints");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        serverData();
    }

    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(ComplaintsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ComplaintsData>> call = service.getComplaints();

        call.enqueue(new Callback<List<ComplaintsData>>() {
            @Override
            public void onResponse(Call<List<ComplaintsData>> call, Response<List<ComplaintsData>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ComplaintsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    //getAllCars = response.body();
                    list_view.setAdapter(new ComplaintsAdapter(response.body(), ComplaintsActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<ComplaintsData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ComplaintsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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