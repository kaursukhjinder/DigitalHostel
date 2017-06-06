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
public class HostelLeaveFragment extends Fragment implements View.OnClickListener{

    EditText HostelName,HostelRollno,Reason;
    Button btnLeave;
    Student student;

    RequestQueue requestqueue;
    ProgressDialog progressDialog;

    public HostelLeaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=null;
        view= inflater.inflate(R.layout.fragment_hostel_leave, container, false);
        HostelName=(EditText)view.findViewById(R.id.HostelName);
        HostelRollno=(EditText)view.findViewById(R.id.HostelRoll);
        Reason=(EditText)view.findViewById(R.id.Reason);
        btnLeave=(Button)view.findViewById(R.id.buttonLeave);
        btnLeave.setOnClickListener(this);
        student=new Student();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        requestqueue = Volley.newRequestQueue(getContext());
        return view;
    }

    public void onClick(View view){
        if(view.getId()==R.id.buttonLeave){
            student.setName(HostelName.getText().toString().trim());
            student.setRollno(HostelRollno.getText().toString().trim());
            student.setReason(Reason.getText().toString().trim());
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
            HostelName.setError("Please Enter Name");
        }
        if(student.getRollno().isEmpty()){
            flag=false;
            HostelRollno.setError("Please Enter Rollno");
        }
        if(student.getReason().isEmpty()){
            flag=false;
            Reason.setError("Please Enter Reason");
        }
        return flag;
    }

    void insertIntoCloud() {
        String url = "";
        progressDialog.show();
        url = Util.HOSTELLEAVE_PHP;

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
                map.put("rollno", student.getRollno());
                map.put("reason", student.getReason());
                Log.i("test",student.toString());
                return map;
            }
        };
        requestqueue.add(request); // execute the request, send it ti server

        clearFields();
    }


    void clearFields() {
        HostelName.setText("");
        HostelRollno.setText("");
        Reason.setText("");
    }


}
