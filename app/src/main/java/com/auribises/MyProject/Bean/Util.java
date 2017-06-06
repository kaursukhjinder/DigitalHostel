package com.auribises.MyProject.Bean;

import android.net.Uri;

/**
 * Created by aman on 11/04/17.
 */

public class Util {
    public static final String sharedPreferences = "sharedPreferences";
    // Information for my Database
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Students.db";

    // Information for my Table
    public static final String TAB_NAME = "StudentTable";
    public static final String COL_ID = "_ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_ROllNO = "ROLLNO";
    public static final String COL_COURSE = "COURSE";
    public static final String COL_ROOMNO = "ROOMNO";
    public static final String COL_HOSTEL= "HOSTEL";
    public static final String COL_PHONE = "PHONE";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_ADDRESS = "ADDRESS";
    public static final String COL_STATE = "STATE";




    public static final String TAB_NAME_ADMIN = "AdminLogin";
    public static final String COL_ID_ADMIN = "_ID";
    public static final String COL_USERNAME_ADMIN = "ADMINUSERNAME";
    public static final String COL_PASSWORD_ADMIN = "ADMINPASSWORD";

    public static final String TAB_NAME2 = "EntryTable";
    public static final String COL_ID2 = "_ID";
    public static final String COL_NAME2 = "NAME";
    public static final String COL_ROllNO2 = "ROLLNO";
    public static final String COL_TIME = "TIME";
    public static final String COL_DATE = "DATE";


    public static final String TAB_NAME6 = "LeaveEntry";
    public static final String COL_ID6 = "_ID";
    public static final String COL_NAME6 = "NAME";
    public static final String COL_ROllNO6 = "ROLLNO";
    public static final String COL_TIME6 = "TIME";
    public static final String COL_DATE6 = "DATE";

    public static final String TAB_NAME3= "MessEntryTable";
    public static final String COL_ID3 = "_ID";
    public static final String COL_NAME3 = "NAME";
    public static final String COL_ROllNO3 = "ROLLNO";
    public static final String COL_TIME3 = "TIME";
    public static final String COL_DATE3 = "DATE";

    public static final String TAB_NAME4 = "ComplaintTable";
    public static final String COL_ID4 = "_ID";
    public static final String COL_NAME4 = "NAME";
    public static final String COL_ROOMNO4 = "ROOMNO";
    public static final String COL_COMPLAINT = "COMPLAINT";

    public static final String TAB_NAME5 = "HostelLeavePermission";
    public static final String COL_ID5 = "_ID";
    public static final String COL_NAME5 = "NAME";
    public static final String COL_ROLLNO5 = "ROLLNO";
    public static final String COL_REASON= "REASON";

    public static final String TAB_NAME7= "Attendence";
    public static final String COL_ID7 = "_ID";
    public static final String COL_NAME7 = "NAME";
    public static final String COL_ROllNO7 = "ROLLNO";
    public static final String COL_TIME7 = "TIME";
    public static final String COL_DATE7 = "DATE";

    public static final String TAB_NAME8= "MessDue";
    public static final String COL_ID8 = "_ID";
    public static final String COL_NAME8 = "NAME";
    public static final String COL_EMAIL8 = "EMAIL";
    public static final String COL_MESSDUE = "MESSDUE";


    public static final String CREATE_TAB_QUERY = "create table StudentTable(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ROLLNO varchar(20)," +
            "COURSE varchar(256)," +
            "ROOMNO varchar(256)," +
            "HOSTEL varchar(256)," +
            "PHONE varchar(20)," +
            "EMAIL varchar(256)," +
            "PASSWORD varchar(256)," +
            "ADDRESS varchar(256)," +
            "STATE varchar(20)" +
            ")";


    public static final String CREATE_TAB_QUERY_ADMIN = "create table AdminLogin(" +
            "_ID integer primary key autoincrement," +
            "ADMINUSERNAME varchar(256)," +
            "ADMINPASSWORD varchar(256)" +
            ")";


    public static final String CREATE_TAB_QUERY2= "create table EntryTable(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ROLLNO varchar(20)," +
            "TIME varchar(256)," +
            "DATE varchar(256)," +
            "HOSTELSTATUS varchar(256)" +
            ")";

