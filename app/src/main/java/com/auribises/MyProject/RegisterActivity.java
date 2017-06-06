package com.auribises.MyProject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextRollno)
    EditText eTxtRollno;

    @InjectView(R.id.spinnerCourse)
    Spinner spCourse;

    @InjectView(R.id.editTextPassword)
    EditText eTxtPassword;

    @InjectView(R.id.editTextRoomno)
    EditText eTxtRoom;

    @InjectView(R.id.radioButtonBoyshostel)
    RadioButton rbBoysHostel;

    @InjectView(R.id.radioButtonGirlshostel)
    RadioButton rbGirlsHostel;

    @InjectView(R.id.editTextPhone)
    EditText eTxtPhone;

    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.editTextAddress)
    EditText eTxtAddress;

    @InjectView(R.id.spinnerState)
    Spinner spState;

    @InjectView(R.id.buttonSubmit)
    Button btnSubmit;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ProgressDialog progressDialog;

    Student student;

    boolean updateMode;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    RequestQueue requestQueue;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Util.sharedPreferences,MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        student = new Student();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Course--");
        adapter.add("B.Tech");
        adapter.add("M.Tech");
        adapter.add("MBA");
        adapter.add("MCA");


        spCourse.setAdapter(adapter);

        spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    student.setCourse(adapter.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("--Select State--");
        adapter2.add("Punjab");
        adapter2.add("Haryana");
        adapter2.add("Jammu & kashmir");
        adapter2.add("Bihar");
        adapter2.add("Rajasthan");

        spState.setAdapter(adapter2);

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    student.setState(adapter2.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        rbBoysHostel.setOnCheckedChangeListener(this);
        rbGirlsHostel.setOnCheckedChangeListener(this);

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyStudent");




    }
    public boolean onSupportNavigateUp(){
        Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
        return true;
    }



    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void clickHandler(View view){
        if(view.getId() == R.id.buttonSubmit){
            //String name = eTxtName.getText().toString().trim();
            //student.setName(name);
            student.setName(eTxtName.getText().toString().trim());
            student.setRollno(eTxtRollno.getText().toString().trim());
            student.setPassword(eTxtPassword.getText().toString().trim());
            student.setRoomno(eTxtRoom.getText().toString().trim());
            student.setPhone(eTxtPhone.getText().toString().trim());
            student.setEmail(eTxtEmail.getText().toString().trim());
            student.setAddress(eTxtAddress.getText().toString().trim());


            if(validateFields()) {
                if (isNetworkConected()) {
                    insertIntoCloud();

                } else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }
    }


    void insertIntoCloud() {

        String url = "";
        progressDialog.show();

            url = Util.INSERT_STUDENTTABLE_PHP;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                     Log.i("test",response.toString());
                    jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                            Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("name", student.getName());
                map.put("rollno", student.getRollno());
                map.put("course", student.getCourse());
                map.put("roomno",student.getRoomno());
                map.put("hostel", student.getHostel());
                map.put("phone", student.getPhone());
                map.put("email", student.getEmail());
                map.put("password", student.getPassword());
                map.put("address", student.getAddress());
                map.put("state", student.getState());
                Log.i("test",student.toString());
                return map;
            }
        };


        requestQueue.add(request); // execute the request, send it ti server

        clearFields();
    }


    void clearFields() {
        eTxtName.setText("");
        eTxtRollno.setText("");
        spCourse.setSelection(0);
        eTxtPassword.setText("");
        rbBoysHostel.setChecked(false);
        rbGirlsHostel.setChecked(false);
        eTxtPhone.setText("");
        eTxtEmail.setText("");
        eTxtAddress.setText("");
        spState.setSelection(0);
    }


    @Override
        public void onCheckedChanged (CompoundButton buttonView,boolean b){
            int id = buttonView.getId();

            if (b) {
                if (id == R.id.radioButtonBoyshostel) {
                    student.setHostel("Boys Hostel");
                } else {
                    student.setHostel("Girls Hostel");
                }
            }
        }

        Boolean validateFields(){
            boolean flag = true;

            if (student.getName().isEmpty()) {
                flag = false;
                eTxtName.setError("Please Enter Name");
            }

            if (student.getRollno().isEmpty()) {
                flag = false;
                eTxtRollno.setError("Please Enter RollNo");
            }

            if (student.getPassword().isEmpty()) {
                flag = false;
                eTxtPassword.setError("Please Enter Password");
            }else {
                if (student.getPassword().length() > 4 && student.getPassword().length() < 10) {
                    flag = false;
                    eTxtPassword.setError("Please Enter Password between 4 and 10 alphanumeric character");
                }
            }

            if (student.getRoomno().isEmpty()) {
                flag = false;
                eTxtRoom.setError("Please Enter Roomno");
            }



            if (student.getPhone().isEmpty()) {
                flag = false;
                eTxtPhone.setError("Please Enter Phone");
            } else {
                if (student.getPhone().length() < 10) {
                    flag = false;
                    eTxtPhone.setError("Please Enter 10 digits Phone Number");
                }
            }

            if (student.getEmail().isEmpty()) {
                flag = false;
                eTxtEmail.setError("Please Enter Email");
            } else {
                if (!(student.getEmail().contains("@") && student.getEmail().contains("."))) {
                    flag = false;
                    eTxtEmail.setError("Please Enter correct Email");
                }
            }


            if (student.getAddress().isEmpty()) {
                flag = false;
                eTxtAddress.setError("Please Enter Address");
            }


            return flag;
        }


    }








