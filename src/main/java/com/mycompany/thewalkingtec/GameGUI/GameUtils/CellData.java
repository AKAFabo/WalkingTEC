/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec.GameGUI.GameUtils;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.Gun;
import javax.swing.JLabel;

/**
 *
 * @author Fabo
 */
public class CellData {
    private Gun gun; // El objeto relacionado con la celda
    private JLabel label; // La etiqueta de la imagen
    // Otros atributos que desees almacenar

    public CellData(Gun gun, JLabel label) {
        this.gun = gun;
        this.label = label;
        // Inicializa otros atributos según sea necesario
    }

    public Gun getGun() {
        return gun;
    }

    public JLabel getLabel() {
        return label;
    }
}
