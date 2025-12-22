/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author OMEN
 */
package frontend;

import controller.GameDriver;
import backend.Difficulty;
import main.FrameManager;
import javax.swing.*;
import java.awt.*;

public class CatalogueScreen extends JPanel {
    private FrameManager frame;
    private GameDriver gd;

    public CatalogueScreen(FrameManager frame) {
        this.frame = frame;
        this.gd = new GameDriver();
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("Sudoku Game Catalogue", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title);

        JComboBox<String> gameList = new JComboBox<>(gd.detectUnfinishedGames().toArray(new String[0]));
        add(new JPanel().add(new JLabel("Select Game:"))); 
        add(gameList);

        
        JButton startBtn = new JButton("Select Difficulty & Play");
        startBtn.addActionListener(e -> {
            Difficulty[] options = gd.detectAvailableDifficulties();
            Difficulty selected = (Difficulty) JOptionPane.showInputDialog(
                this, "Choose Difficulty:", "Difficulty Selection",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]
            );

            if (selected != null) {
                
                frame.switchPanel(new ViewTable(frame)); 
            }
        });
        add(startBtn);
    }
}