package com.auribises.MyProject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.MyProject.Adapter.MessEntryAdapter;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdminMessEntryActivity extends AppCompatActivity {

    @InjectView(R.id.listViewMS)
    ListView ListView;

    @InjectView(R.id.editSearchMess)
    EditText editsearchmess;

    MessEntryAdapter adapter;
    ArrayList<MessEntry> messEntryArrayList;
    MessEntry messEntry;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mess_entry);

        ButterKnife.inject(this);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        editsearchmess.addTextChangedListener(new TextWatcher() {
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

        messEntryArrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.MESSENTRY_RETRIEVE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("Test", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("students");

                    int id=0;
                    String n="",r="",d="",t="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("name");
                        r = jObj.getString("rollno");
                        d= jObj.getString("date");
                        t= jObj.getString("time");

                        messEntryArrayList.add(new MessEntry(id,n,r,d,t));
                    }

                    adapter = new MessEntryAdapter(AdminMessEntryActivity.this,R.layout.list_messentry, messEntryArrayList);
                    ListView.setAdapter(adapter);
                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("Test", e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminMessEntryActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Test", error.toString());
                progressDialog.dismiss();
                Toast.makeText(AdminMessEntryActivity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

}
