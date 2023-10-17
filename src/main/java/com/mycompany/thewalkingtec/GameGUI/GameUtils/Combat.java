/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec.GameGUI.GameUtils;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.Gun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.Zombie;
import java.util.ArrayList;

/**
 *
 * @author Fabo
 */
public class Combat {
    
    public static void doCombat(ArrayList<Zombie> zombies, ArrayList<Gun> armas) {
        
        for (Zombie zombie : zombies) {
                for (Gun arma : armas) {
                    if (rangeCheck(zombie, arma)) {
                        doAttack(zombie, arma);
                    }
                }
            }
    }

    private static boolean rangeCheck(Zombie zombie, Gun arma) {
        int distanciaX = Math.abs(zombie.getX() - arma.getX());
        int distanciaY = Math.abs(zombie.getY() - arma.getY());
        return distanciaX <= arma.getRange() && distanciaY <= arma.getRange();
    }

    private static void doAttack(Zombie zombie, Gun arma) {
        int DPS = arma.getHitsPerSecond();
        int zombieHealth = zombie.getHealth();

        // Calcula el daño total infligido por el arma al zombie
        int totalDamage = DPS;

        // Actualiza la vida del zombie
        zombieHealth  -= totalDamage;
        zombie.setHealth(zombieHealth );

        if (zombieHealth  <= 0) {
            // El zombie ha sido derrotado, quita su imagen de la matriz
            deleteZombie(zombie);
        }
    }

    private static void deleteZombie(Zombie zombie) {
        // Elimina la imagen del zombie de la matriz y de la lista de zombies
        // Aquí debes implementar la lógica específica para eliminar la imagen y actualizar las estructuras de datos.
    }
}

