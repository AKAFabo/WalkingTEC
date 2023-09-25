
package com.mycompany.thewalkingtec.mobStructure.Offense;

public abstract class Zombies {

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String appearance;
    public String getAppearance() {
        return appearance;
    }
    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    private int hitsPerSecond;
    public int getHitsPerSecond() {
        return hitsPerSecond;
    }
    public void setHitsPerSecond(int hitsPerSecond) {
        this.hitsPerSecond = hitsPerSecond;
    }

    private int defenseLevel;
    public int getDefenseLevel() {
        return defenseLevel;
    }
    public void setDefenseLevel(int defenseLevel) {
        this.defenseLevel = defenseLevel;
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
    
    public Zombies(String name, String appearance, int hitsPerSecond, int defenseLevel, int fieldsInMatrix,
            int unlockLevel) {
        this.name = name;
        this.appearance = appearance;
        this.hitsPerSecond = hitsPerSecond;
        this.defenseLevel = defenseLevel;
        this.fieldsInMatrix = fieldsInMatrix;
        this.unlockLevel = unlockLevel;
    }
}

