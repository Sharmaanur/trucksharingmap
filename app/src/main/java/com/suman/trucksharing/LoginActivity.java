package com.suman.trucksharing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.suman.trucksharing.DB.Data;
import com.suman.trucksharing.DB.ItemModel;
import com.suman.trucksharing.DB.UserDBHandler;
import com.suman.trucksharing.DB.UserModel;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    UserDBHandler loginDBHandler;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDBHandler = new UserDBHandler(this);
        username = findViewById(R.id.ed_login_username);
        password = findViewById(R.id.ed_login_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel =  new UserModel();
                userModel.setUsername(username.getText().toString());
                userModel.setPassword(password.getText().toString());
                boolean auth = loginDBHandler.validateUser(userModel);
                if (auth){
                    Data.username = username.getText().toString();
                    Data.image = loginDBHandler.getUserInfo(username.getText().toString()).get(0).getImage();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid Username Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}