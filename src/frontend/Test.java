/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import backend.*;
import main.FrameManager;
import javax.swing.*;

public class Test extends JPanel {

    private FrameManager frame;
    private ViewTable view;

    public Test(FrameManager frame, ViewTable viewTable) {
        this.frame = frame;
        this.view = viewTable;

        setLayout(null);

        JButton back = new JButton("Return");
        back.setBounds(20, 20, 100, 30);
        add(back);

        JButton run = new JButton("RUN TEST");
        run.setBounds(300, 70, 200, 40);
        add(run);

        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBounds(50, 140, 700, 380);
        add(scroll);

        back.addActionListener(e -> frame.previousPanel());
        run.addActionListener(e -> run(output));
    }

    private void run(JTextArea out) {
        String file = view.getCurrentFilePath();
        if (file == null) {
            JOptionPane.showMessageDialog(this, "Open a CSV first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[][] board = csvManager.getInstance().getTable();
        SequentialValidation v = new SequentialValidation(board);
        ValidationResult r = v.validate();

        out.setText("TEST RESULT:\n");
        switch (r.validate()) {
            case 0 ->
                out.append("\nVALID SUDOKU\n");
            case 1 -> {
                out.append("\nINCOMPLETE\n\n");
                r.getNulls().forEach(e -> out.append(e + "\n"));
            }
            case -1 -> {
                out.append("\nINVALID\n\n");
                r.getRowErrors().forEach(e -> out.append(e + "\n"));
                r.getColErrors().forEach(e -> out.append(e + "\n"));
                r.getBoxErrors().forEach(e -> out.append(e + "\n"));
            }
        }
    }
}
