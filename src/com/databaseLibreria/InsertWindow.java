package com.databaseLibreria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class InsertWindow extends JFrame implements ActionListener {

    LabelTextField[] labelTextFields = new LabelTextField[]{
            new LabelTextField(new JLabel("Autore: "), new JTextField()),
            new LabelTextField(new JLabel("Titolo: "), new JTextField()),
            new LabelTextField(new JLabel("Numero Pagine: "), new JTextField()),
            new LabelTextField(new JLabel("Genere: "), new JTextField())
    };

    JPanel[] panels = new JPanel[]{new JPanel(new GridLayout(2,2)), new JPanel(new FlowLayout())};
    JButton[] buttons = new JButton[]{new JButton("Inserisci"), new JButton("Chiudi"), new JButton("Database")};

    public InsertWindow(){
        super("Inserisci");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        for(LabelTextField labelTextField : labelTextFields) panels[0].add(labelTextField);
        for(JButton button : buttons) panels[1].add(button);

        labelTextFields[2].Text.setText("0");

        for (JButton button : buttons) button.addActionListener(this);
        buttons[0].setActionCommand("Insert");
        buttons[1].setActionCommand("Close");
        buttons[2].setActionCommand("Database");


        setLayout(new BorderLayout());
        add(panels[0], BorderLayout.NORTH); add(panels[1], BorderLayout.SOUTH);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Insert" -> {
                String autore = labelTextFields[0].Text.getText();
                String titolo = labelTextFields[1].Text.getText();
                String pagine = !labelTextFields[2].Text.getText().isBlank() ? labelTextFields[2].Text.getText() : "0";
                String genere = !labelTextFields[3].Text.getText().isBlank() ? labelTextFields[3].Text.getText() : "//";

                if (!autore.isBlank() && !titolo.isBlank()) {
                    try {
                        Database database = new Database();
                        database.insert(autore, titolo, Integer.parseInt(pagine), genere);
                        database.connection.close();

                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Campi invalidi!");
                }
            }
            case "Close" -> {
                dispose();
                new MainWindow().setVisible(true);
            }
            case "Database" -> {
                try {
                    dispose();
                    new DatabaseWindow().setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
