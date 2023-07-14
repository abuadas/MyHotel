package com.example.myhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReservationActivity extends AppCompatActivity {
    private static final String TAG = "ReservationActivity";
    private static final String RESERVATION_URL = "https://example.com/api/reservation";

    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        checkInDatePicker = findViewById(R.id.checkInDatePicker);
        checkOutDatePicker = findViewById(R.id.checkOutDatePicker);
        reserveButton = findViewById(R.id.reserveButton);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeReservation();
            }
        });
    }

    private void makeReservation() {
        // Get the selected check-in and check-out dates from the DatePickers
        int checkInDay = checkInDatePicker.getDayOfMonth();
        int checkInMonth = checkInDatePicker.getMonth();
        int checkInYear = checkInDatePicker.getYear();

        int checkOutDay = checkOutDatePicker.getDayOfMonth();
        int checkOutMonth = checkOutDatePicker.getMonth();
        int checkOutYear = checkOutDatePicker.getYear();

        // Create Calendar instances and set the selected dates
        Calendar checkInDate = Calendar.getInstance();
        checkInDate.set(checkInYear, checkInMonth, checkInDay);

        Calendar checkOutDate = Calendar.getInstance();
        checkOutDate.set(checkOutYear, checkOutMonth, checkOutDay);

        // Check if the selected check-out date is after the check-in date
        if (checkOutDate.before(checkInDate)) {
            Toast.makeText(this, "Invalid dates. Check-out date must be after check-in date.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Format the dates using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String checkInDateString = dateFormat.format(checkInDate.getTime());
        String checkOutDateString = dateFormat.format(checkOutDate.getTime());

        // Prepare the reservation JSON object
        JSONObject reservationObject = new JSONObject();
        try {
            reservationObject.put("check_in_date", checkInDateString);
            reservationObject.put("check_out_date", checkOutDateString);
            // Add any other necessary reservation details

            // Send the reservation request to the server
            sendReservationRequest(reservationObject);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating reservation JSON object", e);
        }
    }

    private void sendReservationRequest(JSONObject reservationObject) {
        // Send the reservation request to the server using Volley or any networking library
        // You can use the provided RESTful PHP service to handle the reservation logic on the server side

        // Replace with your network request implementation
        // ...

        // Upon successful reservation, display a success message or navigate to a confirmation page
        Toast.makeText(this, "Reservation successful", Toast.LENGTH_SHORT).show();
    }
}
