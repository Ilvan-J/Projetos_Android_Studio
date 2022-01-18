package com.example.listadeanimes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BancoController {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    public String salvar(anime anime){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(CriaBanco.NOME, anime.getNome());
        valores.put(CriaBanco.TEMPORADAS, anime.getTemporadas());
        valores.put(CriaBanco.STATUS_ANIME, anime.getStatus_anime());
        valores.put(CriaBanco.STATUS_PESSOAL, anime.getStatus_pessoal());

        resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();

        if(resultado ==-1)
            return "Erro ao salvar";
        else
            return "Salvo com sucesso";
    }

    public Cursor carregarDados(){
        Cursor cursor;

        String[] campos ={banco.ID, banco.NOME, banco.TEMPORADAS};
        db = banco.getReadableDatabase();

        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();

        return cursor;

    }

    public  Cursor carregarOrdemAlfabetica(){
        Cursor cursor;

        String[] campos ={banco.ID, banco.NOME, banco.TEMPORADAS};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, banco.NOME, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return  cursor;

    }

    public Cursor carregarPorStatus(String status){
        Cursor cursor = null;
        String where = CriaBanco.STATUS_ANIME + "=" + "'" + status + "'";

        db = banco.getReadableDatabase();

        try {
            cursor = db.query(CriaBanco.TABELA, null, where, null, null, null, CriaBanco.NOME, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;

    }

    public Cursor carregarStatusUsuario(String status){

        Cursor cursor = null;

        String where = CriaBanco.STATUS_PESSOAL + "=" + "'" + status + "'";

        db = banco.getReadableDatabase();

        try {
            cursor = db.query(CriaBanco.TABELA, null, where, null, null, null, CriaBanco.NOME, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;

    }

    public Cursor carregarDadoById(int id){
        Cursor cursor;
        String[] campos = {banco.ID, banco.NOME, banco.TEMPORADAS,banco.STATUS_ANIME, banco.STATUS_PESSOAL};
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();

        cursor = db.query(CriaBanco.TABELA, campos, where, null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();

        return cursor;

    }
    

    public Cursor pesquisar(String p){
        Cursor cursor;
        String[] campos ={banco.ID, banco.NOME, banco.TEMPORADAS};
        db = banco.getReadableDatabase();

        String nome = "'%" + p + "%'";

        String sql = "select * from " + CriaBanco.TABELA + " where " + CriaBanco.NOME + " like " + nome;
        cursor = db.rawQuery(sql,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;

    }

    public void alterarRegistro(anime anime){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        where = CriaBanco.ID + "=" + anime.getId();

        valores.put(CriaBanco.NOME, anime.getNome());
        valores.put(CriaBanco.TEMPORADAS, anime.getTemporadas());
        valores.put(CriaBanco.STATUS_ANIME, anime.getStatus_anime());
        valores.put(CriaBanco.STATUS_PESSOAL, anime.getStatus_pessoal());

        db.update(CriaBanco.TABELA, valores, where,null);
        db.close();

    }

    public void deletarRegistro(int id){
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA, where, null);
        db.close();
    }

}
