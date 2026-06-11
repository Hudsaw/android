package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SQLActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Button btnSalvar, btnLimpar, btnVoltar;
    EditText editText;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        btnSalvar = findViewById(R.id.button);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnVoltar = findViewById(R.id.btnVoltar);
        editText = findViewById(R.id.editTextText);
        listView = findViewById(R.id.listView);

        db = openOrCreateDatabase("banco.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS notas (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, txt TEXT)");

        carregarListagem();

        btnSalvar.setOnClickListener(v -> {
            ContentValues cv = new ContentValues();
            cv.put("titulo", editText.getText().toString());
            db.insert("notas", null, cv);
            editText.setText("");
            carregarListagem();
        });

        btnLimpar.setOnClickListener(v -> {
            db.execSQL("DELETE FROM notas");
            carregarListagem();
        });

        btnVoltar.setOnClickListener(v -> {
            startActivity(new Intent(SQLActivity.this, MainActivity.class));
            finish();
        });
    }

    void carregarListagem() {
        ArrayList<String> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM notas", null);

        int colunaIndex = cursor.getColumnIndex("titulo");

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(colunaIndex));
            } while (cursor.moveToNext());
        }
        cursor.close();

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista));
    }
}