    public static final String CREATE_TAB_QUERY3= "create table MessEntryTable(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ROLLNO varchar(20)," +
            "TIME varchar(256)," +
            "DATE varchar(256)" +
            ")";

    public static final String CREATE_TAB_QUERY6= "create table LeaveEntry(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ROLLNO varchar(20)," +
            "TIME varchar(256)," +
            "DATE varchar(256)" +
            ")";

    public static final String CREATE_TAB_QUERY7= "create table Attendence(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ROLLNO varchar(20)," +
            "TIME varchar(256)," +
            "DATE varchar(256)" +
            ")";


    public static final String CREATE_TAB_QUERY4= "create table ComplaintTable(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ROOMNO varchar(20)," +
            "COMPLAINT varchar(256)" +
            ")";

    public static final String CREATE_TAB_QUERY5= "create table HostelLeavePermission(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ROllNO varchar(20)," +
            "REASON varchar(256)" +
            ")";

    public static final String CREATE_TAB_QUERY8= "create table MessDue(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "EMAIL varchar(256)," +
            "MESSDUE varchar(256)" +
            ")";

    public static final String PREFS_NAME = "DigitalHostelApp";
    public static final String KEY_NAME = "keyName";
    public static final String KEY_ROLLNO = "keyRollno";
    public static final String KEY_USERNAME = "keyUsername";
    public static final String KEY_PASSWORD = "keyPassword";
    public static final String KEY_PHONE = "keyPhone";
    public static final String KEY_EMAIL = "keyEmail";
    public static final String KEY_ADDRESS = "keyAddress";


    public static final String INSERT_STUDENTTABLE_PHP = "http://satvirb.esy.es/satvir/studenttableinsert.php";
    public static final String LOGIN_PHP  = "http://satvirb.esy.es/satvir/login.php";
    public static final String ENTRY_PHP  = "http://satvirb.esy.es/satvir/entry.php";
    public static final String MYPROFILE_PHP  = "http://satvirb.esy.es/satvir/MyProfileretrieve.php";
    public static final String MESSENTRY_PHP  = "http://satvirb.esy.es/satvir/messentry.php";
    public static final String COMPLAINT_PHP  = "http://satvirb.esy.es/satvir/complainttable.php";
    public static final String HOSTELLEAVE_PHP  = "http://satvirb.esy.es/satvir/hostelleave.php";
    public static final String UPDATE_PHP  = "http://satvirb.esy.es/satvir/updateProfile.php";
    public static final String ADMIN_LOGIN_PHP  = "http://satvirb.esy.es/satvir/adminlogin.php";
    public static final String ENTRY_RETRIEVE_PHP  = "http://satvirb.esy.es/satvir/Entryretrieve.php";
    public static final String COMPLAINT_RETRIEVE_PHP  = "http://satvirb.esy.es/satvir/Complaintretrieve.php";
    public static final String MESSENTRY_RETRIEVE_PHP  = "http://satvirb.esy.es/satvir/messentryRetrieve.php";
    public static final String LEAVEPERMISSION_RETRIEVE_PHP  = "http://satvirb.esy.es/satvir/LeavePermissionretrieve.php";
    public static final String LEAVE_ENTRY_PHP  = "http://satvirb.esy.es/satvir/leaveentry.php";
    public static final String LEAVE_RETRIEVE_PHP  = "http://satvirb.esy.es/satvir/LeaveEntryretrieve.php";
    public static final String FORGETPASS_PHP  = "http://satvirb.esy.es/satvir/ForgetPassword.php";
    public static final String ATTENDENCE_PHP  = "http://satvirb.esy.es/satvir/attendence.php";
    public static final String MESS_DUE_PHP  = "http://satvirb.esy.es/satvir/MessDue.php";
    public static final String Delete_STUDENTTABLE_PHP  = "http://satvirb.esy.es/satvir/studenttabledelete.php";
    public static final String Retrieve_STUDENTTABLE_PHP  = "http://satvirb.esy.es/satvir/studenttableretrieve.php";
    public static final String CHANGE_PASS_PHP  = "http://satvirb.esy.es/satvir/PasswordChange.php";

}
