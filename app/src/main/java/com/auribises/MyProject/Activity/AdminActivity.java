package com.auribises.MyProject.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.auribises.MyProject.AdminComplaintActivity;
import com.auribises.MyProject.AdminLoginActivity;
import com.auribises.MyProject.AdminMessEntryActivity;
import com.auribises.MyProject.AllStudentsActivity;
import com.auribises.MyProject.AttendenceFragment;
import com.auribises.MyProject.R;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

     AttendenceFragment attendenceFragment;
    FragmentManager fragmentManager;

    void initViews() {
        attendenceFragment=new AttendenceFragment();
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.frame_admin, attendenceFragment).commit();

    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            initViews();
        }


        @Override
        public void onBackPressed () {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_admin, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.all_students) {
                Intent i = new Intent(AdminActivity.this, AllStudentsActivity.class);
                startActivity(i);
                return true;

            }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.LeavePermission) {
                Intent intent = new Intent(AdminActivity.this, AdminLeavePermissionActivity.class);
                startActivity(intent);

            } else if (id == R.id.Entry) {
                Intent intent = new Intent(AdminActivity.this, AdminEntryActivity.class);
                startActivity(intent);


            } else if (id == R.id.MessEntry) {
                Intent intent = new Intent(AdminActivity.this, AdminMessEntryActivity.class);
                startActivity(intent);

            } else if (id == R.id.Admincomplaint) {
                Intent intent = new Intent(AdminActivity.this, AdminComplaintActivity.class);
                startActivity(intent);

            } else if (id == R.id.HostelLeave) {
                Intent intent = new Intent(AdminActivity.this, AdminLeaveEntryActivity.class);
                startActivity(intent);

            }else if (id == R.id.MessDue) {
                Intent intent = new Intent(AdminActivity.this, MessDueActivity.class);
                startActivity(intent);
            }else if (id == R.id.ChangePass) {
                Intent intent = new Intent(AdminActivity.this, AdminChangePasswordActivity.class);
                startActivity(intent);


            } else if (id == R.id.logout) {
                showAlertDialog();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Do you wish to Logout");
        builder.setCancelable(false); // If user will press the back key dialog will not be dismissed
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AdminActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    }
