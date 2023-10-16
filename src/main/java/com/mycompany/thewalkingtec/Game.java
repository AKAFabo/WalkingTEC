
package com.mycompany.thewalkingtec;

import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Builder.mobBuilder;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Block;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.AerialGun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.ContactGun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.Gun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.ImpactGun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.MidRangeGun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Defense.Guns.MultipleAttackGun;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.AerialZombie;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.ContactZombie;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.CrashZombie;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.MidRangeZombie;
import com.mycompany.thewalkingtec.CreationGUI.mobStructure.Offense.Zombie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Game extends JFrame {
    private JLayeredPane matrixPanel = new JLayeredPane();
    private JLabel[][] matriz = new JLabel[25][25];
    private boolean[][] casillasConImagen = new boolean[25][25];
    private ImageIcon iconoSeleccionado = null; // Almacena el icono seleccionado
    private JLabel counterLabel;
    
    private int defenseCounter = 0;
    private int maxGenericCounter = 20;
    
    private ArrayList<Gun> guns;
    private ArrayList<Zombie> zombies;
    private ArrayList<Block> blocks;

    public Game() {
        
        //Builds Matrix and adds listener to each i,j position
        guns = loadGunsFromFiles();
        zombies = loadZombiesFromFiles();
        
        setTitle("The Walking TEC");
        setSize(642, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelContenedor = new JPanel(new BorderLayout());
        JPanel panelMarco = new JPanel();
        panelMarco.setBackground(Color.BLACK); // Puedes establecer el color que desees
        panelMarco.setPreferredSize(new Dimension(1000, 1000)); // Tamaño del marco

        // Agregar el panel de la matriz al panel del marco
        panelMarco.add(matrixPanel );

        // Agregar el panel del marco al panel principal
        //panelContenedor.add(panelMarco, BorderLayout.CENTER);
        matrixPanel .setLayout(null);

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                matriz[i][j] = new JLabel(new ImageIcon("src/main/resources/image/grass.png"));
                matriz[i][j].setBounds(i * 25, j * 25, 25, 25); // Posición y tamaño del JLabel
                matrixPanel .add(matriz[i][j], JLayeredPane.DEFAULT_LAYER);
                casillasConImagen[i][j] = false;

                matriz[i][j].addMouseListener(new MouseAdapter() {
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    for (int x = 0; x < 25; x++) {
                        for (int y = 0; y < 25; y++) {
                            if (matriz[x][y] == label) {
                                if (!casillasConImagen[x][y]) {
                                    if (iconoSeleccionado != null && defenseCounter < maxGenericCounter) {
                                        // Verifica si hay un icono seleccionado y coloca ese icono en la casilla
                                        boolean canPlace = canPlaceGun(x, y);
                                        if (canPlace) {
                                            
                                            
                                            
                                            ImageIcon icono = new ImageIcon(iconoSeleccionado.getImage());
                                            JLabel imagenSuperpuesta = new JLabel(icono);
                                            imagenSuperpuesta.setBounds(matriz[x][y].getBounds());
                                            matrixPanel .add(imagenSuperpuesta, JLayeredPane.PALETTE_LAYER);
                                            casillasConImagen[x][y] = true;
                                            defenseCounter++;
                                            updateCounter();
                                            System.out.println("Label clickeado en la posición: (" + x + ", " + y + ")");
                                            
                                        }
                                    }
                                    return;
                                }
            }
        }
    }
}
                });
            }
        }
        panelContenedor.add(matrixPanel , BorderLayout.CENTER);
            
        // Crear botones para las armas
        JPanel gunButtonPanel = new JPanel();
        
        for (Gun gun : guns) {
            JButton gunButton = new JButton(new ImageIcon(gun.getNormalStateAppearance()));
            gunButton.addActionListener(e -> iconoSeleccionado = (ImageIcon) gunButton.getIcon());
            gunButtonPanel.add(gunButton);
        }
        
        
        //SOLO PRUEBA: CREAR BOTONES PARA ZOMBIES
        JPanel zombieButtonPanel = new JPanel();
        
        for (Zombie zombie : zombies){
            JButton zombieButton = new JButton(new ImageIcon(zombie.getNormalStateAppearance()));
            zombieButton.addActionListener(e -> iconoSeleccionado = (ImageIcon) zombieButton.getIcon());
            zombieButtonPanel.add(zombieButton);
            
            
        }
        
        //Agregar contador
        counterLabel = new JLabel(defenseCounter + "/" + maxGenericCounter);
        gunButtonPanel.add(counterLabel);
        
        //AGREGAR BOTONES
        
        JPanel buttonPanelContainers = new JPanel();
        buttonPanelContainers.setLayout(new FlowLayout());
             
        buttonPanelContainers.add(gunButtonPanel);
        buttonPanelContainers.add(zombieButtonPanel);
        
        panelContenedor.add(buttonPanelContainers, BorderLayout.SOUTH);
        
        add(panelContenedor);
    }
    
    public void updateCounter(){
        counterLabel.setText(defenseCounter + "/" + maxGenericCounter);
    }
    
    
