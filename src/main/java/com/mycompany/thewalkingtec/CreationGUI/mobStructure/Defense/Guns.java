
package com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense;

public class Guns extends Defenders{

    private String type;
    private int range;
    private int hitsPerSecond;
    private String attackStateAppearance;
    
    public int getHitsPerSecond() {
        return hitsPerSecond;
    }
    public void setHitsPerSecond(int hitsPerSecond) {
        this.hitsPerSecond = hitsPerSecond;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    
    public int getRange(){
        return range;
    }
    public void setRange(int range){
        this.range = range;
    }
    
    public void setAttackStateAppearance(String attackStateAppearance){
        this.attackStateAppearance = attackStateAppearance;
    }
    public String getAttackStateAppearance(){
        return attackStateAppearance;
    }
    
    public Guns(String name, String normalStateAppearance, String attackStateAppearance, int startLevel, int fieldsInMatrix,
            int unlockLevel, int health, String type, int range, int hitsPerSecond) {
        super(name, normalStateAppearance, startLevel, fieldsInMatrix, unlockLevel, health);
        this.attackStateAppearance = attackStateAppearance;
        this.type = type;
        this.range = range;
        this.hitsPerSecond = hitsPerSecond;
            }
}
