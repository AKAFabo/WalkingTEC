
package com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Defender;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.Zombie;
import com.mycompany.thewalkingtec.Game;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Gun extends Defender{
    
    private String type;
    private int range;
    private int hitsPerSecond;
    private String attackStateAppearance;
    private boolean isAttacking = false;
    private String scaledAppearance = "";
    private boolean isAlive = true;

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public String getScaledAppearance() {
        return scaledAppearance;
    }

    public void setScaledAppearance(String scaledAppearance) {
        this.scaledAppearance = scaledAppearance;
    }
    
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
    
    public boolean checkIsAttacking(){
        return isAttacking;
    }
    public void setAttackState(boolean b){
        this.isAttacking = b;
    }
    
    private String attackRegister = ""; 
    public void writeAttack(String attack){
        this.attackRegister += attack + "\n";
    }  
    public String getAttackRegister(){
        return attackRegister;
    }

    
    private int totalDamageGiven;
    public void addTotalDamageGiven(int damage){
        this.totalDamageGiven += damage;
    }
    public int getTotalDamageGiven(){
        return totalDamageGiven;
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
    
    public void attack(Gun g, Zombie z, Game game, ArrayList<Zombie> zombiesInLevel){      
        String txtFilePath = "src/main/resources/registro.txt";

        Thread attackThread = new Thread(new Runnable(){
            @Override
            public void run(){
                g.writeAttack("Arma: " + g.getName() + " ha sido atacada por el zombie: " + z.getName() + " en la posición " + z.getX() + "," + z.getY());
                while (z.isIsAlive()) {
                    z.takeDamage(1); //CADA GOLPE EQUIVALE A 1 DE DAÑO
                    g.addTotalDamageGiven(1);
                    
                    if (z.getHealth() <= 0){
                        z.setIsAlive(false);
                    }

                    try {
                         Thread.sleep(1000/g.getHitsPerSecond());
                    }   catch (InterruptedException e) {
                        e.printStackTrace();
                     }                 
                }
                String lineToWrite = z.getAttackRegister();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath, true))) {
                    // Escribir los datos en el archivo de texto, uno por línea
                    writer.write(lineToWrite); 
                    writer.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                game.deleteZombieFromMatrix(z, g, zombiesInLevel);
                
            Zombie newTarget = game.findNearestZombie(g, zombiesInLevel);

            if (newTarget != null){
                g.attack(g, newTarget, game, zombiesInLevel);
            } else{
                try {
                    Thread.sleep(3000);
                }   catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
                
                
            }

         });    
        attackThread.start();
    }
    
    public void takeDamage(int damage){
        this.health -= damage;
    }
    
    public void upgrade(int value){
        this.health += (int) (value*this.getHealth())/100;
        this.hitsPerSecond += (int) (value*this.getHitsPerSecond())/100;
    }
}

