package com.auribises.MyProject.Activity;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.auribises.MyProject.R;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LeaveEntryActivity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.EditnameL)
    EditText eTxtName;

    @InjectView(R.id.EditrollnoL)
    EditText eTxtRoll;

    @InjectView(R.id.EdittimeL)
    EditText eTxtTime;

    @InjectView(R.id.EditdateL)
    EditText eTxtDate;

    @InjectView(R.id.SubmitL)
    Button BtnSubmit;

    Student student;

    RequestQueue requestqueue;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            eTxtDate.setText(i+"/"+(i1+1)+"/"+i2);

        }
    };

    TimePickerDialog timePickerDialog;
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            eTxtTime.setText(i+" : "+i1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_entry);
        ButterKnife.inject(this);
        BtnSubmit.setOnClickListener(this);
        eTxtTime.setOnClickListener(this);
        eTxtDate.setOnClickListener(this);
        student =new Student();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(101);

        requestqueue = Volley.newRequestQueue(this);
    }
    public void onClick(View view){
        if (view.getId() == R.id.SubmitL) {
            student.setName(eTxtName.getText().toString().trim());
            student.setRollno(eTxtRoll.getText().toString().trim());
            student.setTime(eTxtTime.getText().toString().trim());
            student.setDate(eTxtDate.getText().toString().trim());
            if (validation()) {
                insertIntoCloud();
            } else {
                Toast.makeText(LeaveEntryActivity.this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        } else {
            if (view.getId() == R.id.EdittimeL) {
                showTimePicker(view);
            } else {
                if (view.getId() == R.id.EditdateL) {
                    showDatePicker(view);
                }
            }
        }
    }


    boolean  validation(){
        boolean flag =true;
        if(student.getName().isEmpty()){
            flag=false;
            eTxtName.setError("Please Enter Name");
        }
        if(student.getRollno().isEmpty()){
            flag=false;
            eTxtRoll.setError("Please Enter Rollno");
        }
        if(student.getTime().isEmpty()){
            flag=false;
            eTxtTime.setError("Please Enter Time");
        }

        if(student.getDate().isEmpty()){
            flag=false;
            eTxtDate.setError("Please Enter Date");
        }

        return flag;
    }

    void showDatePicker(View v){

        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this,dateSetListener,yy,mm,dd);
        datePickerDialog.show();

    }

    void showTimePicker(View v){
        Calendar calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this,timeSetListener,hh,mm,false);
        timePickerDialog.show();
    }

    void insertIntoCloud() {
        String url = "";
        progressDialog.show();
        url = Util.LEAVE_ENTRY_PHP;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(LeaveEntryActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(LeaveEntryActivity.this, "Insert Successfully", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(LeaveEntryActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
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
        eTxtName.setText("");
        eTxtRoll.setText("");
        eTxtDate.setText("");
        eTxtTime.setText("");
    }

}


