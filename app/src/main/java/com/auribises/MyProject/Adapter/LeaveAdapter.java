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

import com.auribises.MyProject.Bean.Leave;
import com.auribises.MyProject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satvir on 25-May-17.
 */

public class LeaveAdapter extends ArrayAdapter<Leave> {

    Context context;
    int resource;
    ArrayList<Leave> leaveList,tempList;

    public LeaveAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Leave> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        leaveList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(leaveList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtnameL = (TextView)view.findViewById(R.id.txtviewnameleave);
        TextView txtrollnoL = (TextView)view.findViewById(R.id.txtviewrollnoleave);
        TextView txttimeL = (TextView)view.findViewById(R.id.txtviewtimeleave);
        TextView txtdateL = (TextView)view.findViewById(R.id.txtviewdateleave);


        Leave leave = leaveList.get(position);
        txtnameL.setText(leave.getName());
        txtrollnoL.setText(String.valueOf(leave.getRollno()));
        txttimeL.setText(String.valueOf(leave.getTime()));
        txtdateL.setText(String.valueOf(leave.getDate()));

        Log.i("Test",leave.toString());

        return view;
    }
    public void filter(String str){

        leaveList.clear();

        if(str.length()==0){
            leaveList.addAll(tempList);
        }else{
            for(Leave t : tempList){
                if(t.getName().toLowerCase().contains(str.toLowerCase())){
                    leaveList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }

}
