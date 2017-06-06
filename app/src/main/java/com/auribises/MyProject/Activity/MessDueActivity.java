package com.auribises.MyProject.Activity;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.auribises.MyProject.R;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessDueActivity extends AppCompatActivity {

    @InjectView(R.id.NameMess)
    EditText eTxtName;

    @InjectView(R.id.Email)
    EditText eTxtEmail;

    @InjectView(R.id.messDue)
    EditText eTxtDue;

    @InjectView(R.id.buttonMessdue)
    Button Btnnsubmit;

    RequestQueue requestQueue;

    Student student;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_due);
        ButterKnife.inject(this);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        student = new Student();
    }

    boolean isNetworkConected() {

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo != null && networkInfo.isConnected());

    }

    public void OnClickEmail(View view) {
        if (view.getId() == R.id.buttonMessdue) {
            student.setName(eTxtName.getText().toString().trim());
            student.setEmail(eTxtEmail.getText().toString().trim());
            student.setMessdue(eTxtDue.getText().toString().trim());

            if (validateFields()) {
                if (isNetworkConected())
                    UpdateIntoCloud();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }


        }
    }
    void UpdateIntoCloud() {

        String url = "";
        progressDialog.show();

        url = Util.MESS_DUE_PHP;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(MessDueActivity.this, message, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MessDueActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.i("test", e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(MessDueActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", error.toString());
                progressDialog.dismiss();
                Toast.makeText(MessDueActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("name", student.getName());
                map.put("email", student.getEmail());
                map.put("messdue", student.getMessdue());

                Log.i("test", student.toString());
                return map;

            }
        };
        requestQueue.add(request); // execute the request, send it to server

    }
    boolean validateFields() {
        boolean flag = true;
        if (student.getName().isEmpty()) {
            flag = false;
            eTxtName.setError("Please Enter Name");
        }
        if (student.getEmail().isEmpty()) {
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }
        if (student.getMessdue().isEmpty()) {
            flag = false;
            eTxtDue.setError("Please Enter Mess Due");
        }

        return flag;


    }

}
