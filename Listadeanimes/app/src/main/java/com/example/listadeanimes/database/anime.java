package com.example.listadeanimes.database;

public class anime {

    private int id, temporadas;
    private String nome, status_anime, status_pessoal;

    public anime(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus_anime() {
        return status_anime;
    }

    public void setStatus_anime(String status_anime) {
        this.status_anime = status_anime;
    }

    public String getStatus_pessoal() {
        return status_pessoal;
    }

    public void setStatus_pessoal(String status_pessoal) {
        this.status_pessoal = status_pessoal;
    }
}
