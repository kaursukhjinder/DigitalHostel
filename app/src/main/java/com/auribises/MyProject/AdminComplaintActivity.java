package com.auribises.MyProject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.MyProject.Adapter.ComplaintAdapter;
import com.auribises.MyProject.Bean.Complaint;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdminComplaintActivity extends AppCompatActivity {

    @InjectView(R.id.ListView)
    ListView listView;

    @InjectView(R.id.editsearchC)
    EditText editSearch;

    ArrayList<Complaint> complaintList;

    Complaint complaint;
    ProgressDialog progressDialog;
    ComplaintAdapter adapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint);
        ButterKnife.inject(this);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        editSearch.addTextChangedListener(new TextWatcher() {
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
        complaintList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.COMPLAINT_RETRIEVE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("students");

                    int id=0;
                    String n="",r="",c="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("name");
                        r = jObj.getString("roomno");
                        c= jObj.getString("complaint");

                        complaintList.add(new Complaint(id,n,r,c));
                    }

                    adapter = new ComplaintAdapter(AdminComplaintActivity.this,R.layout.complaint_list, complaintList);
                    listView.setAdapter(adapter);

                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminComplaintActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AdminComplaintActivity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }


}
