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
import com.friendlyrideshare.activities.carowner.BookingRequestsActivity;
import com.friendlyrideshare.activities.carowner.adapters.BookingRequestAdapter;
import com.friendlyrideshare.activities.tourists.adapters.BookingHistoryAdapter;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.BookingHistoryData;
import com.friendlyrideshare.model.BookingRequestsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingHistoryActivity extends AppCompatActivity {
    ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_history);
        list_view = findViewById(R.id.list_view);
        getSupportActionBar().setTitle("Bookings History");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        serverData();
    }

    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(MyBookingHistoryActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<List<BookingHistoryData>> call = service.getBookingHistory(sharedPreferences.getString("uname","-"));

        call.enqueue(new Callback<List<BookingHistoryData>>() {
            @Override
            public void onResponse(Call<List<BookingHistoryData>> call, Response<List<BookingHistoryData>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(MyBookingHistoryActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    //getAllCars = response.body();
                    list_view.setAdapter(new BookingHistoryAdapter(response.body(), MyBookingHistoryActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<BookingHistoryData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyBookingHistoryActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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