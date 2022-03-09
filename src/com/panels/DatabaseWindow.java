package com.panels;

import com.database.Book;
import com.database.Database;
import com.panels.components.TableMouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DatabaseWindow extends JFrame implements ActionListener {

    JButton[] buttons = new JButton[]{new JButton("Nuovo"), new JButton("Modifica") ,new JButton("Elimina"), new JButton("Chiudi")};
    JTable table;
    String[] columns = new String[]{"Id", "Autore", "Titolo", "Numero Pagine", "Genere"};
    DefaultTableModel model = new DefaultTableModel(columns, 0);

    public DatabaseWindow() throws SQLException {

        super("Library of Alexandria");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        String[] commands = new String[]{"New", "Modify", "Delete", "Close"};

        table = new JTable(model){
            public boolean isCellEditable(int row, int column){
                return false; //rende tutte le celle non editabili
            }
        };

        //Aggiunge alla tabella i risultati della query al database
        populateTable();

        //renderer per allineamento dei campi
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        //Aggiunge header, setta larghezza minima, giustifica al centro contenuto delle colonne
        for(int i = 0; i < table.getColumnModel().getColumnCount(); i++){
            TableColumn tc = table.getColumnModel().getColumn(i);
            tc.setHeaderValue(columns[i]);
            tc.setMinWidth(150);
            tc.setCellRenderer(renderer);
        }

        JPopupMenu menu = new JPopupMenu();
        JMenuItem[] menuItems = new JMenuItem[] {
                new JMenuItem("Aggiungi"),
                new JMenuItem("Modifica"),
                new JMenuItem("Elimina"),
        };

        for(int i = 0; i < commands.length; i++){
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand(commands[i]);
            if(i < 3){
                menu.add(menuItems[i]);
                menuItems[i].addActionListener(this);
                menuItems[i].setActionCommand(commands[i]);
            }
        }

        table.setComponentPopupMenu(menu);
        table.addMouseListener(new TableMouseListener(table));

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        for(JButton button : buttons) buttonPanel.add(button);

        add(buttonPanel, BorderLayout.SOUTH);
        setMinimumSize(new Dimension(800, 600));

        pack();
    }

    public void populateTable() throws SQLException {
        Database database = new Database();
        for(Book book : database.selectBook("SELECT * FROM libri")){
            Object[] object = new Object[]{book.getId(), book.getAutore(), book.getTitolo(), book.getNumeroPagine(), book.getGenere()};
            model.addRow(object);
        }
        database.connection.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //L'azione eseguita cambia a seconda del bottone premuto
        switch(e.getActionCommand()){
            case "Close" -> {
                dispose();
                new MainWindow().setVisible(true);
            }
            case "New" ->{
                dispose();
                new InsertWindow().setVisible(true);
            }
            case "Modify" -> {
                int row = table.getSelectedRow();
                if(row != -1){
                    String[] rowValues = new String[5];
                    for(int i = 0; i < 5; i++){
                        rowValues[i] = table.getModel().getValueAt(row, i).toString();
                    }
                    dispose();
                    new ModifyWindow(rowValues[0], rowValues[1], rowValues[2], rowValues[3], rowValues[4]).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Non hai selezionato nulla!");
                }
            }
            case "Delete" -> {
                int input = JOptionPane.showConfirmDialog(null, "Vuoi davvero eliminare il libro selezionato?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (input == 0){
                    try{
                        int row = table.getSelectedRow();
                        if(row != -1){
                            String id = table.getModel().getValueAt(row, 0).toString();
                            Database database = new Database();
                            database.delete(id);
                            ((DefaultTableModel)table.getModel()).removeRow(row);
                            database.connection.close();
                        } else {
                            JOptionPane.showMessageDialog(null, "Non hai selezionato nulla!");
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        }
    }
}
