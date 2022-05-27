package com.suman.trucksharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.suman.trucksharing.DB.ItemModel;
import com.suman.trucksharing.DB.ItemsDBHandler;
import com.suman.trucksharing.adapter.HomeAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeAdapter adapter;
    ItemsDBHandler itemsDBHandler;
    String username = "";
    ArrayList<ItemModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.listview_item);
        itemsDBHandler = new ItemsDBHandler(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        list = itemsDBHandler.getAllItems();

        adapter = new HomeAdapter(this, list);
        recyclerView.setAdapter(adapter);
        username = getIntent().getStringExtra("username");
        findViewById(R.id.floatingActionButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NewOrderActivity.class));
            }
        });
        adapter.setOnItemClickListener(new HomeAdapter.onItemClickListener() {
            @Override
            public void onitemClick(int position, String name, String date, String time, String weight, String type, String width, String height, String length) {
                Intent intent = new Intent(HomeActivity.this, OrderDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("name", name);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("weight", weight);
                intent.putExtra("type", type);
                intent.putExtra("width", width);
                intent.putExtra("height", height);
                intent.putExtra("length", length);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_my_order:
                startActivity(new Intent(HomeActivity.this, MyOrderActivity.class));
                break;
            case R.id.menu_account:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}