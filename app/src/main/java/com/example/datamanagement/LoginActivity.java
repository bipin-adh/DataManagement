package com.example.datamanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    DatabaseHelper myDb = new DatabaseHelper(this);

    Button btnlogin;

    public void initView() {
        btnlogin = (Button) findViewById(R.id.button_login);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initView();
        btnlogin.setOnClickListener(this);

        //add toolbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }


    public void registerPage(View view) {


        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {

        EditText editTextEnteredLogin = (EditText) findViewById(R.id.edittext_loginusername);
        EditText editTextEnteredPassword = (EditText) findViewById(R.id.edittext_loginpassword);

        String struser = editTextEnteredLogin.getText().toString();
        String strpass = editTextEnteredPassword.getText().toString();

        String loginPass = editTextEnteredPassword.getText().toString();
        String loginUser = editTextEnteredLogin.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(loginUser).matches()) {

            editTextEnteredLogin.setError("invalid username! eg- abc@hotmail.com");
            editTextEnteredLogin.requestFocus();
        } else {

            if (TextUtils.isEmpty(loginPass) || loginPass.length() < 8) {

                editTextEnteredPassword.setError("password must have 8 characters");
                editTextEnteredPassword.requestFocus();
                return;

            } else {


                String password = myDb.searchPass(struser);

                if (password.equals("username not found")) {
                    Toast.makeText(LoginActivity.this, "username not found", Toast.LENGTH_LONG).show();
                } else {


                    if (strpass.equals(password)) {
                        Toast.makeText(LoginActivity.this, "login succesfull", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "login failed. password is incorrect", Toast.LENGTH_LONG).show();

                    }

                }


            }
        }
    }
}




