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

import com.auribises.MyProject.Bean.Entry;
import com.auribises.MyProject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satvir on 24-May-17.
 */

public class EntryAdapter extends ArrayAdapter<Entry> {

    Context context;
    int resource;
    ArrayList<Entry> entryList,tempList;
    public EntryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Entry> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        entryList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(entryList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtnameEntry = (TextView)view.findViewById(R.id.txtviewnameEntry);
        TextView txtrollnoEntry = (TextView)view.findViewById(R.id.txtviewrollnoEntry);
        TextView txtdateEntry = (TextView)view.findViewById(R.id.txtviewdateEntry);
        TextView txttimeEntry = (TextView)view.findViewById(R.id.txtviewtimeEntry);

        Entry entry = entryList.get(position);
        txtnameEntry.setText(entry.getName());
        txtrollnoEntry.setText(String.valueOf(entry.getRollno()));
        txtdateEntry.setText(entry.getDate());
        txttimeEntry.setText(entry.getTime());


        Log.i("Test",entry.toString());

        return view;
    }
    public void filter(String str){

        entryList.clear();

        if(str.length()==0){
            entryList.addAll(tempList);
        }else{
            for(Entry t : tempList){
                if(t.getName().toLowerCase().contains(str.toLowerCase())){
                    entryList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }

}
