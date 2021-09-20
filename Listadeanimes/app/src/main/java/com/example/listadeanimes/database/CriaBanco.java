package com.example.listadeanimes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CriaBanco extends SQLiteOpenHelper {

    protected static final String NOME_BANCO = "anime.db";
    protected static final String TABELA = "animes";
    protected static final String ID = "_id";
    protected static final String NOME ="nome";
    protected static final String TEMPORADAS = "temporadas";
    protected static final String STATUS_ANIME = "status_anime";
    protected static final String STATUS_PESSOAL = "status_pessoal";
    protected static final int VERSAO = 1;

    public CriaBanco(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement,"
                + NOME + " text,"
                + TEMPORADAS + " integer,"
                + STATUS_ANIME + " text,"
                + STATUS_PESSOAL + " text"
                + ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
