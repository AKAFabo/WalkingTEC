
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


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
    private JLabel levelInGame;
    private JPanel infoPanel;
    private JTextArea infoTextArea;
    
    private int defenseCounter = 0;
    private int zombieCounter = 0;
    private int maxGenericCounter = 20;
    private int actualLevel = 1;
    
    private ArrayList<Gun> availableGuns;
    private ArrayList<Zombie> availableZombies;
    private ArrayList<Block> availableBlocks;
    

    public Game() {
        
        availableGuns = loadGunsFromFiles();
        availableZombies = loadZombiesFromFiles();
        
        setTitle("The Walking TEC");
        setSize(642, 900);
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
        
        clearMatrix();

        panelContenedor.add(matrixPanel , BorderLayout.CENTER);
            
        JPanel gunButtonPanel = new JPanel();
        
        

        for (Gun gun : availableGuns) {
            String imagePath = gun.getNormalStateAppearance(); // Ruta de la imagen
            ImageIcon originalIcon = new ImageIcon(imagePath); // Crear un ImageIcon a partir de la ruta

            // Reescalar la imagen al tamaño deseado (25x25)
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

            // Crear un nuevo ImageIcon con la imagen reescalada
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JButton gunButton = new JButton(scaledIcon);
            gunButton.addActionListener(e -> iconoSeleccionado = originalIcon);
            gunButtonPanel.add(gunButton);
            gun.setScaledAppearance(scaledIcon.getDescription());
        }
            
        startGameButton = new JButton("Iniciar nivel");
        startGameButton.addActionListener(e -> startGameLoop());
        
        //Agregar contador
        counterLabel = new JLabel(defenseCounter + "/" + maxGenericCounter);
        gunButtonPanel.add(counterLabel);
        
        //Agregar Nivel actual
        levelInGame = new JLabel("Nivel: " + actualLevel);
        
        //AGREGAR BOTONES
        
        JPanel buttonPanelContainers = new JPanel();
        buttonPanelContainers.setLayout(new FlowLayout());
             
        buttonPanelContainers.add(gunButtonPanel);
        buttonPanelContainers.add(startGameButton);
        buttonPanelContainers.add(levelInGame);
        
        infoPanel = new JPanel();
        infoPanel.setBounds(650, 0, 200, 800); // Ajusta la posición y tamaño según tus necesidades
        infoPanel.setLayout(new BorderLayout());

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoPanel.add(infoTextArea, BorderLayout.CENTER);

        // Agregar el panel de información a la ventana
        buttonPanelContainers.add(infoPanel);
        
        panelContenedor.add(buttonPanelContainers, BorderLayout.SOUTH);
        
        add(panelContenedor);
        
        //startGameLoop();
    }
    
    public void updateGameLabels(){
        counterLabel.setText(defenseCounter + "/" + maxGenericCounter);
        levelInGame.setText("Nivel: " + actualLevel);
    }
    
    public void startGameLoop() {
        Thread gameThread;
        Game currentGame = this;
        Combat combat = new Combat(currentGame, guns);
        
        ArrayList<Zombie> zombiesInLevel = generateZombies(maxGenericCounter); 

        gameThread = new Thread(new Runnable() {     
            @Override
            public void run() {
               String txtFilePath = "src/main/resources/registro.txt";
               String lineToWrite = "Nivel " + actualLevel;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath, true))) {
                    // Escribir los datos en el archivo de texto, uno por línea
                    writer.write(lineToWrite); 
                    writer.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (!zombiesInLevel.isEmpty()) {
                    
                    if (guns.isEmpty()){
                        break;
                    }

                    for (Zombie zombie : zombiesInLevel) {
                        
                        Gun nearestGun = findNearestGun(zombie, guns);
                        if (nearestGun != null && zombie.checkIsGunNotInRange()) {
                            moveZombieTowardsGun(zombie, nearestGun);
                            
                            if (isZombieInRange(zombie, nearestGun)){
                                zombie.setIsGunNotInRange(false);
                                zombie.attack(zombie, nearestGun, currentGame, guns);
                            }
                        }
                    }
                    
                    for (Gun gun : guns) {
                        for (Zombie zombie : zombiesInLevel) {
                            if (isZombieInRange(zombie, gun) && !gun.checkIsAttacking()){
                                gun.setAttackState(true);
                                gun.attack(gun, zombie, currentGame, zombiesInLevel);
                            }
                        }
                    }

                    try {
                        Thread.sleep(1000); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
               if (actualLevel == 10) {
                    JFrame congratulationsFrame = new JFrame("¡Felicidades, ganaste el juego!");
                    congratulationsFrame.setSize(400, 200);
                    congratulationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel congratulationsPanel = new JPanel();
                    congratulationsPanel.setLayout(new BorderLayout());

                    JLabel congratulationsLabel = new JLabel("¡Felicidades, ganaste el juego!");
                    congratulationsLabel.setHorizontalAlignment(JLabel.CENTER);
                    congratulationsPanel.add(congratulationsLabel, BorderLayout.CENTER);

                    JButton exitButton = new JButton("Vencer Juego");
                    exitButton.addActionListener(e -> {
                        congratulationsFrame.dispose(); // Cierra el cuadro de diálogo
                        dispose(); // Cierra la ventana principal
                    });

                    JButton continueButton = new JButton("Continuar Juego");
                    continueButton.addActionListener(e -> {
                        congratulationsFrame.dispose(); // Cierra el cuadro de diálogo
                        // Coloca aquí la lógica para continuar el juego, si es necesario
                    });

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(exitButton);
                    buttonPanel.add(continueButton);
                    congratulationsPanel.add(buttonPanel, BorderLayout.SOUTH);

                    congratulationsFrame.add(congratulationsPanel);
                    congratulationsFrame.setLocationRelativeTo(null); // Centra el cuadro de diálogo en la pantalla
                    congratulationsFrame.setVisible(true);
                }
               
               if (zombiesInLevel.isEmpty()) {
                   
                   combat.levelCompleted();
                    actualLevel++;
                    maxGenericCounter += 5;
                    zombieCounter = 0;
                    defenseCounter = 0;
                    upgradeComponents();
                    updateGameLabels();
                    clearMatrix();
                   
                   
               }
               
               
               else {
                   
                    JFrame lostLevelFrame = new JFrame("Nivel Perdido");
                    lostLevelFrame.setSize(400, 200);
                    lostLevelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel lostLevelPanel = new JPanel();
                    lostLevelPanel.setLayout(new BorderLayout());

                    JLabel lostLevelLabel = new JLabel("¡Nivel Perdido!");
                    lostLevelLabel.setHorizontalAlignment(JLabel.CENTER);
                    lostLevelPanel.add(lostLevelLabel, BorderLayout.CENTER);

                    JButton restartButton = new JButton("Reiniciar Nivel");
                    restartButton.addActionListener(e -> {
                        clearMatrix();
                        defenseCounter = 0;
                        updateGameLabels();
                        // Coloca aquí la lógica para reiniciar el nivel, si es necesario
                        lostLevelFrame.dispose();
                    });

                    JButton advanceButton = new JButton("Avanzar Nivel");
                    advanceButton.addActionListener(e -> {
                        clearMatrix();
                        actualLevel++;
                        maxGenericCounter += 5;
                        zombieCounter = 0;
                        defenseCounter = 0;
                        upgradeComponents();
                        updateGameLabels();
                        lostLevelFrame.dispose();
                    });

                    JButton loseGameButton = new JButton("Perder Juego");
                    loseGameButton.addActionListener(e -> {
                        lostLevelFrame.dispose(); // Cierra el cuadro de diálogo
                        // Coloca aquí la lógica para perder el juego, si es necesario
                        // Esto podría incluir cerrar todas las ventanas o finalizar el juego por completo
                    });

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(restartButton);
                    buttonPanel.add(advanceButton);
                    buttonPanel.add(loseGameButton);
                    lostLevelPanel.add(buttonPanel, BorderLayout.SOUTH);

                    lostLevelFrame.add(lostLevelPanel);
                    lostLevelFrame.setLocationRelativeTo(null); // Centra el cuadro de diálogo en la pantalla
                    lostLevelFrame.setVisible(true);
                }
                
                
            }
        });

        gameThread.start(); // Inicia el hilo del juego
    }
    
    public void upgradeComponents(){
        
        for (Zombie zombie : availableZombies){
            
            int randomNumber = (int) (Math.random() * (20 - 5 + 1)+5);           
            zombie.upgrade(randomNumber);
        }
        for (Gun gun : availableGuns){
            int randomNumber = (int) (Math.random() * (20 - 5 + 1) + 5);
            gun.upgrade(randomNumber);
            
        }
    }
    
    public void clearMatrix() {
        matrixPanel.removeAll(); // Elimina todos los componentes en el JLayeredPane
        
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                gunMatrix[i][j] = null;
                zombieMatrix[i][j] = null;
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
                                               if (gun.getNormalStateAppearance().equals(iconoSeleccionado.getDescription()) && gun.getUnlockLevel() <= actualLevel){
                                                    gunMatrix[x][y] = new Gun(gun.getName(),
                                                            gun.getNormalStateAppearance(), gun.getAttackStateAppearance(), 
                                                            gun.getFieldsInMatrix(), gun.getUnlockLevel(), 
                                                            gun.getHealth(), gun.getType(), gun.getRange(), gun.getHitsPerSecond());
                                                    gunMatrix[x][y].setX(x);
                                                    gunMatrix[x][y].setY(y);
                                                    guns.add(gunMatrix[x][y]);
                                                    defenseCounter += gunMatrix[x][y].getFieldsInMatrix();
                                                    updateGameLabels();
                                                    
                                                    String imagePath = gun.getNormalStateAppearance(); // Ruta de la imagen
                                                    ImageIcon originalIcon = new ImageIcon(imagePath); // Crear un ImageIcon a partir de la ruta

                                                    // Reescalar la imagen al tamaño deseado (25x25)
                                                    Image originalImage = originalIcon.getImage();
                                                    Image scaledImage = originalImage.getScaledInstance(23, 23, Image.SCALE_SMOOTH);

                                                    // Crear un nuevo ImageIcon con la imagen reescalada
                                                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                   
                                                    JLabel gunLabel = new JLabel(scaledIcon);
                                                    gunLabel.setBounds(matriz[x][y].getBounds());
                                                    matrixPanel.add(gunLabel, JLayeredPane.PALETTE_LAYER);
                                                    zombieLabels[x][y] = gunLabel;
                                                    

                                                }                                   
                                            }
                                    
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

        // Repinta la matriz para que se reflejen los cambios
        matrixPanel.revalidate();
        matrixPanel.repaint();
        
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
                          
                if (randomZombie != null && randomZombie.getUnlockLevel() <= actualLevel) {
                    // Crea una etiqueta para el zombie y agrégala a la matriz
                    
                    String imagePath = randomZombie.getNormalStateAppearance(); // Ruta de la imagen
                    ImageIcon originalIcon = new ImageIcon(imagePath); // Crear un ImageIcon a partir de la ruta

                    // Reescalar la imagen al tamaño deseado (25x25)
                    Image originalImage = originalIcon.getImage();
                    Image scaledImage = originalImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

                    // Crear un nuevo ImageIcon con la imagen reescalada
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    ImageIcon zombieIcon = scaledIcon;
                    
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
                    zombieCounter += randomZombie.getFieldsInMatrix();
                }

            }
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
   
   public Zombie findNearestZombie(Gun gun, ArrayList<Zombie> zombies) {
        Zombie nearestZombie = null;
        double shortestDistance = Double.MAX_VALUE;

        int gunX = gun.getX();
        int gunY = gun.getY();
        int gunRange = gun.getRange();

        for (Zombie zombie : zombies) {
            int zombieX = zombie.getX();
            int zombieY = zombie.getY();

            // Calculate the distance between the gun and the zombie
            double distance = Math.sqrt(Math.pow(gunX - zombieX, 2) + Math.pow(gunY - zombieY, 2));

            // Check if the zombie is within the range of the gun and closer than the previous nearest zombie
            if (distance <= gunRange && distance < shortestDistance) {
                nearestZombie = zombie;
                shortestDistance = distance;
            }
        }
        return nearestZombie;
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
            casillasConImagen[zombieX][zombieY] = false;
            zombieMatrix[destinationX][destinationY] = zombie;
            casillasConImagen[destinationX][destinationY] = true;

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
   
   public boolean isGunInRange(Zombie zombie, Gun gun) {
        int zombieX = zombie.getX();
        int zombieY = zombie.getY();
        int gunX = gun.getX();
        int gunY = gun.getY();
        int range = gun.getRange();

        // Calcula la distancia entre el zombie y el arma
        int distance = Math.abs(zombieX - gunX) + Math.abs(zombieY - gunY);

        // Comprueba si la distancia es menor o igual al rango del arma
        return distance <= range;
    }
    
   public void deleteZombieFromMatrix(Zombie zombie, Gun gun, ArrayList<Zombie> zombiesInLevel) {
        int x = zombie.getX();
        int y = zombie.getY();
        gun.setAttackState(false);

        if (zombieLabels[x][y] != null) {
            matrixPanel.remove(zombieLabels[x][y]);
            zombieLabels[x][y] = null;
            casillasConImagen[x][y] = false;

            matrixPanel.revalidate();
            matrixPanel.repaint();   
            
        }
        zombiesInLevel.remove(zombie);
        
    }
   
   public void deleteGunFromMatrix(Gun g, Zombie z, ArrayList<Gun> guns) {
        int x = g.getX();
        int y = g.getY();
        z.setIsGunNotInRange(true);

        if (gunMatrix[x][y] != null) {
            gunMatrix[x][y] = null;
            casillasConImagen[x][y] = false;

            JLabel gunLabel = zombieLabels[x][y];
            if (gunLabel != null) {
                // Remove the gun's JLabel from the matrixPanel
                matrixPanel.remove(gunLabel);
                matrixPanel.revalidate();
                matrixPanel.repaint(); 
            }
        }
        this.guns.remove(g);
    }

   
//MATRIX LABEL MANAGERS ENDS  
    
    public void showEntityInformation(int x, int y) {
        if (casillasConImagen[x][y]) {
            // La casilla está ocupada, obtén la entidad (zombie o arma) en esa posición
            Zombie zombie = zombieMatrix[x][y];
            Gun gun = gunMatrix[x][y];

            if (zombie != null) {
                // Se hizo clic en un zombie
                infoTextArea.setText("-------------------------\n");
                infoTextArea.append("Información del Zombie:\n");
                infoTextArea.append("Nombre: " + zombie.getName() + "\n");
                infoTextArea.append("Tipo: " + zombie.getType() + "\n");
                infoTextArea.append("Salud: " + zombie.getHealth() + "\n");
                infoTextArea.append("GPS: " + zombie.getHitsPerSecond() + "\n");
                infoTextArea.append("Espacios: " + zombie.getFieldsInMatrix() + "\n");
                infoTextArea.append("-------------------------");
            } else if (gun != null) {
                infoTextArea.setText("-------------------------\n");
                infoTextArea.append("Información del Arma:\n");
                infoTextArea.append("Nombre: " + gun.getName() + "\n");
                infoTextArea.append("Tipo: " + gun.getType() + "\n");
                infoTextArea.append("Salud: " + gun.getHealth() + "\n");
                infoTextArea.append("GPS: " + gun.getHitsPerSecond() + "\n");
                infoTextArea.append("Espacios: " + gun.getFieldsInMatrix() + "\n");
                infoTextArea.append("-------------------------");
            }
        } else {
            // Si la casilla está vacía, borra la información en el área de texto
            infoTextArea.setText("");
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