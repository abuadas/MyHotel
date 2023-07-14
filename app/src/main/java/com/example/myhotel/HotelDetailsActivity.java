package com.example.myhotel;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HotelDetailsActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView locationTextView;
    private TextView ratingTextView;
    private TextView capacityTextView;
    private TextView priceTextView;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        nameTextView = findViewById(R.id.nameTextView);
        locationTextView = findViewById(R.id.locationTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        capacityTextView = findViewById(R.id.capacityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        reserveButton = findViewById(R.id.reserveButton);

        // Get the hotel object from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("hotel")) {
            Hotel hotel = intent.getParcelableExtra("hotel");
            if (hotel != null) {
                // Set the hotel details in the TextViews
                nameTextView.setText(hotel.getName());
                locationTextView.setText(hotel.getLocation());
                ratingTextView.setText(String.valueOf(hotel.getRating()));
                capacityTextView.setText(String.valueOf(hotel.getCapacity()));
                priceTextView.setText(String.valueOf(hotel.getPrice()));

                // Handle reserve button click
                reserveButton.setOnClickListener(view -> {
                    // Perform reservation logic here
                    // Replace with your reservation implementation
                    Toast.makeText(HotelDetailsActivity.this, "Hotel reserved", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }
}
