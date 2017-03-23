package com.example.datamanagement.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.datamanagement.model.Contact;

/**
 * Created by b1p1n on 1/24/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_NAME1 = "UserData";
    public static final int DATABASE_VERSION = 2;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "email";
    public static final String COL_4 = "password";


    public static final String TABLE_NAME2 = "List";
    public static final String COL2_1 = "ID";
    public static final String COL2_2 = "task";
    public static final String COL2_3 = "status";


    String SQL_String1 = "CREATE TABLE " + TABLE_NAME1 + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " TEXT " +
            " ) ";

    String SQL_String2 = "CREATE TABLE " + TABLE_NAME2 + " ( " +
            COL2_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2_2 + " TEXT, " +
            COL2_3 + " TEXT " +
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

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);

    }

    public boolean insertData(Contact c) {

        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, c.getName());
        contentValues.put(COL_3, c.getEmail());
        contentValues.put(COL_4, c.getPassword());



      long result =  db.insert(TABLE_NAME1, null, contentValues); // returns -1 if no data is inserted
        db.close();
      if (result == -1)
            return false;
        else
            return true;

    }

    public boolean insertListData(String item1){

        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_2,item1);
        long result =  db.insert(TABLE_NAME2, null, contentValues); // returns -1 if no data is inserted
        db.close();
        if (result == -1)
            return false;
        else
            return true;


    }

    public String searchPass(String struser ) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select email , password from " + TABLE_NAME1;
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
        Cursor res = db.rawQuery("select * from " + TABLE_NAME1, null);
        return res;


    }

    public  Cursor getListData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + TABLE_NAME2,null);
        return data;

    }
}