package com.auribises.MyProject;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.auribises.MyProject.R.id.name;
import static com.auribises.MyProject.R.id.rollno;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendenceFragment extends Fragment implements View.OnClickListener {
    EditText txtName,txtRollno,txtTime,txtDate;
    Button btnMark;

    Student student;

    RequestQueue requestqueue;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            txtDate.setText(i+"/"+(i1+1)+"/"+i2);

        }
    };

    TimePickerDialog timePickerDialog;
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            txtTime.setText(i+" : "+i1);
        }
    };


    public AttendenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=null;
        view= inflater.inflate(R.layout.fragment_attendence, container, false);
        txtName=(EditText)view.findViewById(name);
        txtRollno=(EditText)view.findViewById(rollno);
        txtTime=(EditText)view.findViewById(R.id.timeA);
        txtDate=(EditText)view.findViewById(R.id.dateA);
        btnMark=(Button)view.findViewById(R.id.mark);
        btnMark.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        student =new Student();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        requestqueue = Volley.newRequestQueue(getContext());
         return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mark) {
            student.setName(txtName.getText().toString().trim());
            student.setRollno(txtRollno.getText().toString().trim());
            student.setTime(txtTime.getText().toString().trim());
            student.setDate(txtDate.getText().toString().trim());
            if (validation()) {
                insertIntoCloud();
            } else {
                Toast.makeText(getContext(), "Please correct Input", Toast.LENGTH_LONG).show();
            }
        } else {
            if (v.getId() == R.id.timeA) {
                showTimePicker(v);
            } else {
                if (v.getId() == R.id.dateA) {
                    showDatePicker(v);
                }
            }
        }

    }
    boolean  validation(){
        boolean flag =true;
        if(student.getName().isEmpty()){
            flag=false;
            txtName.setError("Please Enter Name");
        }
        if(student.getRollno().isEmpty()){
            flag=false;
            txtRollno.setError("Please Enter Rollno");
        }
        if(student.getTime().isEmpty()){
            flag=false;
            txtTime.setError("Please Enter Time");
        }

        if(student.getDate().isEmpty()){
            flag=false;
            txtDate.setError("Please Enter Date");
        }

        return flag;
    }

    void showDatePicker(View v){

        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(getContext(),dateSetListener,yy,mm,dd);
        datePickerDialog.show();

    }

    void showTimePicker(View v){
        Calendar calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(getContext(),timeSetListener,hh,mm,false);
        timePickerDialog.show();
    }

    void insertIntoCloud() {
        String url = "";
        progressDialog.show();
        url = Util.ATTENDENCE_PHP;

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
                map.put("time", student.getTime());
                map.put("date", student.getDate());
                Log.i("test",student.toString());
                return map;
            }
        };
        requestqueue.add(request); // execute the request, send it ti server

        clearFields();
    }


    void clearFields() {
        txtName.setText("");
        txtRollno.setText("");
        txtDate.setText("");
        txtTime.setText("");
    }

}
