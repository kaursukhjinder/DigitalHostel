package com.auribises.MyProject;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.auribises.MyProject.Bean.Login;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends AppCompatActivity {

    @InjectView(R.id.Nametxt)
    EditText TxtName;

    @InjectView(R.id.textRollno)
    EditText TxtRollno;

    @InjectView(R.id.textPhone)
    EditText TxtPhone;

    @InjectView(R.id.textEmail)
    EditText TxtEmail;

    @InjectView(R.id.textPass)
    EditText TxtPass;

    @InjectView(R.id.textAddress)
    EditText TxtAdress;

    @InjectView(R.id.textCity)
    EditText TxtState;

    @InjectView(R.id.textRoom)
    EditText TxtRoom;

    @InjectView(R.id.Update)
    Button btnUpdate;

    RequestQueue requestQueue;
    String name,rollno,phone,address,email,password,state,roomno;
    int id=0;
    boolean flag=true;
   Login login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    JSONArray jsonArray;
    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Intent rcv = getIntent();

        requestQueue = Volley.newRequestQueue(this);
        login=new Login();

        sharedPreferences = getSharedPreferences(Util.sharedPreferences,MODE_PRIVATE);
        login.setId(sharedPreferences.getInt("id",0));
        login.setName(sharedPreferences.getString("name",""));
        login.setRollno(sharedPreferences.getString("rollno",""));
        login.setPhone(sharedPreferences.getString("phone",""));
        login.setEmail(sharedPreferences.getString("email",""));
        login.setPassword(sharedPreferences.getString("password",""));
        login.setRoomno(sharedPreferences.getString("roomno",""));
        login.setAddress(sharedPreferences.getString("address",""));
        login.setState(sharedPreferences.getString("state",""));
        /*id = sharedPreferences.getInt("id",0);
        name = sharedPreferences.getString("name","");
        rollno= sharedPreferences.getString("rollno","");
        phone = sharedPreferences.getString("phone","");
        email= sharedPreferences.getString("email","");
        password = sharedPreferences.getString("password","");
        roomno= sharedPreferences.getString("roomno","");
        address= sharedPreferences.getString("address","");
        state= sharedPreferences.getString("state","");*/
        setFields();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);



        //retrieveFromStudent();
    }
    public boolean onSupportNavigateUp(){
       Intent i=new Intent(ProfileActivity.this,StudentActivity.class);
        startActivity(i);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,101,0,"Logout");
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            showAlertDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public  void OnClick(View view){
        login.setName(TxtName.getText().toString().trim());
        login.setRollno(TxtRollno.getText().toString().trim());
        login.setRoomno(TxtRoom.getText().toString().trim());
        login.setPhone(TxtPhone.getText().toString().trim());
        login.setEmail(TxtEmail.getText().toString().trim());
        login.setPassword(TxtPass.getText().toString().trim());
        login.setAddress(TxtAdress.getText().toString().trim());
        login.setState(TxtState.getText().toString().trim());
        if(view.getId()==R.id.Update){
            if(validate()) {
                if (isNetworkConected())
                    updateData();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }
        }
        }


    void showAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Do you wish to Logout");
        builder.setCancelable(false); // If user will press the back key dialog will not be dismissed
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setFields(){
        Log.i("test","----"+login.toString());
        TxtName.setText(login.getName());
        Log.i("test",TxtName.toString());
        TxtRollno.setText(login.getRollno());
        Log.i("test",TxtRollno.toString());

        TxtPhone.setText(login.getPhone());
        Log.i("test",TxtPhone.toString());

        TxtEmail.setText(login.getEmail());
        Log.i("test",TxtEmail.toString());

        TxtPass.setText(login.getPassword());
        Log.i("test",TxtPass.toString());
        TxtRoom.setText(login.getRoomno());
        Log.i("test",TxtRoom.toString());
        TxtAdress.setText(login.getAddress());
        Log.i("test",TxtAdress.toString());
        TxtState.setText(login.getState());
        Log.i("test",TxtState.toString());
    }

    void updateData(){


        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.UPDATE_PHP, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                try{
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        Toast.makeText(ProfileActivity.this,message,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ProfileActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this,"Some error occured, please try again in some time.",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("id",String.valueOf(login.getId()));
                map.put("name",login.getName());
                map.put("rollno",login.getRollno());
                map.put("roomno",login.getRoomno());
                map.put("phone",login.getPhone());
                map.put("email",login.getEmail());
                map.put("password",login.getPassword());
                map.put("address",login.getAddress());
                map.put("state",login.getState());
                Log.i("test",login.toString());

                return map;
            }
        };
        requestQueue.add(request);

    }

    boolean isNetworkConected(){
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    public boolean validate() {
        boolean valid = true;
        name=TxtName.getText().toString().trim();
        phone=TxtPhone.getText().toString().trim();
       rollno=TxtRollno.getText().toString().trim();
        roomno=TxtRoom.getText().toString().trim();
        email=TxtEmail.getText().toString().trim();
        password=TxtPass.getText().toString().trim();
        address=TxtAdress.getText().toString().trim();
        state=TxtState.getText().toString().trim();
        if (name.isEmpty()) {
            TxtName.setError("Please fill this field");
            valid = false;
        } else {TxtName.setError(null);
        }

        /*if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }*/
        if(phone.isEmpty()){
            TxtPhone.setError("Please enter phone!");
        }else{
            if(phone.length()<10){
                flag = false;
                TxtPhone.setError("Please enter 10 digit phone number!");
            }
        }

        return valid;
    }


}
