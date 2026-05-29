package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TinderActivity extends AppCompatActivity {

    private LinearLayout card;
    private TextView nameText, ageText, bioText;
    private Button btnNo, btnYes, btnMenu;

    private int current = 0;

    private String[] names = {"Ana", "Bruno", "Carla"};
    private String[] ages = {"24", "27", "22"};
    private String[] bios = {"Gosta de café", "Fã de futebol", "Ama viajar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder);

        card = findViewById(R.id.card);
        nameText = findViewById(R.id.name);
        ageText = findViewById(R.id.age);
        bioText = findViewById(R.id.bio);
        btnNo = findViewById(R.id.btnNo);
        btnYes = findViewById(R.id.btnYes);
        btnMenu = findViewById(R.id.btnMenu);

        showProfile();

        btnNo.setOnClickListener(v -> {
            Toast.makeText(this, "✗ Você disse NÃO para " + names[current], Toast.LENGTH_SHORT).show();
            nextProfile();
        });

        btnYes.setOnClickListener(v -> {
            Toast.makeText(this, "❤ Você disse SIM para " + names[current], Toast.LENGTH_SHORT).show();
            nextProfile();
        });

        btnMenu.setOnClickListener(v -> {
            finish(); // Retorna ao Menu Principal
        });
    }

    private void showProfile() {
        nameText.setText(names[current]);
        ageText.setText(ages[current] + " anos");
        bioText.setText(bios[current]);
    }

    private void nextProfile() {
        current++;
        if (current < names.length) {
            showProfile();
        } else {
            Toast.makeText(this, "Acabaram os perfis! 😢", Toast.LENGTH_LONG).show();
            btnNo.setEnabled(false);
            btnYes.setEnabled(false);
        }
    }
}