package com.auribises.MyProject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auribises.MyProject.Activity.AdminActivity;
import com.auribises.MyProject.Activity.AdminLeavePermissionActivity;
import com.auribises.MyProject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FirstActivity extends AppCompatActivity {
    @InjectView(R.id.student)
    Button btnStudent;

    @InjectView(R.id.admin)
    Button btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.inject(this);
    }

    public  void onClickhandler(View view){
        if(view.getId()==R.id.student){
            Intent i= new Intent(FirstActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i =new Intent(FirstActivity.this,AdminLoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
