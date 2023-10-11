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
    protected String normalStateAppearance;   
    protected String attackStateAppearance;
    protected int startLevel; 
    protected int fieldsInMatrix;
    protected int unlockLevel;
    protected int health;

    public Defenders(String name, String normalStateAppearance, String attackStateAppearance, int startLevel, int fieldsInMatrix,
            int unlockLevel, int health) {

        this.name = name;
        this.normalStateAppearance = normalStateAppearance;
        this.attackStateAppearance = attackStateAppearance;
        this.startLevel = startLevel;
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

    public int getStartLevel() {
        return startLevel;
    }
    public void setStartLevel(int defenseLevel) {
        this.startLevel = defenseLevel;
    }

    public String getNormalStateAppearance() {
        return normalStateAppearance;
    }
    public void setAppearance(String normalStateAppearance) {
        this.normalStateAppearance = normalStateAppearance;
    }
    
    public String getAttackStateAppearance() {
        return attackStateAppearance;
    }
    public void setAttackStateAppearance(String attackStateAppearance) {
        this.attackStateAppearance = attackStateAppearance;
    }
    
}
