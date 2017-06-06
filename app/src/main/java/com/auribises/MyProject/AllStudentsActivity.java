package com.auribises.MyProject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.MyProject.Adapter.AllStudentAdapter;
import com.auribises.MyProject.Bean.AllStudent;
import com.auribises.MyProject.Bean.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AllStudentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<AllStudent> studentList;

    AllStudentAdapter adapter;

    AllStudent allstudent;
    int pos;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

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
                if (adapter != null) {
                    adapter.filter(str);
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

        studentList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.Retrieve_STUDENTTABLE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("students");

                    int id = 0;
                    String n = "", r = "", c = "", rn ="" , h = "", ph = "", e = "", a = "", s = "";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("name");
                        r = jObj.getString("rollno");
                        c = jObj.getString("course");
                        rn =jObj.getString("roomno");
                        h = jObj.getString("hostel");
                        ph = jObj.getString("phone");
                        e = jObj.getString("email");
                        a = jObj.getString("address");
                        s = jObj.getString("state");

                        studentList.add(new AllStudent(id, n, r, c, rn, h, ph, e, a, s));
                    }
                    Log.i("test","1 - "+studentList.toString());
                    adapter = new AllStudentAdapter(AllStudentsActivity.this, R.layout.list_item_all, studentList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(AllStudentsActivity.this);

                    progressDialog.dismiss();

                } catch (Exception e) {
                    Log.i("test", e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllStudentsActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", error.toString());
                progressDialog.dismiss();
                Toast.makeText(AllStudentsActivity.this, "Some Error", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        allstudent = studentList.get(i);
        showOptions();
    }

    void showOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"View", "Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        showallstudent();
                        break;

                    case 1:
                        deleteallstudent();
                        break;
                }

            }
        });


        builder.create().show();
    }

    void showallstudent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of " + allstudent.getName());
        builder.setMessage(allstudent.toString());
        builder.setPositiveButton("Done", null);
        builder.create().show();
    }

    void deleteallstudent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + allstudent.getName());
        builder.setMessage("Do you wish to Delete?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                deleteFromCloud();

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    void deleteFromCloud() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.Delete_STUDENTTABLE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        studentList.remove(pos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AllStudentsActivity.this, message, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AllStudentsActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllStudentsActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AllStudentsActivity.this, "Some Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(allstudent.getId()));
                return map;
            }
        };

        requestQueue.add(request); // Execution of HTTP Request

    }
}

