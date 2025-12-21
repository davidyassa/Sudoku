/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import backend.Validity;
import controller.GameDriver;
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
        String report = getReportOrValid(new GameDriver());
        out.setText(report);
    }

    /**
     *
     * @param gd
     * @return String -> full report
     */
    public static String getReportOrValid(GameDriver gd) {
        Validity v = gd.validateBoard();
        StringBuilder sb = new StringBuilder("TEST RESULT:\n");
        sb.append(v.toString()).append(" SUDOKU\n");
        switch (v) {
            // no case VALID since "VALID SUDOKU" is all we need
            case INCOMPLETE -> {
                gd.getResult().getNulls().forEach(e -> sb.append(e).append("\n"));
            }
            case INVALID -> {
                gd.getResult().getRowErrors().forEach(e -> sb.append(e).append("\n"));
                gd.getResult().getColErrors().forEach(e -> sb.append(e).append("\n"));
                gd.getResult().getBoxErrors().forEach(e -> sb.append(e).append("\n"));
            }
        }
        return sb.toString();
    }
}
