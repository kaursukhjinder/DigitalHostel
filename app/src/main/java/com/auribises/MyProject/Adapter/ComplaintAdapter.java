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

import com.auribises.MyProject.Bean.Complaint;
import com.auribises.MyProject.R;

import java.util.ArrayList;

/**
 * Created by Satvir on 22-May-17.
 */

public class ComplaintAdapter extends ArrayAdapter<Complaint> {

    Context context;
    int resource;
    ArrayList<Complaint> complaintList,tempList;

    public ComplaintAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Complaint> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        complaintList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(complaintList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.txtviewName);
        TextView txtRoomno = (TextView)view.findViewById(R.id.txtviewRoom);
        TextView txtComplaint = (TextView)view.findViewById(R.id.txtviewComplaint);


        Complaint complaint = complaintList.get(position);
        txtName.setText(complaint.getName());
        txtRoomno.setText(String.valueOf(complaint.getRoomno()));
        txtComplaint.setText(complaint.getComplaint());

        Log.i("Test", complaint.toString());

        return view;
    }
    public void filter(String str){

        complaintList.clear();

        if(str.length()==0){
            complaintList.addAll(tempList);
        }else{
            for(Complaint t: tempList){
                if(t.getName().toLowerCase().contains(str.toLowerCase())){
                    complaintList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }


}
