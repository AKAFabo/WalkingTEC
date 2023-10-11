
package com.mycompany.thewalkingtec.mobStructure.Offense;

public abstract class Zombie {

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

    private int startLevel;
    public int getStartLevel() {
        return startLevel;
    }
    public void setStartLevel(int startLevel) {
        this.startLevel = startLevel;
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
    public Zombie(String name, String normalStateAppearance, String attackStateAppearance, int hitsPerSecond, int startLevel, int fieldsInMatrix,
            int unlockLevel, String type) {
        this.name = name;
        this.normalStateAppearance = normalStateAppearance;
        this.attackStateAppearance = attackStateAppearance;
        this.hitsPerSecond = hitsPerSecond;
        this.startLevel = startLevel;
        this.fieldsInMatrix = fieldsInMatrix;
        this.unlockLevel = unlockLevel;
        this.type = type;
    }
}

