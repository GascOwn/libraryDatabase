package com.database;

public class User {
    public int Id;
    public String Nominativo;
    public String Username;
    public String Password;

    public User (int id, String nominativo, String username, String password){
        Id = id;
        Nominativo = nominativo;
        Username = username;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNominativo() {
        return Nominativo;
    }

    public void setNominativo(String nominativo) {
        Nominativo = nominativo;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
