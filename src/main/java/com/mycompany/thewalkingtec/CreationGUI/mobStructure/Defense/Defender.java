/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense;

/**
 *
 * @author Fabo
 */
public abstract class Defender {

    protected String name;
    protected String normalStateAppearance;   
    protected int fieldsInMatrix;
    protected int unlockLevel;
    protected int health;
    protected int x = 0;
    protected int y = 0;

    public Defender(String name, String normalStateAppearance, int fieldsInMatrix,
            int unlockLevel, int health) {

        this.name = name;
        this.normalStateAppearance = normalStateAppearance;
 
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

    public String getNormalStateAppearance() {
        return normalStateAppearance;
    }
    public void setAppearance(String normalStateAppearance) {
        this.normalStateAppearance = normalStateAppearance;
    }
    
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
}
