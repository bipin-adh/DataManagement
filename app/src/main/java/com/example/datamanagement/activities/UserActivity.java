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
import com.example.datamanagement.model.Task;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDb;
    Button btnAdd ;// btnView;
    EditText editText;
    CustomAdapter customAdapter;
    List<Task> taskList;
    ListView listView;

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
        viewListData();

        btnAdd.setOnClickListener(this);
        //btnView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.addListData:
                addListData();
                break;

            //case R.id.viewListData:
                //viewListData();
              //  break;
        }

    }

    public void addListData(){
        String newEntry = editText.getText().toString();
        if(newEntry.length()==0){
            Toast.makeText(UserActivity.this, "you must write something", Toast.LENGTH_LONG).show();

        }else{
            AddData(newEntry);
            refreshData();
            editText.setText("");
        }
    }

    private void refreshData() {
        taskList = myDb.getTasks();
        customAdapter.getData().clear();
        customAdapter.getData().addAll(taskList);
        customAdapter.notifyDataSetChanged();
    }

    public void AddData(String newEntry){
        Task task = new Task();
        task.setTaskName(newEntry);
        task.setChecked(false);
        boolean insertData = myDb.insertListData(task);

        if(insertData){
            Toast.makeText(UserActivity.this, "data inserted successfully", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(UserActivity.this, "error inserting data", Toast.LENGTH_LONG).show();
        }

    }

    public void viewListData(){
        listView = (ListView)findViewById(R.id.listViewData);
        taskList = new ArrayList<>();

        taskList = myDb.getTasks();

        if(taskList!=null && taskList.size()>0){
            customAdapter = new CustomAdapter(this,taskList);
            listView.setAdapter(customAdapter);
        }else{
            Toast.makeText(UserActivity.this, "database empty", Toast.LENGTH_SHORT).show();
        }
    }
}
