package com.example.myhotel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements LocationListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final String TAG = "DashboardActivity";

    private ConstraintLayout locationLayout;
    private ImageView searchImageView;
    private EditText searchEditText;
    private RecyclerView hotelsRecyclerView;
    private HotelAdapter hotelAdapter;
    private LocationManager locationManager;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        locationLayout = findViewById(R.id.locationLayout);
        searchImageView = findViewById(R.id.searchImageView);
        searchEditText = findViewById(R.id.searchEditText);
        hotelsRecyclerView = findViewById(R.id.hotelsRecyclerView);

        hotelAdapter = new HotelAdapter(new ArrayList<>(), new HotelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Hotel hotel) {
                Intent intent = new Intent(DashboardActivity.this, HotelDetailsActivity.class);
                intent.putExtra("hotel", hotel);
                startActivity(intent);
            }
        });
        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelsRecyclerView.setAdapter(hotelAdapter);

        // Check location permission and request if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Start location updates
            startLocationUpdates();
        }

        // Set up search functionality
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchEditText.getText().toString();
                performSearch(searchQuery);
            }
        });
    }

    private void startLocationUpdates() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                locationLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLocationSettings();
                    }
                });
            }
        }
    }

    private void showLocationSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private void performSearch(String searchQuery) {
        List<Hotel> searchResults = getSampleSearchResults(searchQuery);
        hotelAdapter.updateData(searchResults);
    }

    private List<Hotel> getSampleSearchResults(String searchQuery) {

        List<Hotel> searchResults = new ArrayList<>();
        List<Hotel> hotelsList = new ArrayList<>();

        for (Hotel hotel : hotelsList) {
            if (hotel.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(hotel);
            }
        }

        return searchResults;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location updates
                startLocationUpdates();
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Location permission denied. Unable to get current location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
        performLocationSearch();
    }

    private void performLocationSearch() {
        List<Hotel> nearbyHotels = getSampleNearbyHotels();
        hotelAdapter.updateData(nearbyHotels);
    }

    private List<Hotel> getSampleNearbyHotels() {
        List<Hotel> nearbyHotels = new ArrayList<>();

        Location userLocation = getCurrentLocation();

        Hotel[] hotelsList = new Hotel[0];
        for (Hotel hotel : hotelsList) {
            float distance = calculateDistance(userLocation.getLatitude(), userLocation.getLongitude(), hotel.getLatitude(), hotel.getLongitude());

            if (distance <= 5000) {
                nearbyHotels.add(hotel);
            }
        }

        return nearbyHotels;
    }

    private float calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
    }

    private Location getCurrentLocation() {
        Location location = new Location("");
        location.setLatitude(37.7749);
        location.setLongitude(-122.4194);

        return location;
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.d(TAG, "onProviderDisabled: " + provider);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.d(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged: " + provider + ", status: " + status);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
}
