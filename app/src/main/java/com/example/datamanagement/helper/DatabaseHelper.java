package com.example.datamanagement.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.datamanagement.activities.SignupActivity;
import com.example.datamanagement.model.Contact;
import com.example.datamanagement.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b1p1n on 1/24/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String TAG = DatabaseHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_USER = "UserData";
    public static final int DATABASE_VERSION = 3;
    public static final String COL_ID = "ID";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";


    public static final String TABLE_LIST = "List";
    public static final String COL_TASK_NAME = "task";
    public static final String COL_TASK_STATUS = "status";
    public static final String COL_TASK_USER = "user";


    String QUERY_CREATE_USER = "CREATE TABLE " + TABLE_USER + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_USER_NAME + " TEXT, " +
            COL_USER_EMAIL + " TEXT UNIQUE, " +
            COL_USER_PASSWORD + " TEXT " +
            " ) ";

    String QUERY_CREATE_TASK = "CREATE TABLE " + TABLE_LIST + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_TASK_NAME + " TEXT, " +
            COL_TASK_STATUS + " TEXT, " +
            COL_TASK_USER + " TEXT " +
            " ) ";



    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_USER);
        db.execSQL(QUERY_CREATE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(db);

    }

    public boolean checkDuplicateEntries(Contact c){

        boolean success = false;
        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_NAME, c.getName());
        contentValues.put(COL_USER_EMAIL, c.getEmail());
        contentValues.put(COL_USER_PASSWORD, c.getPassword());

        try{
            db.insertOrThrow(TABLE_USER,null,contentValues);
            success = true;
        }catch (SQLException e){
            e.printStackTrace();
            //Toast.makeText(signupActivity.this, "Account already exists on that username", Toast.LENGTH_LONG).show();

        }
        db.close();
        return success;


    }
    /*
    // check whether data is added to database or not

    public boolean insertData(Contact c) {


        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_NAME, c.getName());
        contentValues.put(COL_USER_EMAIL, c.getEmail());
        contentValues.put(COL_USER_PASSWORD, c.getPassword());


      long result =  db.insert(TABLE_USER, null, contentValues); // returns -1 if no data is inserted
        db.close();
      if (result == -1)
            return false;
        else
            return true;

    }
    */

    public boolean insertListData(Task task){

        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TASK_NAME,task.getTaskName());
        contentValues.put(COL_TASK_STATUS, task.isChecked()?1:0);
        contentValues.put(COL_TASK_USER, task.getTaskUser());

        long result =  db.insert(TABLE_LIST, null, contentValues); // returns -1 if no data is inserted
        db.close();
        if (result == -1)
            return false;
        else
            return true;


    }

    public String searchPass(String struser ) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select email , password from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);


        String savedPassword;
        String savedUsername;

        savedPassword = "username not found";
        if (cursor.moveToFirst()) {

            do {

                savedUsername = cursor.getString(0);

                if (savedUsername.equals(struser)) {
                    savedPassword = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());



        }

        return  savedPassword;

    }




    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_USER, null);
        return res;


    }

    public List<Task> getTasks(String activeUser){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> taskList = new ArrayList<>();


        String query = "select * from " + TABLE_LIST + " where  user = '" + activeUser +"' ";
        Log.e(TAG, query );
        Cursor cursor = db.rawQuery(query,null);
        Log.e(TAG, query );
        //Cursor cursor = db.rawQuery("select * from  TABLE_LIST  where  COL_TASK_USER = "+email+" ",null)
        // "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + MESSAGE_USERNAME + " = " + username
        //"select * from " + TABLE_LIST + " where " + COL_TASK_USER + " = " + activeUser
        if(cursor.getCount()==0){
            taskList = null;
        }else{

            while(cursor.moveToNext()){
                Task task = new Task();
                String name = cursor.getString(cursor.getColumnIndex(COL_TASK_NAME));
                Log.e("DBHelper", name);
                boolean isChecked = true;
                int checkedStatus = cursor.getInt(cursor.getColumnIndex(COL_TASK_STATUS));
                String user = cursor.getString(cursor.getColumnIndex(COL_TASK_USER));
                Log.e("DBHelper", user);
                Log.e(TAG, "getTasks: user" );
                if(checkedStatus == 0){
                    isChecked = false;
                }
                task.setTaskName(name);
                task.setChecked(isChecked);
                task.setTaskUser(user);
                taskList.add(task);
            }
        }
        db.close();
        return  taskList;
    }

}