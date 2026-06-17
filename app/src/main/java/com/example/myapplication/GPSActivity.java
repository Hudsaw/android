package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class GPSActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private TextView tvGPS;
    private Button btnGPS, btnVoltar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        tvGPS = findViewById(R.id.tvGPS);
        btnGPS = findViewById(R.id.btnGPS);
        btnVoltar = findViewById(R.id.btnVoltar);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnGPS.setOnClickListener(v -> checkAndGetLocation());

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }

    private void checkAndGetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            requisitandoPermissao();
            return;
        }
        getLocation();
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            updateLocationUI(latitude, longitude);
            Toast.makeText(this, "Localização obtida!", Toast.LENGTH_SHORT).show();
        } else {
            tvGPS.setText("Localização não disponível\nTente novamente");
            Toast.makeText(this, "Não foi possível obter a localização", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateLocationUI(double latitude, double longitude) {
        if (tvGPS != null) {
            tvGPS.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
        }
    }

    public void requisitandoPermissao() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                REQUEST_LOCATION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Permissão negada! Não é possível obter localização.", Toast.LENGTH_LONG).show();
            }
        }
    }
}