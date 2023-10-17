
package com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.Gun;

public class Zombie {
    
    private int x = 0;
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
        
    private int y = 0;
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
       
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String normalStateAppearance;
    public String getNormalStateAppearance() {
        return normalStateAppearance;
    }
    public void setAppearance(String normalStateAppearance) {
        this.normalStateAppearance = normalStateAppearance;
    }
    
    private String attackStateAppearance;
    public String getAttackStateAppearance(){
        return attackStateAppearance;
    }
    public void setAttackStateAppearance(String attackStateAppearance){
        this.attackStateAppearance = attackStateAppearance;
    }
    
    private int hitsPerSecond;
    public int getHitsPerSecond() {
        return hitsPerSecond;
    }
    public void setHitsPerSecond(int hitsPerSecond) {
        this.hitsPerSecond = hitsPerSecond;
    }

    private int range;
    public int getRange() {
        return range;
    }
    public void setStartLevel(int range) {
        this.range = range;
    }

    private int fieldsInMatrix;
    public int getFieldsInMatrix() {
        return fieldsInMatrix;
    }
    public void setFieldsInMatrix(int fieldsInMatrix) {
        this.fieldsInMatrix = fieldsInMatrix;
    }

    private int unlockLevel;
    public int getUnlockLevel() {
        return unlockLevel;
    }
    public void setUnlockLevel(int unlockLevel) {
        this.unlockLevel = unlockLevel;
    }
    
    private String type;
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    
    private int health;
    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    
    private boolean isGunNotInRange = true;
    public boolean checkIsGunNotInRange(){
        return isGunNotInRange;
    }
    public void changeIsGunInRange(){
        this.isGunNotInRange = !this.isGunNotInRange;
    }
    
    public Zombie(String name, String normalStateAppearance, String attackStateAppearance, int hitsPerSecond, int range, int fieldsInMatrix, int unlockLevel, String type, int health) {
        this.name = name;
        this.normalStateAppearance = normalStateAppearance;
        this.attackStateAppearance = attackStateAppearance;
        this.hitsPerSecond = hitsPerSecond;
        this.range = range;
        this.fieldsInMatrix = fieldsInMatrix;
        this.unlockLevel = unlockLevel;
        this.type = type;
        this.health = health;
    }
    
    public void attack(Zombie z, Gun g){
          
        Thread attackThread = new Thread(new Runnable() {
            @Override
            public void run(){        
                while (z.getHealth() > 0) {
                    g.takeDamage(1); //CADA GOLPE EQUIVALE A 1 DE DAÑO

                    try {
                        Thread.sleep(1000/z.getHitsPerSecond());
                    }   catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }      
        });
        
        attackThread.start();
     }
    
    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0){
            this.health = 0;
        }
    }
}

