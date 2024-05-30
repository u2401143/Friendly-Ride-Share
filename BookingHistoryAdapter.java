package com.friendlyrideshare.activities.tourists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.friendlyrideshare.R;
import com.friendlyrideshare.model.BookingHistoryData;
import com.friendlyrideshare.model.BookingRequestsData;

import java.util.List;

public class BookingHistoryAdapter extends BaseAdapter {
    List<BookingHistoryData> data;
    Context cnt;
    public BookingHistoryAdapter(List<BookingHistoryData> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.row_booking_history,null);

        TextView tv_vehicle_name=(TextView)obj2.findViewById(R.id.tv_vehicle_name);
        tv_vehicle_name.setText("Vehicle Name : "+data.get(pos).vehicle_name);

        TextView tv_vehicle_number_plate=(TextView)obj2.findViewById(R.id.tv_vehicle_number_plate);
        tv_vehicle_number_plate.setText("Number Plate : "+data.get(pos).vehicle_number_plate);

        TextView tv_vehicle_model=(TextView)obj2.findViewById(R.id.tv_vehicle_model);
        tv_vehicle_model.setText("Model : "+data.get(pos).vehicle_model);

        TextView tv_rider_email=(TextView)obj2.findViewById(R.id.tv_rider_email);
        tv_rider_email.setText("Rider: "+data.get(pos).rider_email);


        return obj2;
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