package com.friendlyrideshare.activities.tourists.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.activities.admin.RegisteredTouristsActivity;
import com.friendlyrideshare.activities.tourists.ViewAllVehiclesActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.EditProfilePojo;
import com.friendlyrideshare.model.ResponseData;
import com.friendlyrideshare.model.VehicledData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VehiclesListAdapter extends BaseAdapter {
    List<VehicledData> getAllCarOwnersProfilePojos;
    Context cnt;
    public VehiclesListAdapter(List<VehicledData> ar, Context cnt)
    {
        this.getAllCarOwnersProfilePojos=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return getAllCarOwnersProfilePojos.size();
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
        View obj2=obj1.inflate(R.layout.row_vehicles,null);

        TextView tv_vehicle_name=(TextView)obj2.findViewById(R.id.tv_vehicle_name);
        tv_vehicle_name.setText("Vehicle Name : "+getAllCarOwnersProfilePojos.get(pos).vehicle_name);

        TextView tv_vehicle_number_plate=(TextView)obj2.findViewById(R.id.tv_vehicle_number_plate);
        tv_vehicle_number_plate.setText("Number Plate : "+getAllCarOwnersProfilePojos.get(pos).vehicle_number_plate);

        TextView tv_vehicle_model=(TextView)obj2.findViewById(R.id.tv_vehicle_model);
        tv_vehicle_model.setText("Model : "+getAllCarOwnersProfilePojos.get(pos).vehicle_model);

        TextView tv_location=(TextView)obj2.findViewById(R.id.tv_location);
        tv_location.setText("Lat: "+getAllCarOwnersProfilePojos.get(pos).lat + " Lng : "+getAllCarOwnersProfilePojos.get(pos).lng);

        TextView tv_rider_email=(TextView)obj2.findViewById(R.id.tv_rider_email);
        tv_rider_email.setText("Email: "+getAllCarOwnersProfilePojos.get(pos).rider_email);


        Button btn_book_vehicle=(Button)obj2.findViewById(R.id.btn_book_vehicle);
        btn_book_vehicle.setVisibility(View.GONE);
        btn_book_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book(getAllCarOwnersProfilePojos.get(pos));
            }
        });

        Button btn_rise_complaint=(Button)obj2.findViewById(R.id.btn_rise_complaint);
        btn_rise_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaintDialog(getAllCarOwnersProfilePojos.get(pos));
                //complaint(getAllCarOwnersProfilePojos.get(pos));
            }
        });

        return obj2;
    }
    ProgressDialog progressDialog;
    public void book(VehicledData data){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = cnt.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<ResponseData> call = service.bookVehicle(data.vehicle_name,data.vehicle_number_plate,data.vehicle_model,data.rider_email,sharedPreferences.getString("uname","-"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(cnt,""+response.body().message,Toast.LENGTH_SHORT).show();
                    ((ViewAllVehiclesActivity)cnt).finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void complaintDialog(VehicledData data){
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
    }
    public void complaint(VehicledData data,String complaint){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedPreferences = cnt.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Call<ResponseData> call = service.complaints(data.vehicle_name,data.vehicle_number_plate,data.vehicle_model,data.rider_email,sharedPreferences.getString("uname","-"),complaint);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(cnt,""+response.body().message,Toast.LENGTH_SHORT).show();
                    ((ViewAllVehiclesActivity)cnt).finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}