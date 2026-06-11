package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class SQLActivity extends AppCompatActivity {
    private NotaController controller;
    private EditText editText;
    private ListView listView;
    private Button btnSalvar, btnLimpar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        controller = new NotaController(this);

        editText = findViewById(R.id.editTextText);
        listView = findViewById(R.id.listView);
        btnSalvar = findViewById(R.id.button);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnVoltar = findViewById(R.id.btnVoltar);

        atualizarLista();

        btnSalvar.setOnClickListener(v -> {
            controller.salvarNota(editText.getText().toString());
            editText.setText("");
            atualizarLista();
        });

        btnLimpar.setOnClickListener(v -> {
            controller.limparNotas();
            atualizarLista();
        });

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }

    private void atualizarLista() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.listarTitulos());
        listView.setAdapter(adapter);
    }
}