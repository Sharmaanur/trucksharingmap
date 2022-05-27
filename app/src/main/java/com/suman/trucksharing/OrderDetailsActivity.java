package com.suman.trucksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.suman.trucksharing.DB.Data;
import com.suman.trucksharing.DB.ItemModel;
import com.suman.trucksharing.DB.ItemsDBHandler;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView sender, pickuptime, receiver, dropofftime, weight, type, width, height, length, qty;
    ImageView img;
    Button btnEstimate;
    ItemsDBHandler itemsDBHandler;
    ItemModel itemModel;
    int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        img = findViewById(R.id.imageView);
        sender = findViewById(R.id.txt_sender);
        pickuptime = findViewById(R.id.txt_pickup_time);
        receiver = findViewById(R.id.txt_receiver);
        dropofftime = findViewById(R.id.txt_drop_off_time);
        weight = findViewById(R.id.txt_weight);
        type = findViewById(R.id.txt_type);
        width = findViewById(R.id.txt_width);
        height = findViewById(R.id.txt_height);
        length = findViewById(R.id.txt_length);
        qty = findViewById(R.id.txt_quantity);
        btnEstimate = findViewById(R.id.button_estimate);
        byte[] decodedString = Base64.decode(Data.image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);
        itemsDBHandler = new ItemsDBHandler(this);
        ArrayList<ItemModel> list = itemsDBHandler.getAllItems();

        Intent intent = getIntent();
        try {
            itemModel = list.get(intent.getIntExtra("position",0));
            sender.setText("From "+intent.getStringExtra("name"));
            pickuptime.setText(intent.getStringExtra("time"));
            receiver.setText("To Me");
            //dropofftime.setText(intent.getStringExtra(""));
            amount = Integer.parseInt(intent.getStringExtra("weight"))*2+5;
            weight.setText("Weight\n"+intent.getStringExtra("weight"));
            type.setText("Goods Type\n"+intent.getStringExtra("type"));
            width.setText("Width\n"+intent.getStringExtra("width"));
            height.setText("Height\n"+intent.getStringExtra("height"));
            length.setText("Length\n"+intent.getStringExtra("length"));
            qty.setText("Quantity\n1");
        }catch (Exception e){
            e.printStackTrace();
        }
        btnEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent direction = new Intent(OrderDetailsActivity.this, Direction.class);
                direction.putExtra("picklocation", itemModel.getPickuplocation());
                direction.putExtra("droplocation", itemModel.getDroplocation());
                direction.putExtra("from", itemModel.getFromCoordinates());
                direction.putExtra("to", itemModel.getToCoordinates());
                direction.putExtra("amount", amount);
                startActivity(direction);
            }
        });
    }
}