/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec.GameGUI.GameUtils;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.Gun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.Zombie;
import com.mycompany.thewalkingtec.Game;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Fabo
 */
public class Combat {
    
    private Game game;
    private ArrayList<Gun> guns;
    
    
    public Combat(Game game, ArrayList<Gun> guns){
        this.game = game;
        this.guns = guns;
    }

    public void levelCompleted() {

        JOptionPane.showMessageDialog(null, "Nivel terminado con éxito", "The Walking TEC", JOptionPane.INFORMATION_MESSAGE);    
    }
}


