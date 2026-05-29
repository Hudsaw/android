package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CounterActivity extends AppCompatActivity {

    TextView textView;
    Button button, btnreset, btnVoltar;
    int c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        textView = findViewById(R.id.tv);
        button = findViewById(R.id.button);
        btnreset = findViewById(R.id.btnreset);
        btnVoltar = findViewById(R.id.btnVoltar);

        textView.setText("0");

        button.setOnClickListener(v -> {
            c++;
            textView.setText(Integer.toString(c));
        });

        btnreset.setOnClickListener(v -> {
            textView.setText("0");
            c = 0;
        });

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
}