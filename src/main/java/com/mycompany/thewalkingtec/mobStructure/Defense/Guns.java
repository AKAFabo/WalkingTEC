
package com.mycompany.thewalkingtec.mobStructure.Defense;

public class Guns extends Defenders{

    private String type;
    private int range;
    private int hitsPerSecond;
    
    public int getHitsPerSecond() {
        return hitsPerSecond;
    }
    public void setHitsPerSecond(int hitsPerSecond) {
        this.hitsPerSecond = hitsPerSecond;
    }


    
    public Guns(String name, String normalStateAppearance, String attackStateAppearance, int startLevel, int fieldsInMatrix,
            int unlockLevel, int health, String type, int range, int hitsPerSecond) {
        super(name, normalStateAppearance, attackStateAppearance, startLevel, fieldsInMatrix, unlockLevel, health);
        this.type = type;
        this.range = range;
        this.hitsPerSecond = hitsPerSecond;
            }
}
