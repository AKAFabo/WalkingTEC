
package com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Defender;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.Zombie;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Gun extends Defender{

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
    
    public Gun(String name, String normalStateAppearance, String attackStateAppearance, int fieldsInMatrix,
            int unlockLevel, int health, String type, int range, int hitsPerSecond) {
        super(name, normalStateAppearance, fieldsInMatrix, unlockLevel, health);
        this.attackStateAppearance = attackStateAppearance;
        this.type = type;
        this.range = range;
        this.hitsPerSecond = hitsPerSecond;
            }
    
    public JButton createButton() {
        JButton button = new JButton(new ImageIcon(getNormalStateAppearance()));
        button.setToolTipText(getName()); // Establecer el nombre del arma como tooltip
        return button;
    }
    
    public void attack(Zombie z){
        while (this.getHealth() > 0) {
            z.takeDamage(1); //CADA GOLPE EQUIVALE A 1 DE DAÑO
            
            try {
                Thread.sleep(1000/this.getHitsPerSecond());
            }   catch (InterruptedException e) {
                e.printStackTrace();
            }
                    
        }
    }
    
    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0){
            this.health = 0;
        }
    }
    
    
}
