package com.example.cultureevents.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.cultureevents.R;
import com.example.cultureevents.models.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omarshehe.forminputkotlin.FormInputAutoComplete;
import com.omarshehe.forminputkotlin.FormInputMultiline;
import com.omarshehe.forminputkotlin.FormInputText;

import java.util.Calendar;

import static com.example.cultureevents.utils.DbConstants.DB_EVENTS;

/**
 * Activity used for displaying news cards.
 */
public class NewEventActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference ref;

    Event event;
    FormInputText name;
    FormInputMultiline description;
    FormInputAutoComplete location;

    Switch switchTickets;
    Button saveBtn;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        initViews();
        initListeners();
    }

    private void initViews() {
        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        txtDate = findViewById(R.id.in_date);
        txtTime = findViewById(R.id.in_time);
        name = findViewById(R.id.nameField);
        description = findViewById(R.id.descriptionField);
        location = findViewById(R.id.locationField);
        switchTickets = findViewById(R.id.simpleSwitch);
        saveBtn = findViewById(R.id.save);
    }

    private void initListeners() {
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                ref = database.getReference(DB_EVENTS);
                String eventName = name.getValue();
                String eventDescription = description.getValue();
                String eventLocation = location.getValue();
                String time = txtTime.getText().toString();
                String date = txtDate.getText().toString();
                Boolean needTicket = switchTickets.getShowText();
                String image = "";
                String creationDate = String.valueOf(java.time.LocalDate.now());
                event = new Event(eventName, eventDescription, eventLocation, date, time, creationDate, image, needTicket);
                ref.child(eventName).setValue(event);
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view == btnDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == btnTimePicker) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}