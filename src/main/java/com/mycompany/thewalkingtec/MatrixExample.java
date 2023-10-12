package com.mycompany.thewalkingtec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatrixExample extends JFrame {
    private JLayeredPane panelInterno = new JLayeredPane();
    private JLabel[][] matriz = new JLabel[25][25];

    public MatrixExample() {
        setTitle("The Walking TEC");
        setSize(660, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelInterno.setLayout(null);

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                matriz[i][j] = new JLabel(new ImageIcon("src/main/resources/image/grass25x25.png"));
                matriz[i][j].setBounds(i * 25, j * 25, 25, 25); // Posición y tamaño del JLabel
                panelInterno.add(matriz[i][j], JLayeredPane.DEFAULT_LAYER);

                matriz[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel) e.getSource();
                        for (int x = 0; x < 25; x++) {
                            for (int y = 0; y < 25; y++) {
                                if (matriz[x][y] == label) {
                                    System.out.println("Label clickeado en la posición: (" + x + ", " + y + ")");
                                    // Coloca el símbolo "!" encima del JLabel en la posición (x, y)
                                    JLabel simbolo = new JLabel("!");
                                    simbolo.setBounds(matriz[x][y].getBounds());
                                    panelInterno.add(simbolo, JLayeredPane.PALETTE_LAYER);
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        }

        add(panelInterno);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MatrixExample example = new MatrixExample();
            example.setVisible(true);
        });
    }
}



