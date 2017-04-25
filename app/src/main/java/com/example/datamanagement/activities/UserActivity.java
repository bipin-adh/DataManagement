package com.example.datamanagement.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.datamanagement.R;
import com.example.datamanagement.adapter.CustomAdapter;
import com.example.datamanagement.fragment.AddToListDialogFragment;
import com.example.datamanagement.helper.DatabaseHelper;
import com.example.datamanagement.model.Task;

import java.util.ArrayList;
import java.util.List;




public class UserActivity extends AppCompatActivity implements CustomAdapter.CheckboxListener,View.OnClickListener,AddToListDialogFragment.DataEnteredListener {


    private Toolbar toolbar;
    private static final String TAG = UserActivity.class.getSimpleName();
    public static final String EXTRA_USER = "user_id";
    FloatingActionButton fab;
    DatabaseHelper myDb;
    List<Task> taskList;
    ListView listView;
//    EditText editText;
    AddToListDialogFragment dialogFragment;
    CustomAdapter customAdapter;
    CustomAdapter.CheckboxListener checkboxListener;
    public String userActive;







    public void initView(){

        fab = (FloatingActionButton)findViewById(R.id.fab_addtask);
        checkboxListener = (CustomAdapter.CheckboxListener) this;


//        editText =(EditText)findViewById(R.id.edittext_listdata);
          }


    @Override
    public void OnDataEntered(String enteredData){
        Log.d(TAG, "OnDataEntered:" + enteredData);
        addListData(enteredData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //String userActive;
        userActive = getIntent().getStringExtra(EXTRA_USER);
        Log.d(TAG, "onCreate: userActive  " + userActive);

        myDb = new DatabaseHelper(this);
        initView();
        viewListData();


        fab.setOnClickListener(this);

        toolbar =(Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(userActive);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        switch (id){

            case R.id.activity_user_logout :
                logoutUser();
                break;
        }
        return true;

    }

    @Override
    public void onBackPressed() {
        UserActivity.this.finish();
        super.onBackPressed();
    }

    //for logout button
    public void backToLoginPage(){

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }



    public void logoutUser(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                backToLoginPage();
                UserActivity.this.finish();
            }
        });


        builder.setNegativeButton("Cancel",null);



        builder.show();


    }

    private void showDialog(){
        Log.d(TAG, "showDialog: after fabbutton click");

        dialogFragment = new AddToListDialogFragment();
        dialogFragment.show(UserActivity.this.getFragmentManager(),"AddToListFragment");

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.fab_addtask:
                Log.d(TAG, "onClick: inside fabaddtask");
                showDialog();
                //addListData();
                break;


            //case R.id.viewListData:
                //viewListData();
              //  break;
        }

    }


    public void addListData(String entry) {

        String newEntry = entry;
        if (newEntry.length() == 0) {
            Toast.makeText(UserActivity.this, "you must write something", Toast.LENGTH_LONG).show();
        } else {
            AddData(newEntry);
            refreshData();
//            editText.setText("");
        }
    }

    private void refreshData() {
        taskList = myDb.getTasks(userActive);
        customAdapter.getData().clear();
        customAdapter.getData().addAll(taskList);
        customAdapter.notifyDataSetChanged();
    }

    public void AddData(String newEntry){
        Task task = new Task();
        task.setTaskName(newEntry);
        task.setChecked(false);
        task.setTaskUser(userActive);

        boolean insertData = myDb.insertListData(task);

        if(insertData){
            Toast.makeText(UserActivity.this, "data inserted successfully", Toast.LENGTH_LONG).show();
            dialogFragment.dismiss();

        }else{
            Toast.makeText(UserActivity.this, "error inserting data", Toast.LENGTH_LONG).show();
        }



    }

    public void viewListData(){
        Log.d(TAG, "viewListData");
        listView = (ListView)findViewById(R.id.listViewData);
        taskList = new ArrayList<>();
        customAdapter = new CustomAdapter(this,taskList,checkboxListener);
        listView.setAdapter(customAdapter);

        taskList = myDb.getTasks(userActive);

        if(taskList!=null && taskList.size()>0){
            refreshData();
        }else{
            Toast.makeText(UserActivity.this, "database empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckBoxTick(Task task, boolean isChecked) {

        Log.d(TAG, "onCheckBoxTick:" + task.getTaskName());

        task = task.setChecked(isChecked);
        
        boolean uiUpdated = myDb.updateData(task);
        Log.d(TAG, "onCheckBoxTick: database updated");
        
        if(uiUpdated){
            Toast.makeText(UserActivity.this, "database updated", Toast.LENGTH_LONG).show();
            refreshData();

        }else{
            Toast.makeText(UserActivity.this, "error updating database", Toast.LENGTH_LONG).show();
        }



    }
}
