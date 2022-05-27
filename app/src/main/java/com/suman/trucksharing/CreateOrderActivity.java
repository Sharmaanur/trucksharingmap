package com.suman.trucksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.suman.trucksharing.DB.Data;
import com.suman.trucksharing.DB.ItemModel;
import com.suman.trucksharing.DB.ItemsDBHandler;

public class CreateOrderActivity extends AppCompatActivity {
    Spinner goods, vehicle;
    String name, date, time, pickloc, droploc, fromcord, tocord, goodstype, weight, width, length, height, vehicletype;
    EditText mWeight, mWidth, mLength, mHeight;
    ItemsDBHandler itemsDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        itemsDBHandler = new ItemsDBHandler(this);
        goods = findViewById(R.id.spinner_goods);
        vehicle = findViewById(R.id.spinner_vehicle);
        mWeight = findViewById(R.id.ed_weight);
        mWidth = findViewById(R.id.ed_width);
        mLength = findViewById(R.id.ed_length);
        mHeight = findViewById(R.id.ed_height);

        try {
            Intent intent = getIntent();
            if (intent.hasExtra("name") && intent.hasExtra("date")){
                name = intent.getStringExtra("name");
                date = intent.getStringExtra("date");
                time = intent.getStringExtra("time");
                pickloc = intent.getStringExtra("picklocation");
                droploc = intent.getStringExtra("droplocation");
                fromcord = intent.getStringExtra("from");
                tocord = intent.getStringExtra("to");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        goods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                goodstype = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vehicletype = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        findViewById(R.id.btn_create_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight = mWeight.getText().toString().trim();
                width = mWidth.getText().toString().trim();
                length = mLength.getText().toString().trim();
                height = mHeight.getText().toString().trim();
                ItemModel itemModel = new ItemModel();
                itemModel.setName(name);
                itemModel.setDate(date);
                itemModel.setTime(time);
                itemModel.setPickuplocation(pickloc);
                itemModel.setDroplocation(droploc);
                itemModel.setFromCoordinates(fromcord);
                itemModel.setToCoordinates(tocord);
                itemModel.setGoodstype(goodstype);
                itemModel.setWeight(weight);
                itemModel.setWidth(width);
                itemModel.setLength(length);
                itemModel.setHeight(height);
                itemModel.setVehicletype(vehicletype);
                itemModel.setUsername(Data.username);
                itemsDBHandler.addItem(itemModel);
                startActivity(new Intent(CreateOrderActivity.this, HomeActivity.class));
            }
        });
    }
}