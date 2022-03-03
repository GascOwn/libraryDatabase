package com.panels.components;

import javax.swing.*;

public class LabelTextField extends JPanel {

    public JLabel Label;
    public JTextField Text;

    public LabelTextField(JLabel label, JTextField text){
        Label = label;
        Text = text;
        Text.setColumns(20);
        add(Label); add(Text);
    }
}
