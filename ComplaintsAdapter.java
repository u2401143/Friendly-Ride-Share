package com.friendlyrideshare.activities.admin.adapters;

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
import com.friendlyrideshare.activities.tourists.ViewAllVehiclesActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.ComplaintsData;
import com.friendlyrideshare.model.ResponseData;
import com.friendlyrideshare.model.VehicledData;
import java.util.List;

public class ComplaintsAdapter extends BaseAdapter {
    List<ComplaintsData> data;
    Context cnt;
    public ComplaintsAdapter(List<ComplaintsData> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.row_complaints,null);

        TextView tv_vehicle_name=(TextView)obj2.findViewById(R.id.tv_vehicle_name);
        tv_vehicle_name.setText("Vehicle Name : "+data.get(pos).vehicle_name);

        TextView tv_vehicle_number_plate=(TextView)obj2.findViewById(R.id.tv_vehicle_number_plate);
        tv_vehicle_number_plate.setText("Number Plate : "+data.get(pos).vehicle_number_plate);

        TextView tv_vehicle_model=(TextView)obj2.findViewById(R.id.tv_vehicle_model);
        tv_vehicle_model.setText("Model : "+data.get(pos).vehicle_model);

        TextView tv_booked_by=(TextView)obj2.findViewById(R.id.tv_booked_by);
        tv_booked_by.setText("Booked: "+data.get(pos).booked_by);

        TextView tv_rider_email=(TextView)obj2.findViewById(R.id.tv_rider_email);
        tv_rider_email.setText("Rider: "+data.get(pos).rider_email);

        TextView tv_complaint=(TextView)obj2.findViewById(R.id.tv_complaint);
        tv_complaint.setText("Complaint: "+data.get(pos).complaint);


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