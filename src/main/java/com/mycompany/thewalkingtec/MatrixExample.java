package com.mycompany.thewalkingtec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatrixExample extends JFrame {
    private JLayeredPane panelInterno = new JLayeredPane();
    private JLabel[][] matriz = new JLabel[25][25];
    private boolean[][] casillasConImagen = new boolean[25][25];
    private ImageIcon iconoSeleccionado = null; // Almacena el icono seleccionado
    private JLabel counterLabel;
    
    private int counter = 0;
    private int maxCounter = 20;

    public MatrixExample() {
        setTitle("The Walking TEC");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelContenedor = new JPanel(new BorderLayout());
        JPanel panelMarco = new JPanel();
        panelMarco.setBackground(Color.BLACK); // Puedes establecer el color que desees
        panelMarco.setPreferredSize(new Dimension(750, 750)); // Tamaño del marco

        // Agregar el panel de la matriz al panel del marco
        panelMarco.add(panelInterno);

        // Agregar el panel del marco al panel principal
        //panelContenedor.add(panelMarco, BorderLayout.CENTER);
        panelInterno.setLayout(null);

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                matriz[i][j] = new JLabel(new ImageIcon("src/main/resources/image/grass25x25.png"));
                matriz[i][j].setBounds(i * 25, j * 25, 25, 25); // Posición y tamaño del JLabel
                panelInterno.add(matriz[i][j], JLayeredPane.DEFAULT_LAYER);
                casillasConImagen[i][j] = false;

                matriz[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel) e.getSource();
                        for (int x = 0; x < 25; x++) {
                            for (int y = 0; y < 25; y++) {
                                if (matriz[x][y] == label) {                                 
                                    if (!casillasConImagen[x][y]) {
                                        // Si la casilla ya tiene una imagen, no se hace nada                                                                     
                                        if (iconoSeleccionado != null && counter < maxCounter) {
                                            // Verifica si hay un icono seleccionado y coloca ese icono en la casilla
                                            ImageIcon icono = new ImageIcon(iconoSeleccionado.getImage());
                                            JLabel imagenSuperpuesta = new JLabel(icono);
                                            imagenSuperpuesta.setBounds(matriz[x][y].getBounds());
                                            panelInterno.add(imagenSuperpuesta, JLayeredPane.PALETTE_LAYER);
                                            casillasConImagen[x][y] = true;
                                            counter++;
                                            updateCounter();
                                            System.out.println("Label clickeado en la posición: (" + x + ", " + y + ")");
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

        panelContenedor.add(panelInterno, BorderLayout.CENTER);

        // Crea tres botones con imágenes distintas
        JButton boton1 = new JButton(new ImageIcon("src/main/resources/image/redDot.png"));
        JButton boton2 = new JButton(new ImageIcon("src/main/resources/image/blueDot.png"));
        JButton boton3 = new JButton(new ImageIcon("src/main/resources/image/greenDot.png"));

        // Agrega ActionListeners a los botones para seleccionar la imagen
        boton1.addActionListener(e -> iconoSeleccionado = (ImageIcon) boton1.getIcon());
        boton2.addActionListener(e -> iconoSeleccionado = (ImageIcon) boton2.getIcon());
        boton3.addActionListener(e -> iconoSeleccionado = (ImageIcon) boton3.getIcon());
        //panelInterno.setBorder(BorderFactory.createLineBorder(Color.green, 2));

        // Crea un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(boton1);
        panelBotones.add(boton2);
        panelBotones.add(boton3);
        
        //Agregar contador
        counterLabel = new JLabel(counter + "/" + maxCounter);
        panelBotones.add(counterLabel);
        
        
        
        panelContenedor.add(panelBotones, BorderLayout.SOUTH);

        add(panelContenedor);
    }
    
    public void updateCounter(){
        counterLabel.setText(counter + "/" + maxCounter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MatrixExample example = new MatrixExample();
            example.setVisible(true);
        });
    }
}




