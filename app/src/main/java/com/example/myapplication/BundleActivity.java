package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class BundleActivity extends AppCompatActivity {

    Button btnVoltar;
    TextView textViewNome, textViewIMC;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);

        String nome = getIntent().getStringExtra("NOME_USUARIO");
        String strPeso = getIntent().getStringExtra("PESO");
        String strAltura = getIntent().getStringExtra("ALTURA");

        textViewNome = findViewById(R.id.textViewNome);
        textViewIMC = findViewById(R.id.tvIMC);
        imageView = findViewById(R.id.imageView2);
        btnVoltar = findViewById(R.id.btnVoltar);

        if (nome != null && !nome.isEmpty()) {
            textViewNome.setText("Olá, " + nome + "!");
        } else {
            textViewNome.setText("Olá, usuário!");
        }

        if (strPeso != null && strAltura != null && !strPeso.isEmpty() && !strAltura.isEmpty()) {
            double peso = Double.parseDouble(strPeso);
            double altura = Double.parseDouble(strAltura);
            double imc = peso / (altura * altura);

            DecimalFormat dc = new DecimalFormat("##.##");
            String resultadoIMC = "IMC: " + dc.format(imc);

            int imagemResource;
            String classificacao;

            if (imc < 18.5) {
                imagemResource = R.drawable.abaixopeso;
                classificacao = "Abaixo do peso";
            } else if (imc < 25) {
                imagemResource = R.drawable.normal;
                classificacao = "Peso normal";
            } else if (imc < 30) {
                imagemResource = R.drawable.sobrepeso;
                classificacao = "Sobrepeso";
            } else if (imc < 35) {
                imagemResource = R.drawable.obesidade1;
                classificacao = "Obesidade Grau I";
            } else if (imc < 40) {
                imagemResource = R.drawable.obesidade2;
                classificacao = "Obesidade Grau II";
            } else {
                imagemResource = R.drawable.obesidade3;
                classificacao = "Obesidade Grau III";
            }

            textViewIMC.setText(resultadoIMC + " - " + classificacao);
            imageView.setImageResource(imagemResource);
        }

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
}