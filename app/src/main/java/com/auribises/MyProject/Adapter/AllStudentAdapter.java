package com.auribises.MyProject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auribises.MyProject.Bean.AllStudent;
import com.auribises.MyProject.R;

import java.util.ArrayList;

/**
 * Created by Aman on 19/05/2017.
 */

public class AllStudentAdapter extends ArrayAdapter<AllStudent> {
     Context context;
    int resource;
    ArrayList<AllStudent> studentList, tempList;

    public AllStudentAdapter(Context context, int resource, ArrayList<AllStudent> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        studentList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(studentList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, parent, false);

        TextView txtName = (TextView) view.findViewById(R.id.textViewName);
        TextView txtRollNo = (TextView)view.findViewById(R.id.textViewRollno);
        TextView txtEmail = (TextView)view.findViewById(R.id.textViewEmail);

        AllStudent student = studentList.get(position);
        txtName.setText(student.getName());
        txtRollNo.setText(String.valueOf(student.getRollno()));
        txtEmail.setText(student.getEmail());

        Log.i("Test",student.toString());

        return view;
    }

    public void filter(String str){

        studentList.clear();

        if(str.length()==0){
            studentList.addAll(tempList);
        }else{
            for(AllStudent s : tempList){
                if(s.getName().toLowerCase().contains(str.toLowerCase())){
                    studentList.add(s);
                }
            }
        }

        notifyDataSetChanged();
    }
}