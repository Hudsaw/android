package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.ArrayList;
import java.util.List;

public class PaintActivity extends AppCompatActivity {

    private List<Fragment> camadas;
    private int camadaAtiva = 0;
    private Spinner spinnerCamadas;

    private Button btnClear, btnFino, btnMedio, btnGrosso;
    private Button btnColor;
    private Button btnMenu;
    private Button btnCirculo, btnReta, btnLivre, btnQuadrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        btnClear = findViewById(R.id.btnClear);
        btnFino = findViewById(R.id.btnFino);
        btnMedio = findViewById(R.id.btnMedio);
        btnGrosso = findViewById(R.id.btnGrosso);
        btnColor = findViewById(R.id.btnColor);
        btnMenu = findViewById(R.id.btnMenu);
        btnCirculo = findViewById(R.id.btnCirculo);
        btnReta = findViewById(R.id.btnReta);
        btnLivre = findViewById(R.id.btnLivre);
        btnQuadrado = findViewById(R.id.btnQuadrado);
        spinnerCamadas = findViewById(R.id.spinnerCamadas);

        camadas = new ArrayList<>();
        camadas.add(new FragmentoA());
        camadas.add(new FragmentoB());
        camadas.add(new FragmentoC());

        String[] nomesCamadas = {"Camada 1", "Camada 2", "Camada 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nomesCamadas
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCamadas.setAdapter(adapter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragmentContainer, camadas.get(0));
        transaction.commit();

        spinnerCamadas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != camadaAtiva) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, camadas.get(position));
                    transaction.commit();
                    camadaAtiva = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().clearCanvas();
            }
        });

        btnFino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().setGrossura(5f);
            }
        });

        btnMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().setGrossura(15f);
            }
        });

        btnGrosso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().setGrossura(30f);
            }
        });

        btnColor.setOnClickListener(v -> {
            new ColorPickerDialog.Builder(PaintActivity.this)
                    .setTitle("ColorPicker Dialog")
                    .setPreferenceName("MyColorPickerDialog")
                    .setPositiveButton("Confirmar",
                            new ColorEnvelopeListener() {
                                @Override
                                public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                    getActivePaintView().setColor(envelope.getColor());
                                }
                            })
                    .setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                    .attachAlphaSlideBar(true)
                    .attachBrightnessSlideBar(true)
                    .setBottomSpace(12)
                    .show();
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCirculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().setCirculo();
            }
        });

        btnQuadrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().setQuadrado();
            }
        });

        btnLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().setTraco();
            }
        });

        btnReta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePaintView().setReta();
            }
        });
    }

    private SimplePaint getActivePaintView() {
        Fragment fragmentoAtivo = camadas.get(camadaAtiva);
        if (fragmentoAtivo instanceof PaintLayer) {
            return ((PaintLayer) fragmentoAtivo).getPaintView();
        }
        return null;
    }
}