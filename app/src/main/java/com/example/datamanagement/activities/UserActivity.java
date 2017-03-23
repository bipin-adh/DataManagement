package com.example.datamanagement.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.datamanagement.R;
import com.example.datamanagement.adapter.CustomAdapter;
import com.example.datamanagement.helper.DatabaseHelper;
import com.example.datamanagement.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDb;
    Button btnAdd ;// btnView;
    EditText editText;

    public void initView(){

        editText = (EditText)findViewById(R.id.edittext_listdata);
        btnAdd = (Button)findViewById(R.id.addListData);
        //btnView = (Button)findViewById(R.id.viewListData);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


       myDb = new DatabaseHelper(this);
        initView();

        btnAdd.setOnClickListener(this);
        //btnView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.addListData:
                addListData();
                viewListData();
                break;

            //case R.id.viewListData:
                //viewListData();
              //  break;
        }

    }

    public void addListData(){
        String newEntry = editText.getText().toString();
        if(editText.equals(0)){
            Toast.makeText(UserActivity.this, "you must write something", Toast.LENGTH_LONG).show();

        }else{
            AddData(newEntry);
            editText.setText("");
        }
    }

    public void AddData(String newEntry){
        boolean insertData = myDb.insertListData(newEntry);

        if(insertData){
            Toast.makeText(UserActivity.this, "data inserted succesfully", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(UserActivity.this, "error inserting data", Toast.LENGTH_LONG).show();
        }

    }

    public void viewListData(){

        ListView listView = (ListView)findViewById(R.id.listViewData);
        //ArrayList<String> theList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();

        Cursor data = myDb.getListData();

        if(data.getCount()==0){

            Toast.makeText(UserActivity.this, "database empty", Toast.LENGTH_LONG).show();

        }else{

            while(data.moveToNext()){
                Contact contact = new Contact();
                contact.getName() = data.getString(1);
                //ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                //listView.setAdapter(listAdapter);
                CustomAdapter customAdapter = new CustomAdapter(this,contact);
                listView.setAdapter(customAdapter);
            }
        }
    }
}
