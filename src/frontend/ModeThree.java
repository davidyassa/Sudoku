/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import javax.swing.*;

/**
 *
 * @author DELL 7550
 */
public class ModeThree extends JPanel {

    public ModeThree(FrameManager frame) {
        super();
        setLayout(null);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 310, 150, 40);
        add(exitButton);
        
        
        
        
        
        
        
        
        
        
        
        
        
        exitButton.addActionListener(e -> System.exit(0));

    }
}
