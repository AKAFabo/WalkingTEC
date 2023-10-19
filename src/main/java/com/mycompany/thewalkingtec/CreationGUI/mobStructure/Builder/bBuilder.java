/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.thewalkingtec.CreationGUI.mobStructure.Builder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Fabo
 */
public class bBuilder extends javax.swing.JFrame {

    /**
     * Creates new form bBuilder
     */
    public bBuilder() {
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        startLevelTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        unlockLevelTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fieldsInMatrixTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        healthTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        fileChooserButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Crear bloque");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Nivel inicial:");

        jLabel4.setText("Nivel de desbloqueo:");

        jLabel5.setText("Campos en la matriz:");

        jLabel6.setText("Vida:");

        jLabel7.setText("Archivo de imagen:");

        fileChooserButton.setText("...");
        fileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserButtonActionPerformed(evt);
            }
        });

        createButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        createButton.setText("Crear bloque");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(healthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(fieldsInMatrixTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                .addComponent(unlockLevelTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(startLevelTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileChooserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileChooserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startLevelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unlockLevelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldsInMatrixTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(healthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserButtonActionPerformed
        
        JFileChooser fc = new JFileChooser();
        
        int seleccion = fc.showOpenDialog(this);
        
        if (seleccion == JFileChooser.APPROVE_OPTION){ //COMPROBAR QUE SE DE CLICK EN ACEPTAR
            File archivo = fc.getSelectedFile();
            fileTextField.setText(archivo.getAbsolutePath());
        }
    }//GEN-LAST:event_fileChooserButtonActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        
        try {
            String name = nameTextField.getText();
            String fieldsInMatrix = fieldsInMatrixTextField.getText();
            String health = healthTextField.getText();
            String startLevel = startLevelTextField.getText();
            String unlockLevel = unlockLevelTextField.getText();
            String fileImage = fileTextField.getText();
            
            String txtFilePath = "src/main/java/com/mycompany/thewalkingtec/CreationGUI/mobFiles/Blocks/" + name + ".txt";
            
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
            
            writer.write(name);
            writer.newLine();
            writer.write(health);
            writer.newLine();
            writer.write(startLevel);
            writer.newLine();
            writer.write(unlockLevel);
            writer.newLine();
            writer.write(fieldsInMatrix);
            writer.newLine();
            writer.write(fileImage);
            
            JOptionPane.showMessageDialog(this, "Bloque creado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
         
        }   catch (IOException e) {
            e.printStackTrace();
        }
        
        } catch (NumberFormatException e) {
        // Manejar excepciones si los valores no son números válidos
        e.printStackTrace();      
    }          
        
      
    }//GEN-LAST:event_createButtonActionPerformed

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
            java.util.logging.Logger.getLogger(bBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bBuilder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bBuilder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JTextField fieldsInMatrixTextField;
    private javax.swing.JButton fileChooserButton;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JTextField healthTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField startLevelTextField;
    private javax.swing.JTextField unlockLevelTextField;
    // End of variables declaration//GEN-END:variables
}
