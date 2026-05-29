package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class IMCActivity extends AppCompatActivity {

    Button btnVoltar, btnBundle;
    EditText edpeso, edaltura, editTextMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnBundle = findViewById(R.id.btnBundle);
        edaltura = findViewById(R.id.edAltura);
        edpeso = findViewById(R.id.edPeso);
        editTextMsg = findViewById(R.id.editTextMsg);

        btnVoltar.setOnClickListener(v -> {
            finish();
        });

        btnBundle.setOnClickListener(v -> {
            String strPeso = edpeso.getText().toString();
            String strAltura = edaltura.getText().toString();
            String nome = editTextMsg.getText().toString();

            if (strPeso.isEmpty()) {
                edpeso.setError("Informe o peso");
                edpeso.requestFocus();
                return;
            }
            if (strAltura.isEmpty()) {
                edaltura.setError("Informe a altura");
                edaltura.requestFocus();
                return;
            }

            Intent intent = new Intent(IMCActivity.this, BundleActivity.class);
            intent.putExtra("NOME_USUARIO", nome);
            intent.putExtra("PESO", strPeso);
            intent.putExtra("ALTURA", strAltura);
            startActivity(intent);
        });
    }
}