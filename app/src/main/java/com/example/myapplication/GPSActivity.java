package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class GPSActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    TextView tvGPS;
    MapView map;
    Marker marker;
    Button btnGPS, btnVoltar;
    LocationListener locationListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        tvGPS = findViewById(R.id.tvGPS);
        btnGPS = findViewById(R.id.btnGPS);
        btnVoltar = findViewById(R.id.btnVoltar);
        map = findViewById(R.id.mapView);

        Configuration.getInstance().setUserAgentValue(getPackageName());
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnGPS.setOnClickListener(v -> checkAndGetLocation());
        btnVoltar.setOnClickListener(v -> finish());
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
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    double velocidade = location.getSpeed();
                    updateLocationUI(latitude, longitude, velocidade);
                    Toast.makeText(GPSActivity.this, "Localização obtida!", Toast.LENGTH_SHORT).show();

                    GeoPoint userlocation = new GeoPoint(latitude, longitude, velocidade);
                    map.getController().setCenter(userlocation);
                    map.getController().setZoom(18.8);
                    map.getController().animateTo(userlocation);

                    if(marker == null){ marker = new Marker(map); }
                    marker.setPosition(userlocation);
                    marker.setTitle("Tú estás aqui");
                    map.getOverlays().add(marker);
                }
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        tvGPS.setText("Buscando localização...");
        Toast.makeText(this, "Aguardando sinal GPS...", Toast.LENGTH_SHORT).show();
    }

    private void updateLocationUI(double latitude, double longitude, double velocidade) {
        if (tvGPS != null) {
            tvGPS.setText("Latitude: " + latitude + "\nLongitude: " + longitude + "\nVelocidade: " + velocidade*3.6);
        }
    }

    private void requisitandoPermissao() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION},
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
                tvGPS.setText("Permissão negada!\nNão é possível obter localização.");
                Toast.makeText(this, "Permissão negada! Não é possível obter localização.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}