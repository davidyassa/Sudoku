/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import frontend.ModeThree;
import frontend.ModeTwentySeven;
import frontend.ModeZero;
import javax.swing.*;

/**
 *
 * @author DELL 7550
 */
public class FrameManager extends JFrame {

    public FrameManager() {
        this.setTitle("Sudoku 9x9");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        showMainMenu();
        this.setVisible(true);
    }

    public final void showMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton mode0 = new JButton("Mode 0");
        mode0.setBounds(100, 200, 150, 40);
        panel.add(mode0);

        JButton mode3 = new JButton("Mode 3");
        mode3.setBounds(300, 200, 150, 40);
        panel.add(mode3);

        JButton mode27 = new JButton("Mode 27");
        mode27.setBounds(500, 200, 150, 40);
        panel.add(mode27);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 310, 150, 40);
        panel.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        mode0.addActionListener(e -> switchPanel(new ModeZero(this)));
        mode3.addActionListener(e -> switchPanel(new ModeThree(this)));
        mode27.addActionListener(e -> switchPanel(new ModeTwentySeven(this)));
        exitButton.addActionListener(e -> System.exit(0));

        setContentPane(panel);
        revalidate();
        repaint();
    }

    public void switchPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new FrameManager();
    }
}
