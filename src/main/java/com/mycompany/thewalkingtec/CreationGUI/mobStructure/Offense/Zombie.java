
package com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.Gun;
import com.mycompany.thewalkingtec.Game;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
    public void setIsGunNotInRange(boolean b){
        this.isGunNotInRange = b;
    }
    
    private boolean isAlive = true;

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
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
    
    public void attack(Zombie z, Gun g, Game game, ArrayList<Gun> guns){    
        String txtFilePath = "src/main/resources/registro.txt";
        Thread attackThread = new Thread(new Runnable() {
            
            @Override
            public void run(){        
                z.writeAttack("Zombie: " + z.getName() + " en la posición " + z.getX() + "," + z.getY() + " ha sido atacado por el arma: " + g.getName());
                while (g.isIsAlive()) {
                    g.takeDamage(1); //CADA GOLPE EQUIVALE A 1 DE DAÑO
                    z.addTotalDamageGiven(1);
                    if (g.getHealth() <= 0){
                        g.setIsAlive(false);
                    }

                    try {
                        Thread.sleep(1000/z.getHitsPerSecond());
                    }   catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                String lineToWrite = g.getAttackRegister();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath, true))) {
                    // Escribir los datos en el archivo de texto, uno por línea
                    writer.write(lineToWrite); 
                    writer.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                game.deleteGunFromMatrix(g, z, guns);
                
            Gun newTarget = game.findNearestGun(z, guns);
            
            if (newTarget != null){
                if (newTarget != null && z.checkIsGunNotInRange()) {
                            game.moveZombieTowardsGun(z, newTarget);
                            
                            if (game.isZombieInRange(z, newTarget)){
                                z.setIsGunNotInRange(false);
                                z.attack(z, newTarget, game, guns);
                            }
                        }
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e){
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

