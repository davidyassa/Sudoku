/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import main.FrameManager;
import controller.GameDriver;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author DELL 7550
 */
public class PlayPanel extends JPanel {

    private FrameManager frame;
    private String solutionPath;

    public PlayPanel(FrameManager frame) {
        this.frame = frame;
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel("Generate Sudoku Games", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title);

        JButton choose = new JButton("Choose Valid Solution CSV");
        JLabel chosen = new JLabel("No file selected", SwingConstants.CENTER);
        JButton generate = new JButton("Generate Games");
        JButton back = new JButton("Back");

        add(choose);
        add(chosen);
        add(generate);
        add(back);

        choose.addActionListener(e -> {
            JFileChooser fc = new JFileChooser("./SudokuStorage");
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                solutionPath = fc.getSelectedFile().getAbsolutePath();
                chosen.setText(fc.getSelectedFile().getName());
            }
        });

        generate.addActionListener(e -> {
            if (solutionPath == null) {
                JOptionPane.showMessageDialog(this, "Choose a valid solution first");
                return;
            }
            try {
                GameDriver gd = new GameDriver(solutionPath);
                gd.driveGames();
                JOptionPane.showMessageDialog(this, "Games generated successfully!");
                frame.switchPanel(new CatalogueScreen(frame));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Generation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        back.addActionListener(e -> frame.previousPanel());
    }
}
