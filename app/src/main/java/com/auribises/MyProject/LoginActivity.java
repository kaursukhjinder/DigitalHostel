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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.MyProject.Bean.Login;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.username)
    EditText editTextname;

    @InjectView(R.id.password)
    EditText editTextpass;

    @InjectView(R.id.button)
    Button btn;

    @InjectView(R.id.login)
    TextView txtlogin;

    @InjectView(R.id.newuser)
    TextView txtuser;

    @InjectView(R.id.forgetPassword)
    TextView txtforgetpass;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    String input_username, input_password;
    private boolean loggedIn = false;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
   Login login;
    int id = 0;
    String name, rollno, roomno, phone, email, address, state,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        sharedPreferences = getSharedPreferences(Util.sharedPreferences,MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        login=new Login();
        requestQueue = Volley.newRequestQueue(this);
    }

    public boolean onSupportNavigateUp(){
        Intent i=new Intent(LoginActivity.this,FirstActivity.class);
        startActivity(i);
        finish();
        return true;
    }
    boolean isNetworkConnected() {

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void onClickHandler(View view) {
        if (view.getId() == R.id.button) {
            login.setEmail(editTextname.getText().toString().trim());
            login.setPassword(editTextpass.getText().toString().trim());
            if (validation()) {
                if (isNetworkConnected()) {
                    login();
                } else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        } else {
            if (view.getId() == R.id.newuser) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(i);

            }
        }
    }

    void login() {

        input_username = editTextname.getText().toString().trim();
        input_password = editTextpass.getText().toString().trim();


        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.LOGIN_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject;
                int i = 0;
                try {
                    Log.i("test",response.toString());
                    jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                    jsonObjectLog = new JSONObject(response);
                    //jsonArray = jsonObjectLog.getJSONArray("students");
                    //editor = sharedPreferences.edit();
                    //String str = "a";
                   // editor.putString("name",str);
                    //editor.commit();
                    //String str2 = sharedPreferences.getString("name","");
                if (i == 1) {
                    editor = sharedPreferences.edit();
                    editor.putInt("id", jsonObject.getInt("id"));
                    editor.putString("name", jsonObject.getString("name"));
                    editor.putString("rollno",jsonObject.getString("rollno") );
                    editor.putString("phone", jsonObject.getString("phone"));
                    editor.putString("email", jsonObject.getString("email"));
                    editor.putString("password",jsonObject.getString("password") );
                    editor.putString("roomno", jsonObject.getString("roomno"));
                    editor.putString("address", jsonObject.getString("address"));
                    editor.putString("state", jsonObject.getString("state"));
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failure!", Toast.LENGTH_LONG).show();
                }
                } catch (Exception e) {
                    Log.i("test",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("email", input_username);
                map.put("password", input_password);

                Log.i("username", input_username);
                Log.i("password", input_password);
                Log.i("test",login.toString());
                return map;
            }
        };
        requestQueue.add(request);
        clearFields();
    }

    void clearFields() {
        editTextname.setText("");
        editTextpass.setText("");
    }


    boolean validation() {
        boolean flag = true;
        if (login.getEmail().isEmpty()) {
            flag = false;
            editTextname.setError("Please Enter Username");
        }
        if (login.getPassword().isEmpty()) {
            flag = false;
            editTextpass.setError("Please Enter Password");
        }

        return flag;
    }
}



