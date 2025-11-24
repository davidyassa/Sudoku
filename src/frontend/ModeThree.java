/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import main.FrameManager;
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
        exitButton.setBounds(95, 10, 75, 30);
        add(exitButton);

        JButton backButton = new JButton("Return");
        backButton.setBounds(10, 10, 75, 30);
        add(backButton);

        exitButton.addActionListener(e -> System.exit(0));
        backButton.addActionListener(e -> frame.showMainMenu());

    }
}
