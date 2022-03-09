package com.panels;

import com.database.Database;
import com.database.User;
import com.panels.components.LabelTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class LoginWindow extends JFrame implements ActionListener {

    LabelTextField[] labelTextFields = new LabelTextField[]{
            new LabelTextField("Nome Utente"),
            new LabelTextField("Password", '*')
    };

    JButton loginButton = new JButton("Login");

    public LoginWindow(){

        super("Login");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,1));

        loginButton.addActionListener(this);

        for(LabelTextField labelTextField : labelTextFields) add(labelTextField);
        add(loginButton);

        pack();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            Database database = new Database();
            String query = "SELECT * FROM utenti WHERE username = '" + labelTextFields[0].Text.getText() + "' AND password = '"
                     + String.valueOf(labelTextFields[1].Password.getPassword()) + "'";
            Vector<User> users = database.selectUser(query);
            database.connection.close();
            if(users.size() == 1){
                JOptionPane.showMessageDialog(null, "Ciao " + users.get(0).Nominativo + "!");
                dispose();
                new MainWindow().setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "Utente non valido");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
