package com.auribises.MyProject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.MyProject.Activity.AdminActivity;
import com.auribises.MyProject.Bean.Student;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {

     @InjectView(R.id.adminusername)
    EditText editTextAdminname;

    @InjectView(R.id.adminpassword)
    EditText editTextAdminpass;

    @InjectView(R.id.buttonlogin)
    Button btnlogin;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    RequestQueue requestqueue;
    ProgressDialog progressDialog;
   Student student;
    String input_username,input_password;
    private boolean loggedIn = false;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
       student=new Student();

        requestqueue = Volley.newRequestQueue(this);
    }
    public boolean onSupportNavigateUp(){
        Intent i=new Intent(AdminLoginActivity.this,FirstActivity.class);
        startActivity(i);
        finish();
        return true;
    }

    boolean isNetworkConnected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void onclickHandler(View view){
        if(view.getId()==R.id.buttonlogin) {
            student.setAdminusername(editTextAdminname.getText().toString().trim());
            student.setAdminpassword(editTextAdminpass.getText().toString().trim());
            if(validation()) {
                if (isNetworkConnected()) {
                    login();

                } else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }
    }

    void login(){

        input_username = editTextAdminname.getText().toString().trim();
        input_password = editTextAdminpass.getText().toString().trim();


        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, Util.ADMIN_LOGIN_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                int i = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                    jsonObjectLog = new JSONObject(response);
                    jsonArray = jsonObjectLog.getJSONArray("user");

                }catch (Exception e){
                    e.printStackTrace();
                }

                if(i==1){

                    SharedPreferences sharedPreferences = AdminLoginActivity.this.getSharedPreferences("loginSp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedin", true);
                    editor.putString("adminusername", input_username);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AdminLoginActivity.this,"Login Failure!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AdminLoginActivity.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String>map=new HashMap<>();

                map.put("adminusername",input_username);
                map.put("adminpassword",input_password);

                Log.i("username",input_username);
                Log.i("password",input_password);
                return map;
            }
        };
        requestqueue.add(request);
        clearFields();
    }

    void clearFields() {
        editTextAdminname.setText("");
        editTextAdminpass.setText("");
    }

    boolean  validation(){
        boolean flag =true;
        if(student.getAdminusername().isEmpty()){
            flag=false;
            editTextAdminname.setError("Please Enter Username");
        }
        if(student.getAdminpassword().isEmpty()){
            flag=false;
            editTextAdminpass.setError("Please Enter Password");
        }

        return flag;
    }

}
