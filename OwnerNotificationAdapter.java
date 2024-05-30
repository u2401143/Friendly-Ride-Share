package com.friendlyrideshare.activities.carowner.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.friendlyrideshare.R;
import com.friendlyrideshare.Utils;
import com.friendlyrideshare.activities.carowner.EditVehicleDetailsActivity;
import com.friendlyrideshare.activities.carowner.VehicleDetailsActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.GeoCrossPoint;
import com.friendlyrideshare.model.ResponseData;
import com.friendlyrideshare.model.VehicledData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerNotificationAdapter extends BaseAdapter {
    List<GeoCrossPoint> getAllCarOwnersProfilePojos;
    Context cnt;
    public OwnerNotificationAdapter(List<GeoCrossPoint> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.row_notifications,null);

        TextView tv_vehicle_number_plate=(TextView)obj2.findViewById(R.id.tv_vehicle_number_plate);
        tv_vehicle_number_plate.setText("Number Plate : "+getAllCarOwnersProfilePojos.get(pos).vehicle_plate_number);

        TextView lat_lng=(TextView)obj2.findViewById(R.id.lat_lng);
        lat_lng.setText("Lat: "+getAllCarOwnersProfilePojos.get(pos).lat + " Lng : "+getAllCarOwnersProfilePojos.get(pos).lng);

        TextView create_date_time=(TextView)obj2.findViewById(R.id.create_date_time);
        create_date_time.setText("Create Data : "+getAllCarOwnersProfilePojos.get(pos).create_date_time);

        TextView created_by=(TextView)obj2.findViewById(R.id.created_by);
        created_by.setText("Created By : "+getAllCarOwnersProfilePojos.get(pos).created_by);


        return obj2;
    }
}