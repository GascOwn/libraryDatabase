package com.database;

import java.util.Date;

public class Book {
    public int Id;
    public String Autore;
    public String Titolo;
    public Date DataPubblicazione;
    public int NumeroPagine;
    public String Genere;

    public Book(int id, String autore, String titolo, int numeroPagine, String genere){
        Id = id;
        Autore = autore;
        Titolo = titolo;
        NumeroPagine = numeroPagine;
        Genere = genere;
    }

    public int getId(){return Id; }

    public String getAutore() {
        return Autore;
    }

    public void setAutore(String autore) {
        Autore = autore;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public int getNumeroPagine() {
        return NumeroPagine;
    }

    public void setNumeroPagine(int numeroPagine) {
        NumeroPagine = numeroPagine;
    }

    public String getGenere() {
        return Genere;
    }

    public void setGenere(String genere) {
        Genere = genere;
    }
}
