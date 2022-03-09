package com.panels.components;

import com.sun.source.doctree.TextTree;

import javax.swing.*;

public class LabelTextField extends JPanel {

    public JLabel Label;
    public JTextField Text;
    public JPasswordField Password;

    public LabelTextField(String label){

        Label = new JLabel(label);
        Text = new JTextField(20);
        add(Label);
        add(Text);
    }

    public LabelTextField(String label, char echoChar){

        Label = new JLabel(label);
        Password = new JPasswordField(20);
        Password.setEchoChar(echoChar);

        add(Label);
        add(Password);
    }
}
