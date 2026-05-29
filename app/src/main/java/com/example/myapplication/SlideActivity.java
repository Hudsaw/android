package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SlideActivity extends AppCompatActivity {

    Button buttonAvancar, buttonVoltar, buttonMenu;
    ImageView imageView;
    int imagens[] = new int[]{R.drawable.cachorro,
            R.drawable.gardem,
            R.drawable.happy,
            R.drawable.patinho,
            R.drawable.porquinho};

    int posicao = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        buttonAvancar = findViewById(R.id.btnAvancar);
        buttonVoltar = findViewById(R.id.btnVoltar);
        buttonMenu = findViewById(R.id.btnMenu);
        imageView = findViewById(R.id.imageView);

        buttonAvancar.setOnClickListener(v -> {
            posicao++;
            if (posicao >= imagens.length) {
                posicao = 0;
            }
            imageView.setImageResource(imagens[posicao]);
        });

        buttonVoltar.setOnClickListener(v -> {
            posicao--;
            if (posicao < 0) {
                posicao = imagens.length - 1;
            }
            imageView.setImageResource(imagens[posicao]);
        });

        buttonMenu.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        posicao = 0;
        imageView.setImageResource(imagens[0]);
    }
}