package com.friendlyrideshare.activities.tourists.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.friendlyrideshare.R;
import com.friendlyrideshare.activities.carowner.StartRideMapsActivity;
import com.friendlyrideshare.model.BookingHistoryData;

import java.util.List;

public class MyRidesAdapters extends BaseAdapter {
    List<BookingHistoryData> data;
    Context cnt;
    public MyRidesAdapters(List<BookingHistoryData> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.row_my_rides,null);

        TextView tv_vehicle_name=(TextView)obj2.findViewById(R.id.tv_vehicle_name);
        tv_vehicle_name.setText("Vehicle Name : "+data.get(pos).vehicle_name);

        TextView tv_vehicle_number_plate=(TextView)obj2.findViewById(R.id.tv_vehicle_number_plate);
        tv_vehicle_number_plate.setText("Number Plate : "+data.get(pos).vehicle_number_plate);

        TextView tv_vehicle_model=(TextView)obj2.findViewById(R.id.tv_vehicle_model);
        tv_vehicle_model.setText("Model : "+data.get(pos).vehicle_model);

        TextView tv_rider_email=(TextView)obj2.findViewById(R.id.tv_rider_email);
        tv_rider_email.setText("Rider: "+data.get(pos).rider_email);

        TextView tv_status=(TextView)obj2.findViewById(R.id.tv_status);
        tv_status.setText("Status: "+data.get(pos).status);

        Button btnStartRide = obj2.findViewById(R.id.btnStartRide);
        if(data.get(pos).status.equals("pending")){
            btnStartRide.setEnabled(false);
        }else{
            btnStartRide.setEnabled(true);
        }
        btnStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cnt, StartRideMapsActivity.class);
                intent.putExtra("vehicle_number_plate",data.get(pos).vehicle_number_plate);
                cnt.startActivity(intent);
            }
        });

        return obj2;
    }
}
