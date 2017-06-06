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

import com.auribises.MyProject.MessEntry;
import com.auribises.MyProject.R;

import java.util.ArrayList;

/**
 * Created by Satvir on 22-May-17.
 */

public class MessEntryAdapter extends ArrayAdapter<MessEntry> {

    Context context;
    int resource;
    ArrayList<MessEntry> messEntryList,tempList;

    public MessEntryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<MessEntry> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        messEntryList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(messEntryList);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView textName = (TextView)view.findViewById(R.id.txtviewname);
        TextView textRollno = (TextView)view.findViewById(R.id.txtviewrollno);
        TextView textDate = (TextView)view.findViewById(R.id.txtviewdate);
        TextView textTime = (TextView)view.findViewById(R.id.txtviewtime);


        MessEntry messEntry = messEntryList.get(position);
        textName.setText(messEntry.getName());
        textRollno.setText(String.valueOf(messEntry.getRollno()));
        textTime.setText(messEntry.getTime());
        textDate.setText(messEntry.getDate());

        Log.i("Test", messEntry.toString());

        return view;
    }
    public void filter(String str){

        messEntryList.clear();

        if(str.length()==0){
            messEntryList.addAll(tempList);
        }else{
            for(MessEntry t: tempList){
                if(t.getName().toLowerCase().contains(str.toLowerCase())){
                    messEntryList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }

}
