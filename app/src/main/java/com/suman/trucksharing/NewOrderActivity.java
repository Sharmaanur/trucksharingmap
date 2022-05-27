package com.suman.trucksharing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NewOrderActivity extends AppCompatActivity {
    Calendar calendar;
    int year, month, day;
    private int mHour,mMinute;
    String orderDate, fromco, toco;
    EditText name, pLocation, dLocation;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        name = findViewById(R.id.receiver_name);
        pLocation = findViewById(R.id.ed_location);
        dLocation = findViewById(R.id.ed_droplocation);
        calendarView = findViewById(R.id.calender_view);
        orderDate = day+"/"+month+"/"+year;
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyD6WIvaTH38RsZRkfS8cQSRw0_LzSr8Pjw");
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                orderDate = day+"/"+month+"/"+year;
            }
        });
        pLocation.setFocusable(false);
        pLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(NewOrderActivity.this);
                pickresultLauncher.launch(intent);
            }
        });
        dLocation.setFocusable(false);
        dLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(NewOrderActivity.this);
                dropresultLauncher.launch(intent);
            }
        });
        findViewById(R.id.title_pickup_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(666);
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewOrderActivity.this, CreateOrderActivity.class);
                intent.putExtra("name", name.getText().toString().trim());
                intent.putExtra("date", orderDate);
                intent.putExtra("time", mHour+":"+mMinute );
                intent.putExtra("picklocation", pLocation.getText().toString());
                intent.putExtra("droplocation", dLocation.getText().toString());
                intent.putExtra("from", fromco);
                intent.putExtra("to", toco);
                startActivity(intent);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        if (id == 666) {
            return new TimePickerDialog(this, mopenSetListener, 12, 00, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    showDate(year, month+1, day);
                }
            };
    private TimePickerDialog.OnTimeSetListener mopenSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
        }
    };
    private void showDate(int year, int month, int day) {
        StringBuilder date = new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year);
        TextView txtDate = findViewById(R.id.title_pickup_date);
                txtDate.setText(new StringBuilder().append("Pickup date : ").append(date));
        orderDate = date.toString();
    }

    ActivityResultLauncher<Intent> pickresultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null){
                        Intent intent = result.getData();
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        pLocation.setText(place.getAddress());
                        fromco = place.getLatLng().toString().replace("lat/lng: (","").replace(")","");
                        Toast.makeText(NewOrderActivity.this, fromco, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> dropresultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null){
                        Intent intent = result.getData();
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        dLocation.setText(place.getAddress());
                        toco = place.getLatLng().toString().replace("lat/lng: (","").replace(")","");
                        Toast.makeText(NewOrderActivity.this, fromco, Toast.LENGTH_SHORT).show();

                    }

                }
            });
}