//GUN ARRAY CREATION STARTS
    public static ArrayList<Gun> loadGunsFromFiles() {
        ArrayList<Gun> guns = new ArrayList<>();
        String folderPath = "src/main/java/com/mycompany/thewalkingtec/CreationGUI/mobFiles/Guns/";

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".txt")) {
                    Gun gun = createGunFromFile(file);
                    if (gun != null) {
                        guns.add(gun);
                    }
                }
            }
        }

        return guns;
    }
    private static Gun createGunFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String name = reader.readLine();
            String type = reader.readLine();
            int health = Integer.parseInt(reader.readLine());
            int hitsPerSecond = Integer.parseInt(reader.readLine());
            int range = Integer.parseInt(reader.readLine());
            int unlockLevel = Integer.parseInt(reader.readLine());
            int fieldsInMatrix = Integer.parseInt(reader.readLine());
            String normalStateAppearance = reader.readLine();
            String attackStateAppearance = reader.readLine();

            // Determina el tipo y crea el objeto de arma correspondiente
            Gun gun = null;
            switch (type) {
                case "Aerea" -> gun = new AerialGun(name, normalStateAppearance, attackStateAppearance, fieldsInMatrix, unlockLevel, health, type, range, hitsPerSecond);
                case "De contacto" -> gun = new ContactGun(name, normalStateAppearance, attackStateAppearance, fieldsInMatrix, unlockLevel, health, type, range, hitsPerSecond);
                case "Impacto" -> gun = new ImpactGun(name, normalStateAppearance, attackStateAppearance, fieldsInMatrix, unlockLevel, health, type, range, hitsPerSecond);
                case "Medio alcance" -> gun = new MidRangeGun(name, normalStateAppearance, attackStateAppearance, fieldsInMatrix, unlockLevel, health, type, range, hitsPerSecond);
                case "Ataque multiple" -> gun = new MultipleAttackGun(name, normalStateAppearance, attackStateAppearance, fieldsInMatrix, unlockLevel, health, type, range, hitsPerSecond);
                default -> {
                }
            }
            // Tipo de arma desconocido

            return gun;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
 //GUN ARRAY CREATION ENDS
    
//ZOMBIE ARRAY CREATION STARTS
    
    private static ArrayList<Zombie> loadZombiesFromFiles(){
        ArrayList<Zombie> zombies = new ArrayList<>();
        String folderPath = "src/main/java/com/mycompany/thewalkingtec/CreationGUI/mobFiles/Zombies/";
        
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".txt")) {
                    Zombie zombie = createZombieFromFile(file);
                    if (zombie != null) {
                        zombies.add(zombie);
                    }
                }
            }
        }

        return zombies;
    }
    
     private static Zombie createZombieFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String name = reader.readLine();
            String type = reader.readLine();
            int health = Integer.parseInt(reader.readLine());
            int hitsPerSecond = Integer.parseInt(reader.readLine());
            int range = Integer.parseInt(reader.readLine());
            int unlockLevel = Integer.parseInt(reader.readLine());
            int fieldsInMatrix = Integer.parseInt(reader.readLine());
            String normalStateAppearance = reader.readLine();
            String attackStateAppearance = reader.readLine();

            // Determina el tipo y crea el objeto de zombie correspondiente
            Zombie zombie = null;
            switch (type) {
                case "Aereo" -> zombie = new AerialZombie(name, normalStateAppearance, attackStateAppearance, hitsPerSecond, range, fieldsInMatrix, type, unlockLevel, health);
                case "De contacto" -> zombie = new ContactZombie(name, normalStateAppearance, attackStateAppearance, hitsPerSecond, range, fieldsInMatrix, type, unlockLevel, health);
                case "De choque" -> zombie = new CrashZombie(name, normalStateAppearance, attackStateAppearance, hitsPerSecond, range, fieldsInMatrix, type, unlockLevel, health);
                case "Medio alcance" -> zombie = new MidRangeZombie(name, normalStateAppearance, attackStateAppearance, hitsPerSecond, range, fieldsInMatrix, type, unlockLevel, health);
               
                default -> {
                }
            }
            // Tipo de arma desconocido

            return zombie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        
    
     
     
     
//MATRIX LABEL MANAGERS
    
    private boolean canPlaceGun(int x, int y) {
    // Obtener el rango del arma seleccionada
    int selectedGunRange = getSelectedGunRange();

    // Verificar si hay un arma dentro del rango de la casilla
    for (int i = x - selectedGunRange; i <= x + selectedGunRange; i++) {
        for (int j = y - selectedGunRange; j <= y + selectedGunRange; j++) {
            if (i >= 0 && i < 25 && j >= 0 && j < 25 && casillasConImagen[i][j]) {
                // Si hay un arma dentro del rango, no se puede colocar
                return false;
            }
        }
    }

    return true;
}

    private int getSelectedGunRange() {
        // Obtener el rango del arma seleccionada (en este caso, simplemente obtén el rango de la arma actual)
        if (iconoSeleccionado != null) {
            for (Gun gun : guns) {
                if (gun.getNormalStateAppearance().equals(iconoSeleccionado.getDescription())) {
                    return gun.getRange();
                }
            }
        }
        return 0;
    }
    
 
//MATRIX LABEL MANAGERS ENDS  
    
    
    public static void main(String[] args) {
      
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game example = new Game();
                example.setVisible(true);
                
            }
        });
    }

}