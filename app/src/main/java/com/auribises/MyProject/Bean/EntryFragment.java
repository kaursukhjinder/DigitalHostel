package com.auribises.MyProject.Bean;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.auribises.MyProject.R;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntryFragment extends Fragment  implements View.OnClickListener{

    EditText editTextname,editTextrollno,editTexttime,editTextdate;
    Button btnSubmit;
    Student student;
    Calendar calendar;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    RequestQueue requestqueue;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            editTextdate.setText(i+"/"+(i1+1)+"/"+i2);

        }
    };

    TimePickerDialog timePickerDialog;
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            editTexttime.setText(i+" : "+i1);
        }
    };

    public EntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=null;
        view= inflater.inflate(R.layout.fragment_entry, container, false);
        editTextname=(EditText)view.findViewById(R.id.Name);
        editTextrollno=(EditText)view.findViewById(R.id.Rollno);
        editTexttime=(EditText)view.findViewById(R.id.Time);
        editTextdate=(EditText)view.findViewById(R.id.Date);
        btnSubmit=(Button)view.findViewById(R.id.submit);
        btnSubmit.setOnClickListener(this);
        editTexttime.setOnClickListener(this);
        editTextdate.setOnClickListener(this);
        student =new Student();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        requestqueue = Volley.newRequestQueue(getContext());
        return view;
    }
   /* boolean isNetworkConnected(){

        connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());

    }*/

    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            student.setName(editTextname.getText().toString().trim());
            student.setRollno(editTextrollno.getText().toString().trim());
            student.setTime(editTexttime.getText().toString().trim());
            student.setDate(editTextdate.getText().toString().trim());
            if (validation()) {
                    insertIntoCloud();
            } else {
                Toast.makeText(getContext(), "Please correct Input", Toast.LENGTH_LONG).show();
            }
        } else {
            if (view.getId() == R.id.Time) {
                showTimePicker(view);
            } else {
                if (view.getId() == R.id.Date) {
                    showDatePicker(view);
                }
            }
        }
    }



    boolean  validation(){
        boolean flag =true;
        if(student.getName().isEmpty() ){
            flag=false;
            editTextname.setError("Please Enter Name");
        }

        if(student.getRollno().isEmpty()){
            flag=false;
            editTextrollno.setError("Please Enter Rollno");
        }

        if(student.getTime().isEmpty()){
            flag=false;
            editTexttime.setError("Please Enter Time");
        }

        if(student.getDate().isEmpty()){
            flag=false;
            editTextdate.setError("Please Enter Date");
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
        url = Util.ENTRY_PHP;

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
        editTextname.setText("");
        editTextrollno.setText("");
        editTexttime.setText("");
        editTextdate.setText("");
    }


}



