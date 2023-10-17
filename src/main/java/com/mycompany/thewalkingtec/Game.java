
package com.mycompany.thewalkingtec;

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
import com.mycompany.thewalkingtec.GameGUI.GameUtils.Combat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame {
    private JLayeredPane matrixPanel = new JLayeredPane();
    private JLabel[][] matriz = new JLabel[25][25];
    private boolean[][] casillasConImagen = new boolean[25][25];
    private JButton startGameButton;
    
    private Zombie[][] zombieMatrix = new Zombie[25][25]; //GUARDAR INFO DE COORDENADAS
    private Gun[][] gunMatrix = new Gun[25][25]; //GUARDAR  INFO DE COORDENADAS
    private JLabel[][] zombieLabels = new JLabel[25][25];
    
    private ArrayList<Gun> guns = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    
    private ImageIcon iconoSeleccionado = null; // Almacena el icono seleccionado
    private JLabel counterLabel;
    
    private int defenseCounter = 0;
    private int zombieCounter = 0;
    private int maxGenericCounter = 20;
    private int actualLevel = 0;
    
    private ArrayList<Gun> availableGuns;
    private ArrayList<Zombie> availableZombies;
    private ArrayList<Block> availableBlocks;

    public Game() {
        
        //Builds Matrix and adds listener to each i,j position
        availableGuns = loadGunsFromFiles();
        availableZombies = loadZombiesFromFiles();
        
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
                                showEntityInformation(x, y);
                                if (!casillasConImagen[x][y]) {
                                    if (iconoSeleccionado != null && defenseCounter < maxGenericCounter) {
                                        // Verifica si hay un icono seleccionado y coloca ese icono en la casilla                              
                                            casillasConImagen[x][y] = true;                                           

                                            for (Gun gun : availableGuns) {
                                                if (gun.getNormalStateAppearance().equals(iconoSeleccionado.getDescription())){
                                                    gunMatrix[x][y] = new Gun(gun.getName(),
                                                            gun.getNormalStateAppearance(), gun.getAttackStateAppearance(), 
                                                            gun.getFieldsInMatrix(), gun.getUnlockLevel(), 
                                                            gun.getHealth(), gun.getType(), gun.getRange(), gun.getHitsPerSecond());
                                                    gunMatrix[x][y].setX(x);
                                                    gunMatrix[x][y].setY(y);
                                                    guns.add(gunMatrix[x][y]);
                                                    defenseCounter += gunMatrix[x][y].getFieldsInMatrix();
                                                    updateCounter();
                                                    
                                                    ImageIcon gunIcon = new ImageIcon(gun.getNormalStateAppearance());
                                                    JLabel gunLabel = new JLabel(gunIcon);
                                                    gunLabel.setBounds(matriz[x][y].getBounds());
                                                    matrixPanel.add(gunLabel, JLayeredPane.PALETTE_LAYER);
                                                    zombieLabels[x][y] = gunLabel;
                                                    

                                                }                                    
                                            }
                                            //System.out.println("Label clickeado en la posición: (" + x + ", " + y + ")");
                                    
                                    return;
                                }
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
        for (Gun gun : availableGuns) {
            JButton gunButton = new JButton(new ImageIcon(gun.getNormalStateAppearance()));
            gunButton.addActionListener(e -> iconoSeleccionado = (ImageIcon) gunButton.getIcon());
            gunButtonPanel.add(gunButton);
        }
        
            
        startGameButton = new JButton("Iniciar nivel");
        startGameButton.addActionListener(e -> startGameLoop());
        
        //Agregar contador
        counterLabel = new JLabel(defenseCounter + "/" + maxGenericCounter);
        gunButtonPanel.add(counterLabel);
        
        //AGREGAR BOTONES
        
        JPanel buttonPanelContainers = new JPanel();
        buttonPanelContainers.setLayout(new FlowLayout());
             
        buttonPanelContainers.add(gunButtonPanel);
        buttonPanelContainers.add(startGameButton);
        
        panelContenedor.add(buttonPanelContainers, BorderLayout.SOUTH);
        
        add(panelContenedor);
        
        //startGameLoop();
    }
    
    public void updateCounter(){
        counterLabel.setText(defenseCounter + "/" + maxGenericCounter);
    }
    
    public void startGameLoop() {
        
        Combat combat = new Combat(this, guns);
        Thread gameThread;
        
        ArrayList<Zombie> zombiesInLevel = generateZombies(maxGenericCounter);
        
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    
                    for (Zombie zombie : zombiesInLevel) {
                        
                        Gun nearestGun = findNearestGun(zombie, guns);
                        if (nearestGun != null && zombie.checkIsGunNotInRange()) {
                            moveZombieTowardsGun(zombie, nearestGun);
                            
                            if (isZombieInRange(zombie, nearestGun)){
                                zombie.changeIsGunInRange();
                                zombie.attack(zombie, nearestGun);
                            }
                        }
                    }
                    
                    /*for (Gun gun : guns) {
                        Zombie nearestZombie = findNearestZombie(gun);
                        if (nearestZombie != null){
                            gun.attack(nearestZombie);
                        }
                    }*/
                    
                   /* if (zombiesInLevel.isEmpty()){
                        
                        combat.levelCompleted();
                        break;
                    }*/

                    try {
                        Thread.sleep(1000); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        gameThread.start(); // Inicia el hilo del juego
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
       
//END ZOMBIE ARRAY    
    
//MATRIX LABEL MANAGERS
      
   public ArrayList<Zombie> generateZombies(int maxZombies) {
    ArrayList<Zombie> zombiesInLevel = new ArrayList<>();

    Thread zombieGenerationThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (zombieCounter < maxGenericCounter && zombiesInLevel.size() < maxZombies) {
                int border = (int) (Math.random() * 4); // 0, 1, 2 o 3 para elegir un borde al azar
                int x = 0, y = 0;

                do {
                    switch (border) {
                        case 0:
                            x = (int) (Math.random() * 25);
                            y = 0;
                            break;
                        case 1:
                            x = 24;
                            y = (int) (Math.random() * 25);
                            break;
                        case 2:
                            x = (int) (Math.random() * 25);
                            y = 24;
                            break;
                        case 3:
                            x = 0;
                            y = (int) (Math.random() * 25);
                            break;
                    }
                } while (zombieMatrix[x][y] != null); // Verifica si la posición ya está ocupada

                int randomIndex = (int) (Math.random() * availableZombies.size());
                Zombie randomZombie = availableZombies.get(randomIndex);

                if (randomZombie != null) {
                    // Crea una etiqueta para el zombie y agrégala a la matriz
                    ImageIcon zombieIcon = new ImageIcon(randomZombie.getNormalStateAppearance());
                    JLabel zombieLabel = new JLabel(zombieIcon);
                    zombieLabel.setBounds(x * 25, y * 25, 25, 25);
                    matrixPanel.add(zombieLabel, JLayeredPane.PALETTE_LAYER);
                    zombieLabels[x][y] = zombieLabel;
                    // Establece la posición del zombie y agrégalo a la lista de zombies en el nivel
                    Zombie z = new Zombie(randomZombie.getName(), randomZombie.getNormalStateAppearance(),
                            randomZombie.getAttackStateAppearance(), randomZombie.getHitsPerSecond(), randomZombie.getRange(),
                            randomZombie.getFieldsInMatrix(), randomZombie.getUnlockLevel(), randomZombie.getType(), randomZombie.getHealth());
                    z.setX(x);
                    z.setY(y);
                    zombiesInLevel.add(z);
                    zombieMatrix[x][y] = z;
                    casillasConImagen[x][y] = true;
                    zombieCounter += z.getFieldsInMatrix();
                }

                try {
                    Thread.sleep(700); //Agregar retraso para mas interaccion
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    zombieGenerationThread.start();

    return zombiesInLevel;
}

   public Gun findNearestGun(Zombie zombie, ArrayList<Gun> guns) {
        Gun nearestGun = null;
        double shortestDistance = Double.MAX_VALUE;

        // Obtiene la posición actual del zombie
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();

        for (Gun gun : guns) {
            // Obtiene la posición actual del arma
            int gunX = gun.getX();
            int gunY = gun.getY();
            // Calcula la distancia entre el zombie y el arma
            double distance = Math.sqrt(Math.pow(zombieX - gunX, 2) + Math.pow(zombieY - gunY, 2));
            // Compara si esta arma está más cerca que la anteriormente encontrada
            if (distance < shortestDistance) {
                nearestGun = gun;
                shortestDistance = distance;
            }
        }
        return nearestGun;
    }
   
   public void moveZombieTowardsGun(Zombie zombie, Gun nearestGun) {
        // Obtiene la posición actual del zombie
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();

        // Obtiene la posición del arma más cercana
        int gunX = nearestGun.getX();
        int gunY = nearestGun.getY();

        // Calcula la dirección del movimiento
        int deltaX = 0, deltaY = 0;

        if (zombieX < gunX) {
            deltaX = 1;
        } else if (zombieX > gunX) {
            deltaX = -1;
        }

        if (zombieY < gunY) {
            deltaY = 1;
        } else if (zombieY > gunY) {
            deltaY = -1;
        }

        // Calcula las coordenadas de destino
        int destinationX = zombieX + deltaX;
        int destinationY = zombieY + deltaY;

        // Verifica si la casilla de destino está ocupada por otro zombie
        if (zombieMatrix[destinationX][destinationY] == null) {
            // Mueve el zombie en la matriz
            zombieMatrix[zombieX][zombieY] = null;
            zombieMatrix[destinationX][destinationY] = zombie;

            // Mueve el label que representa al zombie
            JLabel zombieLabel = zombieLabels[zombie.getX()][zombie.getY()];
            zombieLabel.setBounds(destinationX * 25, destinationY * 25, 25, 25);
            zombieLabels[destinationX][destinationY] = zombieLabel;
            zombieLabels[zombieX][zombieY] = null;

            // Actualiza las coordenadas del zombie
            zombie.setX(destinationX);
            zombie.setY(destinationY);
        }
    }

   
   public boolean isZombieInRange(Zombie zombie, Gun gun) {
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();
        int gunX = gun.getX();
        int gunY = gun.getY();
        int range = zombie.getRange();

        // Calcula la distancia entre el zombie y el arma
        int distance = Math.abs(zombieX - gunX) + Math.abs(zombieY - gunY);

        // Comprueba si la distancia es menor o igual al rango del arma
        return distance <= range;
    }
   
//MATRIX LABEL MANAGERS ENDS  
    
    public void showEntityInformation(int x, int y) {
        if (casillasConImagen[x][y]) {
            // La casilla está ocupada, obtén la entidad (zombie o arma) en esa posición
            Zombie zombie = zombieMatrix[x][y];
            Gun gun = gunMatrix[x][y];

            if (zombie != null) {
                // Se hizo clic en un zombie
                System.out.println("-------------------------");
                System.out.println("Información del Zombie:");
                System.out.println("Nombre: " + zombie.getName());
                System.out.println("Tipo: " + zombie.getType());
                System.out.println("Salud: " + zombie.getHealth());
                System.out.println("Espacios: " + zombie.getFieldsInMatrix());
                System.out.println("-------------------------");
            } else if (gun != null) {
                System.out.println("-------------------------");
                System.out.println("Información del Arma:");
                System.out.println("Nombre: " + gun.getName());
                System.out.println("Tipo: " + gun.getType());
                System.out.println("Salud: " + gun.getHealth());
                System.out.println("Espacios: " + gun.getFieldsInMatrix());
                System.out.println("-------------------------");
            }
        }
    }
    
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