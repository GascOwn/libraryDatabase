package com.database;

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
    public Database(String connectionString) {
        try{
            connection = DriverManager.getConnection(connectionString);
        } catch(SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx.getMessage());
        }
    }

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

    public Vector<Book> select(String query) throws SQLException {
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
}
