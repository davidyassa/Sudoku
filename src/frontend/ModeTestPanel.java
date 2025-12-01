/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import backend.SudokuTester;
import backend.csvManager;
import main.FrameManager;

import javax.swing.*;
import java.awt.*;

public class ModeTestPanel extends JPanel {

    private FrameManager frame;
    private ViewTable viewTable;

    private JTextArea output;

    public ModeTestPanel(FrameManager frame, ViewTable viewTable) {
        this.frame = frame;
        this.viewTable = viewTable;

        setLayout(null);

        JButton back = new JButton("Return");
        back.setBounds(10, 10, 100, 30);
        add(back);

        JButton exit = new JButton("Exit");
        exit.setBounds(680, 10, 80, 30);
        add(exit);

        JButton run = new JButton("Run Performance Test");
        run.setBounds(250, 50, 300, 50);
        run.setFont(new Font("Arial", Font.BOLD, 16));
        add(run);

        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBounds(50, 120, 700, 400);
        add(scroll);

        back.addActionListener(e -> frame.previousPanel());
        exit.addActionListener(e -> System.exit(0));

        run.addActionListener(e -> runPerformance());
    }

    private void runPerformance() {
        String file = viewTable.getCurrentFilePath();

        if (file == null) {
            JOptionPane.showMessageDialog(this,
                    "No CSV file selected!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // load board
        csvManager cm = csvManager.getInstance(file);
        int[][] board = cm.getTable();

        output.setText("Running Performance Test...\n\n");

        // use SudokuTester directly
        SudokuTester tester = new SudokuTester();

        // Capture printed output
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream oldOut = System.out;

        System.setOut(ps);
        tester.runOnce(file, 5);       // 5 test runs
        System.setOut(oldOut);

        output.append(baos.toString());
    }
}
