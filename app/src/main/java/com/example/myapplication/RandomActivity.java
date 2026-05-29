package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {

    TextView textView;
    EditText etMin, etMax;
    Button btnrnd, btnreset, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        textView = findViewById(R.id.tv);
        etMin = findViewById(R.id.etMin);
        etMax = findViewById(R.id.etMax);
        btnrnd = findViewById(R.id.btnrdn);
        btnreset = findViewById(R.id.btnreset);
        btnVoltar = findViewById(R.id.btnVoltar);

        textView.setText("0");

        btnreset.setOnClickListener(v -> {
            textView.setText("0");
            etMin.setText("0");
            etMax.setText("100");
        });

        btnrnd.setOnClickListener(v -> {
            if (etMin.getText().toString().isEmpty() || etMax.getText().toString().isEmpty()) {
                return;
            }

            int min = Integer.parseInt(etMin.getText().toString());
            int max = Integer.parseInt(etMax.getText().toString());

            if (min >= max) {
                etMin.setError("Mínimo deve ser menor que Máximo");
                return;
            }

            Random random = new Random();
            int randomNumber = random.nextInt((max - min) + 1) + min;

            textView.setText(Integer.toString(randomNumber));
        });

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
}