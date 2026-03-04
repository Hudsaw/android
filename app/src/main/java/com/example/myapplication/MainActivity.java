package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    Button btnreset;
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tv);
        button=findViewById(R.id.button);
        btnreset=findViewById(R.id.btnreset);
        textView.setText("0");
        button.setOnClickListener(v -> {
            TextView tv = (TextView)v;
            c++;
            textView.setText(Integer.toString(c));
        });
        btnreset.setOnClickListener(v -> {
            textView.setText("0");
            c=0;
        });

    }
}