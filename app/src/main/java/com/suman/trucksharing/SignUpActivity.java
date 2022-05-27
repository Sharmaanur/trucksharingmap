package com.suman.trucksharing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.suman.trucksharing.DB.UserDBHandler;
import com.suman.trucksharing.DB.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class SignUpActivity extends AppCompatActivity {
    UserDBHandler userDBHandler;
    EditText fullname, username, confpass, password, phoneno;
    ImageView pickImage;
    Bitmap bitmap;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userDBHandler = new UserDBHandler(this);
        pickImage = findViewById(R.id.pick_image);
        fullname = findViewById(R.id.ed_fullname);
        username = findViewById(R.id.ed_username);
        password = findViewById(R.id.ed_password);
        confpass = findViewById(R.id.conf_pass);
        phoneno = findViewById(R.id.phone_number);
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    return;
                }
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Browse Image"),666);
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signUp();
            }
        });
    }
    void signUp(){
        if (password.getText().toString().equals(confpass.getText().toString())) {
            UserModel userModel = new UserModel();
            userModel.setImage(image);
            userModel.setFullname(fullname.getText().toString());
            userModel.setUsername(username.getText().toString());
            userModel.setPassword(password.getText().toString());
            userModel.setPhoneno(phoneno.getText().toString());
            userDBHandler.addUser(userModel);
            Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(SignUpActivity.this, "Password are not same", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if(requestCode==666 && resultCode==RESULT_OK)
            {
                Uri filepath=data.getData();
                try
                {
                    InputStream inputStream=getContentResolver().openInputStream(filepath);
                    bitmap= BitmapFactory.decodeStream(inputStream);
                    pickImage.setImageBitmap(bitmap);
                    image = encodeBitmapImage(bitmap);
                    Log.e("TAG", image);
                }catch (Exception ex)
                {

                }
            }
        }
    }
    private String encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }
}