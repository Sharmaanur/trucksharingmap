package com.suman.trucksharing;

import static com.suman.trucksharing.DB.Data.username;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.suman.trucksharing.DB.ItemModel;
import com.suman.trucksharing.DB.ItemsDBHandler;
import com.suman.trucksharing.adapter.HomeAdapter;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeAdapter adapter;
    ItemsDBHandler itemsDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        recyclerView = findViewById(R.id.myorder_item);
        itemsDBHandler = new ItemsDBHandler(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        ArrayList<ItemModel> list = itemsDBHandler.getMyOrder(username);
        adapter = new HomeAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}