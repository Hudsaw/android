package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCounter, btnRandom, btnIMC, btnSlide, btnTinder, btnPaint, btnSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCounter = findViewById(R.id.btnCounter);
        btnRandom = findViewById(R.id.btnRandom);
        btnIMC = findViewById(R.id.btnIMC);
        btnSlide = findViewById(R.id.btnSlide);
        btnTinder = findViewById(R.id.btnTinder);
        btnPaint = findViewById(R.id.btnPaint);
        btnSQL = findViewById(R.id.btnSQL);

        btnCounter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CounterActivity.class);
            startActivity(intent);
        });

        btnRandom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RandomActivity.class);
            startActivity(intent);
        });

        btnIMC.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IMCActivity.class);
            startActivity(intent);
        });

        btnSlide.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SlideActivity.class);
            startActivity(intent);
        });

        btnPaint.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PaintActivity.class);
            startActivity(intent);
        });

        btnTinder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TinderActivity.class);
            startActivity(intent);
        });

        btnSQL.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SQLActivity.class);
            startActivity(intent);
        });
    }
}