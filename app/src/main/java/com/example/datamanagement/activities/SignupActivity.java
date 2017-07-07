package com.example.datamanagement.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datamanagement.R;
import com.example.datamanagement.helper.DatabaseHelper;
import com.example.datamanagement.model.Contact;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG =SignupActivity.class.getSimpleName();
    DatabaseHelper myDb;
    EditText editTextName, editTextEmail, editTextPassword, editTextCpassword;
    Button btnReg;

    TextView textViewBackToLogin;

    //Button btnViewAllData;

    public void initView()
    {
        Log.d(TAG, "initView: ");
        editTextName = (EditText) findViewById(R.id.edittext_name);
        editTextEmail = (EditText) findViewById(R.id.edittext_email);
        editTextPassword = (EditText) findViewById(R.id.edittext_password);
        editTextCpassword = (EditText) findViewById(R.id.edittext_cPassword);

        textViewBackToLogin =(TextView)findViewById(R.id.textview_signin);


        btnReg = (Button) findViewById(R.id.button_signup);

      //  btnViewAllData = (Button) findViewById(R.id.button_viewAllData);

        btnReg.setOnClickListener(this);
        textViewBackToLogin.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        myDb = new DatabaseHelper(this);
        initView();

        //btnViewAllData.setOnClickListener(this);
    }

    public void accountExistsDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Registration");
        builder.setMessage("Account already exists under that email");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SignupActivity.this.finish();

            }
        });
        builder.show();


    }

    public void backToLoginPage(){

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }





    public void accountCreatedDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Registration");
        builder.setMessage("Your account has been succesfully created");
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backToLoginPage();
                SignupActivity.this.finish();

            }
        });
        builder.show();


    }



        public void AddData () {
            String pass = editTextPassword.getText().toString();
            String name1 = editTextName.getText().toString();
            String email1 = editTextEmail.getText().toString();

            String password1 = editTextPassword.getText().toString();
            String cpassword1 = editTextCpassword.getText().toString();



            if(TextUtils.isEmpty(name1)){

                editTextName.setError("Name missing");
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {

                editTextEmail.setError("invalid username! eg- abc@hotmail.com");
                editTextEmail.requestFocus();
            }else {


                if (TextUtils.isEmpty(pass) || pass.length() < 8) {

                    editTextPassword.setError("password must have 8 characters");
                    return;

                } else {

                    // take data from edittext and pass it to insertdata function .

                    if (password1.equals(cpassword1)) {

                        Contact contact = new Contact();
                        contact.setName(name1);
                        contact.setEmail(email1);
                        contact.setPassword(password1);

                        /*
                        boolean isInserted = myDb.insertData(contact);


                        if (isInserted) {

                            Toast.makeText(SignupActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                            accountCreatedDialog();
                        } else {
                            Toast.makeText(SignupActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                        }

                        */


                        boolean duplicateDataChecked = myDb.checkDuplicateEntries(contact);

                        if (duplicateDataChecked) {
                            accountCreatedDialog();
                            //Toast.makeText(SignupActivity.this, "Account has been created", Toast.LENGTH_LONG).show();

                        } else {
                            accountExistsDialog();
                            //Toast.makeText(SignupActivity.this, "Account under that username already created", Toast.LENGTH_LONG).show();
                        }


                    } else {

                        Toast.makeText(SignupActivity.this, "Password and Confirm Password dont match", Toast.LENGTH_LONG).show();

                    }
                }

            }

        }

    /*public void getAll(){

            Cursor res = myDb.getAllData();
            if(res.getCount()==0){

                //no data
                showMessage("Error", "Nothing found");

                return;
            }else {

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Email: " + res.getString(2) + "\n");
                    buffer.append("Password: " + res.getString(3) + "\n\n");


                }

                // show message
                showMessage("Data", buffer.toString());
            }


        }

    //displays data on screen
    public  void showMessage(String title, String message){

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    */

            @Override
            public void onClick (View v){

                int id = v.getId();


                switch (id){

                    case R.id.button_signup:
                        AddData();
                        break;
                    case R.id.textview_signin:
                        backToLoginPage();
                        break;

                    /*case R.id.button_viewAllData:
                        getAll();
                        break; */



                }

            }




}


