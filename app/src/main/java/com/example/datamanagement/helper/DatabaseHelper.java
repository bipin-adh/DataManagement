package com.example.datamanagement.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.datamanagement.activities.UserActivity;
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
    public static final int DATABASE_VERSION = 2;
    public static final String COLUMN_ID = "ID";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";


    public static final String TABLE_LIST = "List";
    public static final String TASK_NAME = "task";
    public static final String TASK_STATUS = "status";
    public static final String TASK_USER = "user";


    String SQL_String1 = "CREATE TABLE " + TABLE_USER + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            USER_EMAIL + " TEXT, " +
            USER_PASSWORD + " TEXT " +
            " ) ";

    String SQL_String2 = "CREATE TABLE " + TABLE_LIST + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TASK_NAME + " TEXT, " +
            TASK_STATUS + " TEXT, " +
            TABLE_USER + " TEXT " +
            " ) ";



    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_String1);
        db.execSQL(SQL_String2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(db);

    }

    public boolean insertData(Contact c) {

        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, c.getName());
        contentValues.put(USER_EMAIL, c.getEmail());
        contentValues.put(USER_PASSWORD, c.getPassword());



      long result =  db.insert(TABLE_USER, null, contentValues); // returns -1 if no data is inserted
        db.close();
      if (result == -1)
            return false;
        else
            return true;

    }

    public boolean insertListData(Task task){

        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_NAME,task.getTaskName());
        contentValues.put(TASK_STATUS, task.isChecked()?1:0);
        contentValues.put(TABLE_USER, task.getTaskUser());
        Log.d(TAG, "insertListData: user is " + task.getTaskUser());
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

    public List<Task> getTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> taskList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_LIST, null);
        if(cursor.getCount()==0){
            taskList = null;
        }else{

            while(cursor.moveToNext()){
                Task task = new Task();
                String name = cursor.getString(cursor.getColumnIndex(TASK_NAME));
                Log.e("DBHelper", name);
                boolean isChecked = true;
                int checkedStatus = cursor.getInt(cursor.getColumnIndex(TASK_STATUS));
                if(checkedStatus == 0){
                    isChecked = false;
                }
                task.setTaskName(name);
                task.setChecked(isChecked);
                taskList.add(task);
            }
        }
        return  taskList;
    }

}