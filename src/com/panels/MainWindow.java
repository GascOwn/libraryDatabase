package com.panels;

import com.database.Database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener {

    JComboBox<String> combo = new JComboBox<>(new String[]{"Visualizza database", "Aggingi elemento", "Statistiche"});
    JButton[] buttons = new JButton[] {new JButton("Procedi"), new JButton("Chiudi")};

    public MainWindow(){

        super("Database Libreria");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout());

        add(combo);

        for(JButton button : buttons) {
            button.addActionListener(this);
            add(button);
        }
        buttons[0].setActionCommand("Open");

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        dispose();
        if (e.getActionCommand().equals("Open")) {
            try {
                switch(combo.getSelectedIndex()){
                    case 0 -> new DatabaseWindow().setVisible(true);
                    case 1 -> new InsertWindow().setVisible(true);
                    case 2 -> {
                        String[] queries = new String[]{
                                "SELECT COUNT(id) FROM libri",
                                "SELECT COUNT(id) FROM libri WHERE numero_pagine > 100"};
                        int[] statistics = Database.getStatistics(queries);
                        JOptionPane.showMessageDialog(
                                null,
                                "Numero di libri: " + statistics[0] +
                                        "\nCon più di cento pagine :" + statistics[1]);
                        setVisible(true);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}


