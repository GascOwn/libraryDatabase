package com.panels;

import com.database.Database;
import com.panels.components.LabelTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModifyWindow extends JFrame implements ActionListener {

    String Id;
    String NumeroPagine;
    String Genere;

    LabelTextField[] labelTextFields = new LabelTextField[]{
            new LabelTextField("Autore: "),
            new LabelTextField("Titolo: "),
            new LabelTextField("Numero Pagine: "),
            new LabelTextField("Genere: ")
    };

    JPanel[] panels = new JPanel[]{new JPanel(new GridLayout(2,2)), new JPanel(new FlowLayout())};
    JButton[] buttons = new JButton[]{new JButton("Modifica"), new JButton("Chiudi")};

    public ModifyWindow(String id, String autore, String titolo, String numeroPagine, String genere) {
        super("Modifica");
        Id = id;
        NumeroPagine = numeroPagine;
        Genere = genere;
        setLocationRelativeTo(null);

        for(LabelTextField labelTextField : labelTextFields) panels[0].add(labelTextField);
        for(JButton button : buttons) panels[1].add(button);

        labelTextFields[0].Text.setText(autore);
        labelTextFields[1].Text.setText(titolo);
        labelTextFields[2].Text.setText(numeroPagine);
        labelTextFields[3].Text.setText(genere);

        buttons[0].addActionListener(this); buttons[0].setActionCommand("Modify");
        buttons[1].addActionListener(this); buttons[1].setActionCommand("Close");

        setLayout(new BorderLayout());
        add(panels[0], BorderLayout.NORTH); add(panels[1], BorderLayout.SOUTH);

        pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Modify" -> {
                String autore = labelTextFields[0].Text.getText();
                String titolo = labelTextFields[1].Text.getText();
                String pagine = !labelTextFields[2].Text.getText().isBlank() ? labelTextFields[2].Text.getText() : NumeroPagine;
                String genere = !labelTextFields[3].Text.getText().isBlank() ? labelTextFields[3].Text.getText() : Genere;

                if (!autore.isBlank() && !titolo.isBlank()) {
                    try {
                        Database database = new Database();
                        database.update(Id, autore, titolo, pagine, genere);
                        database.connection.close();
                        dispose();
                        new DatabaseWindow().setVisible(true);

                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Campi invalidi!");
                }
            }
            case "Close" -> {
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
