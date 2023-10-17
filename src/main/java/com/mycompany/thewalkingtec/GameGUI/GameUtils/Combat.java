/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec.GameGUI.GameUtils;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.Gun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.Zombie;
import com.mycompany.thewalkingtec.Game;
import java.util.ArrayList;
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
    
    //public void doCombat(ArrayList<Zombie> zombies, ArrayList<Gun> guns) {
        



    private boolean rangeCheck(Zombie zombie, Gun arma) {
        int distanciaX = Math.abs(zombie.getX() - arma.getX());
        int distanciaY = Math.abs(zombie.getY() - arma.getY());
        return distanciaX <= arma.getRange() && distanciaY <= arma.getRange();
    }

    private void doAttack(Zombie zombie, Gun arma) {
        int DPS = arma.getHitsPerSecond();
        int zombieHealth = zombie.getHealth();

        // Calcula el daño total infligido por el arma al zombie
        int totalDamage = DPS;

        // Actualiza la vida del zombie
        zombieHealth  -= totalDamage;
        zombie.setHealth(zombieHealth );

        //if (zombieHealth  <= 0) {
            // El zombie ha sido derrotado, quita su imagen de la matriz
           // game.deleteZombieFromMatrix(zombie);
        //}
   // }
    



    /*public void levelCompleted() {
        // Aquí puedes implementar la lógica para el final del nivel
        System.out.println("¡Nivel completado!");
    }*/


}
}

