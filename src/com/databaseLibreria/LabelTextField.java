package com.databaseLibreria;

import javax.swing.*;

public class LabelTextField extends JPanel {

    JLabel Label;
    JTextField Text;

    public LabelTextField(JLabel label, JTextField text){
        Label = label;
        Text = text;
        Text.setColumns(20);
        add(Label); add(Text);
    }
}
