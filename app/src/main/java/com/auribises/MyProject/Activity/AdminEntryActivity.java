package com.auribises.MyProject.Activity;

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
import com.auribises.MyProject.Adapter.EntryAdapter;
import com.auribises.MyProject.Bean.Entry;
import com.auribises.MyProject.R;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdminEntryActivity extends AppCompatActivity {

    @InjectView(R.id.listViewEntry)
    ListView ListViewE;

    @InjectView(R.id.editTextSearchEntry)
    EditText EntrySearch;

    ArrayList<Entry> entryList;
    EntryAdapter entryAdapter;
    int pos;
    Entry entry;
    RequestQueue requestQueue;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_entry);

        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);


       EntrySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (entryAdapter != null) {
                    entryAdapter.filter(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        retrieveFromCloud();
    }


    void retrieveFromCloud() {

        progressDialog.show();

        entryList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.ENTRY_RETRIEVE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("students");

                    int id = 0;
                    String n = "", r = "",t="",d="";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("name");
                        r = jObj.getString("rollno");
                        t = jObj.getString("time");
                        d = jObj.getString("date");


                        entryList.add(new Entry(id, n, r,t,d));
                    }
                    Log.i("test","1 - "+entryList.toString());
                    entryAdapter = new EntryAdapter(AdminEntryActivity.this, R.layout.list_entry, entryList);
                    ListViewE.setAdapter(entryAdapter);

                    progressDialog.dismiss();

                } catch (Exception e) {
                    Log.i("test", e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminEntryActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", error.toString());
                progressDialog.dismiss();
                Toast.makeText(AdminEntryActivity.this, "Some Error", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

}
