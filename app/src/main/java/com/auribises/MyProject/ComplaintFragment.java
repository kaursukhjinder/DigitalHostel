package com.auribises.MyProject;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.MyProject.Bean.Student;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplaintFragment extends Fragment implements View.OnClickListener{

    EditText Name,Room,Complaint;
    Button btnSubmit;
    Student student;

    RequestQueue requestqueue;
    ProgressDialog progressDialog;
    public ComplaintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;
        view=inflater.inflate(R.layout.fragment_complaint, container, false);
        Name=(EditText)view.findViewById(R.id.compName);
        Room=(EditText)view.findViewById(R.id.compRoom);
        Complaint=(EditText)view.findViewById(R.id.Complaint);
        btnSubmit=(Button)view.findViewById(R.id.Button);
        btnSubmit.setOnClickListener(this);
        student=new Student();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        requestqueue = Volley.newRequestQueue(getContext());
        return view;
    }

    public void onClick(View view){
        if(view.getId()==R.id.Button){
            student.setName(Name.getText().toString().trim());
            student.setRoomno(Room.getText().toString().trim());
            student.setComplaint(Complaint.getText().toString().trim());
            if (validation()) {
                insertIntoCloud();
            } else {
                Toast.makeText(getContext(), "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean  validation(){
        boolean flag =true;
        if(student.getName().isEmpty()){
            flag=false;
            Name.setError("Please Enter Name");
        }
        if(student.getRoomno().isEmpty()){
            flag=false;
            Room.setError("Please Enter Roomno");
        }
        if(student.getComplaint().isEmpty()){
            flag=false;
            Complaint.setError("Please Enter Complaint");
        }
        return flag;
    }

    void insertIntoCloud() {
        String url = "";
        progressDialog.show();
        url = Util.COMPLAINT_PHP;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Insert Successfully", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("name", student.getName());
                map.put("roomno", student.getRoomno());
                map.put("complaint", student.getComplaint());
                Log.i("test",student.toString());
                return map;
            }
        };
        requestqueue.add(request); // execute the request, send it ti server

        clearFields();
    }


    void clearFields() {
        Name.setText("");
        Room.setText("");
        Complaint.setText("");
    }


}

