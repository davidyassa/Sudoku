/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import backend.*;
import main.FrameManager;
import javax.swing.*;

public class ModeTwentySeven extends JPanel {

    private FrameManager frame;
    private ViewTable view;

    public ModeTwentySeven(FrameManager frame, ViewTable viewTable) {
        this.frame = frame;
        this.view = viewTable;

        setLayout(null);

        JButton back = new JButton("Return");
        back.setBounds(20, 20, 100, 30);
        add(back);

        JButton run = new JButton("RUN Mode 27");
        run.setBounds(300, 70, 200, 40);
        add(run);

        JTextArea out = new JTextArea();
        out.setEditable(false);
        JScrollPane scroll = new JScrollPane(out);
        scroll.setBounds(50, 140, 700, 380);
        add(scroll);

        back.addActionListener(e -> frame.previousPanel());
        run.addActionListener(e -> runMode(out));
    }

    private void runMode(JTextArea out) {
        if (view.getCurrentFilePath() == null) {
            JOptionPane.showMessageDialog(this, "Open a CSV first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[][] board = csvManager.getInstance().getTable();
        SudokuValidator v = new ModeTwentySevenSolve(board);

        ValidationResult r = v.validate();

        out.setText("MODE 27 RESULT:\n");
        if (r.isValid()) {
            out.append("\nVALID SUDOKU\n");
        } else {
            out.append("\nINVALID\n\n");
            r.getRowErrors().forEach(e -> out.append(e + "\n"));
            r.getColErrors().forEach(e -> out.append(e + "\n"));
            r.getBoxErrors().forEach(e -> out.append(e + "\n"));
        }
    }
}
