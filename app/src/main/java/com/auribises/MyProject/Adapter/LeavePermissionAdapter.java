package com.auribises.MyProject.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auribises.MyProject.Bean.LeavePermission;
import com.auribises.MyProject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satvir on 22-May-17.
 */

public class LeavePermissionAdapter extends ArrayAdapter<LeavePermission>{

    Context context;
    int resource;
    ArrayList<LeavePermission> PermissionList,tempList;

    public LeavePermissionAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<LeavePermission> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        PermissionList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(PermissionList);


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtVname = (TextView)view.findViewById(R.id.txtviewnamepermission);
        TextView txtVrollno = (TextView)view.findViewById(R.id.txtviewrollnopermission);
        TextView txtreason = (TextView)view.findViewById(R.id.txtviewreason);

        LeavePermission leavePermission = PermissionList.get(position);
        txtVname.setText(leavePermission.getName());
        txtVrollno.setText(String.valueOf(leavePermission.getRollno()));
        txtreason.setText(leavePermission.getReason());

        Log.i("Test",leavePermission.toString());

        return view;
    }
    public void filter(String str){

        PermissionList.clear();

        if(str.length()==0){
            PermissionList.addAll(tempList);
        }else{
            for(LeavePermission t : tempList){
                if(t.getName().toLowerCase().contains(str.toLowerCase())){
                    PermissionList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }

}
