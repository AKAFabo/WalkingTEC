/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec.mobStructure.Defense;

/**
 *
 * @author Fabo
 */
public class Blocks extends Defenders{

    private int blockResistance;
    public int getBlockResistance() {
        return blockResistance;
    }
    public void setBlockResistance(int blockResistance) {
        this.blockResistance = blockResistance;
    }

    public Blocks(String name, String appearance, int hitsPerSecond, int defenseLevel, int fieldsInMatrix,
            int unlockLevel, int health, int blockResistance) {
        super(name, appearance, hitsPerSecond, defenseLevel, fieldsInMatrix, unlockLevel, health);
        this.blockResistance = blockResistance;
    }



    
    
}
