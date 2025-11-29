/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import backend.ModeZeroSolve;
import main.FrameManager;
import javax.swing.*;

/**
 *
 * @author DELL 7550
 */
public class ModeZero extends JPanel {

    private FrameManager frame;
    private ViewTable viewTable;
    private ModeZeroSolve solver = new ModeZeroSolve();

    public ModeZero(FrameManager frame, ViewTable viewTable) {
        this.frame = frame;
        this.viewTable = viewTable;

        JButton runBtn = new JButton("Run Mode 0");
        add(runBtn);

        runBtn.addActionListener(e -> runModeZero());
    }

    private void runModeZero() {
        String file = viewTable.getCurrentFilePath();

        if (file == null) {
            JOptionPane.showMessageDialog(this,
                    "No CSV file is open!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[][] dup = solver.Solve(file);

        // TODO: show results in UI
        JOptionPane.showMessageDialog(this, "ModeZero Execute Complete");
    }
}
