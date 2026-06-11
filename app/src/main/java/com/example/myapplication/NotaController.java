package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotaController {
    private NotaDAO notaDAO;
    private SQLiteDatabase db;

    public NotaController(Context context) {
        db = context.openOrCreateDatabase("banco.db", Context.MODE_PRIVATE, null);
        notaDAO = new NotaDAO(db);
        notaDAO.criarTabela();
    }

    public void salvarNota(String titulo) {
        Nota nota = new Nota(titulo, "");
        notaDAO.inserir(nota);
    }

    public void limparNotas() {
        notaDAO.deletarTodas();
    }

    public ArrayList<String> listarTitulos() {
        return notaDAO.buscarTitulos();
    }

    public ArrayList<Nota> listarTodasNotas() {
        return notaDAO.buscarTodas();
    }
}