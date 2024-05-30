package com.friendlyrideshare.activities.carowner.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.activities.carowner.BookingRequestsActivity;
import com.friendlyrideshare.activities.carowner.StartRideMapsActivity;
import com.friendlyrideshare.activities.tourists.ViewAllVehiclesActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.BookingRequestsData;
import com.friendlyrideshare.model.ComplaintsData;
import com.friendlyrideshare.model.ResponseData;
import com.friendlyrideshare.model.VehicledData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRequestAdapter extends BaseAdapter {
    List<BookingRequestsData> data;
    Context cnt;
    public BookingRequestAdapter(List<BookingRequestsData> ar, Context cnt)
    {
        this.data=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.row_booking,null);

        TextView tv_vehicle_name=(TextView)obj2.findViewById(R.id.tv_vehicle_name);
        tv_vehicle_name.setText("Vehicle Name : "+data.get(pos).vehicle_name);

        TextView tv_vehicle_number_plate=(TextView)obj2.findViewById(R.id.tv_vehicle_number_plate);
        tv_vehicle_number_plate.setText("Number Plate : "+data.get(pos).vehicle_number_plate);

        TextView tv_vehicle_model=(TextView)obj2.findViewById(R.id.tv_vehicle_model);
        tv_vehicle_model.setText("Model : "+data.get(pos).vehicle_model);

        TextView tv_booked_by=(TextView)obj2.findViewById(R.id.tv_booked_by);
        tv_booked_by.setText("Booked: "+data.get(pos).booked_by);

        Button btnStartRide=(Button)obj2.findViewById(R.id.btnStartRide);
        btnStartRide.setText("Confirm");
        btnStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(data.get(pos).vehicle_number_plate);
                /*Intent intent = new Intent(cnt, StartRideMapsActivity.class);
                intent.putExtra("vehicle_number_plate",data.get(pos).vehicle_number_plate);
                cnt.startActivity(intent);*/
            }
        });
        return obj2;
    }
    ProgressDialog progressDialog;
    public void updateStatus(String vehicle_no){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = cnt.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<ResponseData> call = service.confirmRide(vehicle_no);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(cnt,""+response.body().message,Toast.LENGTH_SHORT).show();
                    ((BookingRequestsActivity)cnt).refersh();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*public void complaintDialog(VehicledData data){
        LayoutInflater inflater = LayoutInflater.from(cnt);
        final View view = inflater.inflate(R.layout.dialog_complaint, null);
        AlertDialog alertDialog = new AlertDialog.Builder(cnt).create();
        alertDialog.setTitle("Complaint");

        alertDialog.setCancelable(false);


        final EditText etComments = (EditText) view.findViewById(R.id.etComplaint);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (etComments.getText().toString().isEmpty()) {
                    Toast.makeText(cnt, "Comments Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                complaint(data,etComments.getText().toString());
            }
        });


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });


        alertDialog.setView(view);
        alertDialog.show();
    }*/
}