/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import frontend.ViewTable;
import java.util.Stack;
import javax.swing.*;
import frontend.CatalogueScreen;

/**
 *
 * @author DELL 7550
 */
public class FrameManager extends JFrame {

    private final Stack<JPanel> history = new Stack<>();
    private JPanel currentPanel;

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

        JButton StartButton = new JButton("Start");
        StartButton.setBounds(300, 200, 150, 40);
        panel.add(StartButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 300, 150, 40);
        panel.add(exitButton);

        exitButton.addActionListener(e -> System.exit(0));
        StartButton.addActionListener(e -> switchPanel(new ViewTable(this)));
        StartButton.addActionListener(e ->  showCatalogue());

        
        
        setContentPane(panel);
        revalidate();
        repaint();
    }

    public void switchPanel(JPanel newPanel) {
        if (currentPanel != null) {
            history.push(currentPanel);
        }
        currentPanel = newPanel;

        setContentPane(newPanel);
        revalidate();
        repaint();
    }

    public void previousPanel() {
        if (!history.isEmpty()) {
            JPanel prev = history.pop();
            currentPanel = prev;

            setContentPane(prev);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        FrameManager f = new FrameManager();
    }
    
    public void showCatalogue() {
        switchPanel(new CatalogueScreen(this)); 
    }

    public void startGame() {
        switchPanel(new ViewTable(this));
    }
    
    
}
