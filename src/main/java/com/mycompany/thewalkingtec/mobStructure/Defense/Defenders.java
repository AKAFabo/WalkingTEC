/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec.mobStructure.Defense;

/**
 *
 * @author Fabo
 */
public abstract class Defenders {

    protected String name;
    protected String appearance;   
    protected int hitsPerSecond;
    protected int defenseLevel; 
    protected int fieldsInMatrix;
    protected int unlockLevel;
    protected int health;

    public Defenders(String name, String appearance, int hitsPerSecond, int defenseLevel, int fieldsInMatrix,
            int unlockLevel, int health) {

        this.name = name;
        this.appearance = appearance;
        this.hitsPerSecond = hitsPerSecond;
        this.defenseLevel = defenseLevel;
        this.fieldsInMatrix = fieldsInMatrix;
        this.unlockLevel = unlockLevel;
        this.health = health;
    }



    public int getUnlockLevel() {
        return unlockLevel;
    }
    public void setUnlockLevel(int unlockLevel) {
        this.unlockLevel = unlockLevel;
    }
    
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }  

    public int getFieldsInMatrix() {
        return fieldsInMatrix;
    }
    public void setFieldsInMatrix(int fieldsInMatrix) {
        this.fieldsInMatrix = fieldsInMatrix;
    }

    public int getDefenseLevel() {
        return defenseLevel;
    }
    public void setDefenseLevel(int defenseLevel) {
        this.defenseLevel = defenseLevel;
    }

    public int getHitsPerSecond() {
        return hitsPerSecond;
    }
    public void setHitsPerSecond(int hitsPerSecond) {
        this.hitsPerSecond = hitsPerSecond;
    }

    public String getAppearance() {
        return appearance;
    }
    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }
}

