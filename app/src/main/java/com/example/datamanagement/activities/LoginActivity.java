package com.example.datamanagement.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datamanagement.helper.DatabaseHelper;
import com.example.datamanagement.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    DatabaseHelper myDb = new DatabaseHelper(this);

    Button btnlogin;
    TextView txtviewSignup;
    EditText editTextEnteredLogin;
    EditText editTextEnteredPassword;

    public void initView() {
        btnlogin = (Button) findViewById(R.id.button_login);
        txtviewSignup = (TextView) findViewById(R.id.textview_signup);

        editTextEnteredLogin = (EditText) findViewById(R.id.edittext_loginusername);
        editTextEnteredPassword = (EditText) findViewById(R.id.edittext_loginpassword);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initView();


        btnlogin.setOnClickListener(this);

        txtviewSignup.setOnClickListener(this);


        //add toolbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    public void enterLogin(){

        String strUser = editTextEnteredLogin.getText().toString();
        String strPass = editTextEnteredPassword.getText().toString();



        if (!Patterns.EMAIL_ADDRESS.matcher(strUser).matches()) {

            editTextEnteredLogin.setError("invalid username! eg- abc@hotmail.com");
            editTextEnteredLogin.requestFocus();
        } else {

            if (TextUtils.isEmpty(strPass) || strPass.length() < 8) {

                editTextEnteredPassword.setError("password must have 8 characters");
                editTextEnteredPassword.requestFocus();
                return;

            } else {


                String password = myDb.searchPass(strUser);

                if (password.equals("username not found")) {
                    Toast.makeText(LoginActivity.this, "username not found", Toast.LENGTH_LONG).show();
                } else {


                    if (strPass.equals(password)) {
                        Toast.makeText(LoginActivity.this, "login succesfull", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this,UserActivity.class);
                        intent.putExtra(UserActivity.EXTRA_USER,strUser);
                        startActivity(intent);


                    } else {
                        Toast.makeText(LoginActivity.this, "login failed. password is incorrect", Toast.LENGTH_LONG).show();

                    }

                }


            }
        }
    }


    public void enterSignupPage() {


        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch(id){

            case R.id.button_login:
                enterLogin();
                break;
            case R.id.textview_signup:
                enterSignupPage();
                break;
        }

    }
}




