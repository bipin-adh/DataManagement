package com.example.datamanagement.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by b1p1n on 1/24/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_NAME = "UserData";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "email";
    public static final String COL_4 = "password";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1); // database version = 1


    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_String = "CREATE TABLE " + TABLE_NAME + " ( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT " +
                " ) ";

        db.execSQL(SQL_String);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(Contact c) {

        SQLiteDatabase db = this.getWritableDatabase();
        // write data to database table
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, c.getName());
        contentValues.put(COL_3, c.getEmail());
        contentValues.put(COL_4, c.getPassword());



      long result =  db.insert(TABLE_NAME, null, contentValues); // returns -1 if no data is inserted
        db.close();
      if (result == -1)
            return false;
        else
            return true;

    }

    public String searchPass(String struser ) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select email , password from " + TABLE_NAME;
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
        Cursor res = db.rawQuery("select * from " + TABLE_NAME  , null);
        return res;


    }
}