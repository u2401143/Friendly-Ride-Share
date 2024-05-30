package com.friendlyrideshare.activities.admin.adapters;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.friendlyrideshare.R;
import com.friendlyrideshare.activities.admin.RegisterdCarOwnersActivity;
import com.friendlyrideshare.activities.admin.RegisteredTouristsActivity;
import com.friendlyrideshare.apis.EndPointUrl;
import com.friendlyrideshare.apis.RetrofitInstance;
import com.friendlyrideshare.model.EditProfilePojo;
import com.friendlyrideshare.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TouristsListAdapter extends BaseAdapter {
    List<EditProfilePojo> getAllCarOwnersProfilePojos;
    Context cnt;
    public TouristsListAdapter(List<EditProfilePojo> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.row_profile,null);

        TextView tv_name=(TextView)obj2.findViewById(R.id.tv_name);
        tv_name.setText("Name : "+getAllCarOwnersProfilePojos.get(pos).getFirst_name()+" "+getAllCarOwnersProfilePojos.get(pos).getLast_name());

        TextView tv_phno=(TextView)obj2.findViewById(R.id.tv_phno);
        tv_phno.setText("Phno : "+getAllCarOwnersProfilePojos.get(pos).getPhonenumber());

        TextView tv_emailid=(TextView)obj2.findViewById(R.id.tv_emailid);
        tv_emailid.setText("EmailID : "+getAllCarOwnersProfilePojos.get(pos).getEmailid());

        TextView tv_status=(TextView)obj2.findViewById(R.id.tv_status);
        tv_status.setText("Status: "+getAllCarOwnersProfilePojos.get(pos).getStatus());

        Button btn_block=(Button)obj2.findViewById(R.id.btn_block);
        btn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block(getAllCarOwnersProfilePojos.get(pos).getId());
            }
        });

        Button btn_active=(Button)obj2.findViewById(R.id.btn_active);
        btn_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active(getAllCarOwnersProfilePojos.get(pos).getId());
            }
        });
        if(getAllCarOwnersProfilePojos.get(pos).getStatus().equals("active")){
            btn_block.setVisibility(View.VISIBLE);
            btn_active.setVisibility(View.GONE);
        }else{
            btn_block.setVisibility(View.GONE);
            btn_active.setVisibility(View.VISIBLE);
        }

        return obj2;
    }
    ProgressDialog progressDialog;
    public void block(String id){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.blockTourists(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    ((RegisteredTouristsActivity)cnt).refresh();
                    Toast.makeText(cnt,"Status updated successfully",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void active(String id){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.activeTourists(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    ((RegisteredTouristsActivity)cnt).refresh();
                    Toast.makeText(cnt,"Status updated successfully",Toast.LENGTH_SHORT).show();
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
