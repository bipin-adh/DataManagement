package com.example.datamanagement.activities;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datamanagement.model.Contact;
import com.example.datamanagement.helper.DatabaseHelper;
import com.example.datamanagement.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDb;
    EditText editTextName, editTextEmail, editTextPassword, editTextCpassword;
    Button btnReg;
    Button btnViewAllData;

    public void initView()
    {
        editTextName = (EditText) findViewById(R.id.edittext_name);
        editTextEmail = (EditText) findViewById(R.id.edittext_email);
        editTextPassword = (EditText) findViewById(R.id.edittext_password);
        editTextCpassword = (EditText) findViewById(R.id.edittext_cPassword);
        btnReg = (Button) findViewById(R.id.button_signup);
        btnViewAllData = (Button) findViewById(R.id.button_viewAllData);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        myDb = new DatabaseHelper(this);

        initView();

        btnReg.setOnClickListener(this);
        btnViewAllData.setOnClickListener(this);


    }



        public void AddData () {
            String pass = editTextPassword.getText().toString();


            if(TextUtils.isEmpty(pass) || pass.length()<8){

              editTextPassword.setError("password must have 8 characters");
                return;

            }else {

                // take data from edittext and pass it to insertdata function .
                String name1 = editTextName.getText().toString();
                String email1 = editTextEmail.getText().toString();
                String password1 = editTextPassword.getText().toString();
                String cpassword1 = editTextCpassword.getText().toString();

                if (password1.equals(cpassword1)) {

                    Contact contact = new Contact();
                    contact.setName(name1);
                    contact.setEmail(email1);
                    contact.setPassword(password1);

                    boolean isInserted = myDb.insertData(contact);

                    if (isInserted) {
                        Toast.makeText(SignupActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignupActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(SignupActivity.this, "Password and Confirm Password dont match", Toast.LENGTH_LONG).show();

                }
            }

        }
        public void getAll(){

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


            @Override
            public void onClick (View v){

                int id = v.getId();


                switch (id){

                    case R.id.button_signup:
                        AddData();
                        break;

                    case R.id.button_viewAllData:
                        getAll();
                        break;



                }

            }




}


