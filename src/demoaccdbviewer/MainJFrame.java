/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoaccdbviewer;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Graf_Nameless
 */
public class MainJFrame extends javax.swing.JFrame {
    public static File f1;    
    JFileChooser fc = new JFileChooser();
    Table tab;
    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        getFileJButton = new javax.swing.JButton();
        filePathJTextField = new javax.swing.JTextField();
        viewTableJButton = new javax.swing.JButton();
        tablesJComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataJTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        getFileJButton.setText("Файл");
        getFileJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFileJButtonActionPerformed(evt);
            }
        });

        filePathJTextField.setEditable(false);

        viewTableJButton.setText("Просмотреть таблицу");
        viewTableJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewTableJButtonActionPerformed(evt);
            }
        });

        dataJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(dataJTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(getFileJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filePathJTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(viewTableJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tablesJComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getFileJButton)
                    .addComponent(filePathJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewTableJButton)
                    .addComponent(tablesJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void getFileJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getFileJButtonActionPerformed
        fc.setCurrentDirectory(new File("."));
        int returnVal = fc.showDialog(getFileJButton, "Attach");
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            f1 = fc.getSelectedFile();
            filePathJTextField.setText(fc.getSelectedFile().getPath());
            setComboBox();
        }        
    }//GEN-LAST:event_getFileJButtonActionPerformed

    private void viewTableJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewTableJButtonActionPerformed
        try {
            tab = DatabaseBuilder.open(f1).getTable((String) tablesJComboBox.getItemAt(tablesJComboBox.getSelectedIndex()));
            printDataToTable();
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_viewTableJButtonActionPerformed
    
    private void printDataToTable(){
        try {
            dataJTable.setModel(new DefaultTableModel(getDataTable(getColumnsName()), getColumnsName()));
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String[] getColumnsName(){
        String[] columnNames = new String[tab.getColumns().size()];
        for(int i = 0; i < tab.getColumns().size(); i++){
            columnNames[i] = tab.getColumns().get(i).getName();
        }
        return columnNames;
    }
    
    private Object[][] getDataTable(String[] nameCol) throws IOException{
        Object [][] data = new Object[tab.getRowCount()][nameCol.length];
        for(int i = 0; i < nameCol.length; i++){
            String[] dataCol = MDBOperations.GetRowValueOnNameColumn(tab, nameCol[i]);
            for(int j = 0; j < dataCol.length; j++){
                data[j][i] = dataCol[j];
            }
        }
        return data;
    }
    
    private void setComboBox(){
        try {
            Set<String> tableNames = DatabaseBuilder.open(new File(f1.getPath())).getTableNames();
            tableNames.stream().forEach(tab -> {
                tablesJComboBox.addItem(tab);
            });            
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable dataJTable;
    private javax.swing.JTextField filePathJTextField;
    private javax.swing.JButton getFileJButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox tablesJComboBox;
    private javax.swing.JButton viewTableJButton;
    // End of variables declaration//GEN-END:variables
}
