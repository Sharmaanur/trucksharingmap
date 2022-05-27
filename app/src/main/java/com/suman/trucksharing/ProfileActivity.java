package com.suman.trucksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.suman.trucksharing.DB.Data;
import com.suman.trucksharing.DB.ItemModel;
import com.suman.trucksharing.DB.UserDBHandler;
import com.suman.trucksharing.DB.UserModel;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    UserDBHandler userDBHandler;
    TextView name, username, phone;
    ImageView avatar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.txt_name);
        username = findViewById(R.id.txt_username);
        phone = findViewById(R.id.txt_phone);
        avatar = findViewById(R.id.avatar);
        userDBHandler = new UserDBHandler(this);
        ArrayList<UserModel> userInfo = userDBHandler.getUserInfo(Data.username);

        byte[] decodedString = Base64.decode(userInfo.get(0).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        avatar.setImageBitmap(decodedByte);

        name.setText("Name - "+userInfo.get(0).getFullname());
        username.setText("Username - "+userInfo.get(0).getUsername());
        phone.setText("Phone - "+userInfo.get(0).getPhoneno());
    }
}