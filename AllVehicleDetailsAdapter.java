package com.friendlyrideshare.activities.admin.adapters;

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
import com.friendlyrideshare.activities.admin.AllVehiclesDetailsActivity;
import com.friendlyrideshare.activities.admin.TrackVehicleActivity;
import com.friendlyrideshare.activities.carowner.EditVehicleDetailsActivity;
import com.friendlyrideshare.activities.carowner.VehicleDetailsActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.ResponseData;
import com.friendlyrideshare.model.VehicledData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class AllVehicleDetailsAdapter extends BaseAdapter {
    List<VehicledData> getAllCarOwnersProfilePojos;
    Context cnt;
    public AllVehicleDetailsAdapter(List<VehicledData> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.row_all_vehicles,null);

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


        Button btn_start_tracking=(Button)obj2.findViewById(R.id.btn_start_tracking);
        btn_start_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //book(getAllCarOwnersProfilePojos.get(pos));
                Intent intent = new Intent(cnt, TrackVehicleActivity.class);
                intent.putExtra("vehicle_name",getAllCarOwnersProfilePojos.get(pos).vehicle_name);
                intent.putExtra("vehicle_number_plate",getAllCarOwnersProfilePojos.get(pos).vehicle_number_plate);
                intent.putExtra("vehicle_model",getAllCarOwnersProfilePojos.get(pos).vehicle_model);
                intent.putExtra("lat",getAllCarOwnersProfilePojos.get(pos).lat);
                intent.putExtra("lng",getAllCarOwnersProfilePojos.get(pos).lng);
                intent.putExtra("notify_radius",getAllCarOwnersProfilePojos.get(pos).notify_radius);
                cnt.startActivity(intent);
            }
        });


        return obj2;
    }

}
