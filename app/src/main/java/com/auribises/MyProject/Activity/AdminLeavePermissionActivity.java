package com.auribises.MyProject.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.MyProject.Adapter.LeavePermissionAdapter;
import com.auribises.MyProject.Bean.LeavePermission;
import com.auribises.MyProject.R;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdminLeavePermissionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    @InjectView(R.id.listViewPermission)
    ListView listViewP;

    @InjectView(R.id.editSearch)
    EditText eTxtSearch;

    ArrayList<LeavePermission> PermissionList;

    LeavePermission leavePermission;
    LeavePermissionAdapter adapter;
    int pos;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    NotificationManager notificationManager;
    Notification notification;
    NotificationCompat.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_permission);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);

        eTxtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String str = charSequence.toString();
                    if(adapter!=null){
                        adapter.filter(str);
                    }
                }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        retrieveFromCloud();
    }

    void retrieveFromCloud(){

        progressDialog.show();

        PermissionList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LEAVEPERMISSION_RETRIEVE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("students");

                    int id=0;
                    String n="",p="",e="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("name");
                        p = jObj.getString("rollno");
                        e = jObj.getString("reason");

                        PermissionList.add(new LeavePermission(id,n,p,e));
                    }

                    adapter = new LeavePermissionAdapter(AdminLeavePermissionActivity.this,R.layout.permission_list, PermissionList);
                    listViewP.setAdapter(adapter);
                    listViewP.setOnItemClickListener(AdminLeavePermissionActivity.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminLeavePermissionActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(AdminLeavePermissionActivity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        pos = i;
        leavePermission = PermissionList.get(i);
        showOptions();

    }

    void showOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items ={"Allowed","Not Allowed"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:
                        showNotification();
                        break;

                    case 1:
                        Shownotification();
                        break;

                }

            }
        });
        //AlertDialog dialog = builder.create();
        //dialog.show();

        builder.create().show();
    }

    void showNotification(){

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Leave Permission");
        builder.setContentText("You are Allowed to go home");
        builder.setSmallIcon(R.drawable.hostel);
        builder.setDefaults(Notification.DEFAULT_ALL); // Requires Permission to Vibrate



        Intent i = new Intent(AdminLeavePermissionActivity.this,LeaveEntryActivity.class);
        PendingIntent pi = PendingIntent.getActivity
                (this,200,i,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pi);

        notification = builder.build();

        notificationManager.notify(101,notification);
    }

    void Shownotification(){

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Leave Permission");
        builder.setContentText("You are Not Allowed to go home");
        builder.setSmallIcon(R.drawable.hostel);
        builder.setDefaults(Notification.DEFAULT_ALL); // Requires Permission to Vibrate

        //Intent i = new Intent(MainActivity.this,WelcomeActivity.class);
        //PendingIntent pi = PendingIntent.getActivity
        //(this,200,i,PendingIntent.FLAG_UPDATE_CURRENT);

        // builder.setContentIntent(pi);

        /*builder.setStyle(new NotificationCompat.BigTextStyle());
        builder.addAction(android.R.drawable.ic_menu_call,"Call",pi);
        builder.addAction(R.drawable.music,"Delete",pi);*/

        notification = builder.build();

        notificationManager.notify(102,notification);
    }




}

