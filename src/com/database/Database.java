package com.database;

import com.panels.LoginWindow;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class Database {

    public Connection connection;

    public Database() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/libreria", "root", "");
        } catch(SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx.getMessage());
        }
    }

    //Ora come ora non serve, è solo per un futuro se si volessero implementare connessioni ad altri database
    /* public Database(String connectionString) {
        try{
            connection = DriverManager.getConnection(connectionString);
        } catch(SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx.getMessage());
        }
    } */

    public void insert(String autore, String titolo, int numeroPagine, String genere) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO libri VALUES(default, ?, ?, ? ,?)");
            statement.setString(1, titolo);
            statement.setString(2, autore);
            statement.setInt(3, numeroPagine);
            statement.setString(4, genere);
            statement.executeUpdate();
            statement.close();
            JOptionPane.showMessageDialog(null, "Inserimento completato!");
        }  catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx.getMessage());
        }
    }

    public Vector<Book> selectBook(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        Vector<Book> results = new Vector<>();
        while(result.next()){
            results.add(new Book(result.getInt("id"),
                                 result.getString("autore"),
                                 result.getString("titolo"),
                                 result.getInt("numero_pagine"),
                                 result.getString("genere")));
        }
        statement.close();
        return results;
    }

    public Vector<User> selectUser(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        Vector<User> results = new Vector<>();
        while(result.next()){
            results.add(new User(result.getInt("id"),
                    result.getString("nominativo"),
                    result.getString("username"),
                    result.getString("password")));
        }
        statement.close();
        return results;
    }


    public void update(String id, String autore, String titolo, String numeroPagine, String genere) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE libri SET autore = ?, titolo = ?, numero_pagine = ?, genere = ? WHERE id = ?");
            statement.setString(1, autore);
            statement.setString(2, titolo);
            statement.setInt(3, Integer.parseInt(numeroPagine));
            statement.setString(4, genere);
            statement.setInt(5, Integer.parseInt(id));
            statement.executeUpdate();
            statement.close();
            JOptionPane.showMessageDialog(null, "Modifica completata!");

        } catch (SQLException sqlex){
            JOptionPane.showMessageDialog(null, sqlex.getMessage());
        }
    }

    public void delete(String id){
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM libri WHERE id = ?");
            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
            statement.close();

        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public static int count(String query) throws SQLException {
        Database database = new Database();
        PreparedStatement statement = database.connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        results.next();
        return results.getInt(1);
    }

    public static int[] getStatistics(String[] queries) throws SQLException {

        int[] statistics = new int[queries.length];
        for(int i = 0; i < queries.length; i++){
            statistics[i] = count(queries[i]);
        }
        return statistics;
    }

    public static void initialise() throws SQLException {
        Database database = new Database();
        ResultSet results =  database.connection.getMetaData().getTables(null, null, "utenti", null);
        if(!results.next()){
            String[] queries = new String[]{"CREATE TABLE utenti( " +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "nominativo VARCHAR(30) NOT NULL," +
                    "username VARCHAR(30) NOT NULL," +
                    "password VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY(id)" +
                    ")",
                    "INSERT INTO utenti VALUES(null, 'admin', 'admin', 'admin')"
                    };
            for(String query : queries){
                PreparedStatement statement = database.connection.prepareStatement(query);
                statement.executeUpdate();
                statement.close();
            }
            JOptionPane.showMessageDialog(null, "tabella utenti creata, accedere con username: admin e password: admin");
        }
        results =  database.connection.getMetaData().getTables(null, null, "libri", null);
        if(!results.next()){
            PreparedStatement statement = database.connection.prepareStatement(
                    "CREATE TABLE libri( " +
                            "id INT NOT NULL AUTO_INCREMENT," +
                            "autore VARCHAR(30) NOT NULL," +
                            "titolo VARCHAR(50) NOT NULL," +
                            "numero_pagine INT," +
                            "genere VARCHAR(30)," +
                            "PRIMARY KEY(id)" +
                            ")"
            );
            statement.executeUpdate();
            statement.close();
        }
        database.connection.close();
        new LoginWindow().setVisible(true);

    }
}
