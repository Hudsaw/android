package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnCounter).setOnClickListener(v -> startActivity(new Intent(this, CounterActivity.class)));
        findViewById(R.id.btnRandom).setOnClickListener(v -> startActivity(new Intent(this, RandomActivity.class)));
        findViewById(R.id.btnIMC).setOnClickListener(v -> startActivity(new Intent(this, IMCActivity.class)));
        findViewById(R.id.btnSlide).setOnClickListener(v -> startActivity(new Intent(this, SlideActivity.class)));
        findViewById(R.id.btnTinder).setOnClickListener(v -> startActivity(new Intent(this, TinderActivity.class)));
        findViewById(R.id.btnPaint).setOnClickListener(v -> startActivity(new Intent(this, PaintActivity.class)));
        findViewById(R.id.btnSQL).setOnClickListener(v -> startActivity(new Intent(this, SQLActivity.class)));
        findViewById(R.id.btnGPS).setOnClickListener(v -> startActivity(new Intent(this, GPSActivity.class)));
        findViewById(R.id.btnCamera).setOnClickListener(v -> startActivity(new Intent(this, CameraActivity.class)));
    }
}