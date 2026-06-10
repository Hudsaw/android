package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SQLActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Button button, btnLimpar, btnVoltar;
    EditText editText;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sql);

        button = findViewById(R.id.button);
        btnVoltar = findViewById(R.id.btnVoltar);
        editText = findViewById(R.id.editTextText);
        listView = findViewById(R.id.listView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = openOrCreateDatabase("meu_banco.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS notas " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, txt TEXT);");

        carregarListagem();

        button.setOnClickListener(v -> {
            String titulo = editText.getText().toString();
            if (!titulo.isEmpty()) {
                ContentValues cv = new ContentValues();
                cv.put("titulo", titulo);
                db.insert("notas", null, cv);
                editText.setText("");
                carregarListagem();
            }
        });
    }

    public void carregarListagem() {
        ArrayList<String> titulos = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM notas", null);

        if (cursor.moveToFirst()) {
            do {
                int tituloIndex = cursor.getColumnIndex("titulo");
                if (tituloIndex != -1) {
                    String titulo = cursor.getString(tituloIndex);
                    titulos.add(titulo);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> titulosAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                titulos
        );

        listView.setAdapter(titulosAdapter);

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}