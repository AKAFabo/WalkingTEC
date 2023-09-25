
package com.mycompany.thewalkingtec.mobStructure.Defense;

public class Guns extends Defenders{

    private String type;
    private boolean isMovable = true;
    private int range;

    
    public Guns(String name, String appearance, int hitsPerSecond, int defenseLevel, int fieldsInMatrix,
            int unlockLevel, int health, String type, int range) {
        super(name, appearance, hitsPerSecond, defenseLevel, fieldsInMatrix, unlockLevel, health);
        this.type = type;
        this.range = range;
        this.isMovable = isMovable;
            }

    @Override
    public String toString(){

        String str = "";

        str += name + "\n";
        str += "Archivo de imagen: " + appearance + "\n";
        str += "Hits/Segundo: " + hitsPerSecond + "\n";
        str += "Nivel de defensa: " + defenseLevel + "\n";
        str += "Campos en la matriz: " + fieldsInMatrix + "\n";
        str += "Desbloqueble en nivel: " + unlockLevel + "\n";
        str += "Vida total: " + health + "\n";
        str += "Tipo de arma: " + type + "\n";
        str += "Rango en casillas: " + range + "\n";
        
        return str;



    }   
}
