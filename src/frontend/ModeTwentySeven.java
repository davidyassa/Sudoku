/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import backend.ModeTwentySevenSolve;
import backend.ValidationReport;
import backend.csvManager;
import main.FrameManager;
import javax.swing.*;
import java.awt.Font;

public class ModeTwentySeven extends JPanel {

    private FrameManager frame;
    private ViewTable viewTable;
    private JTextArea resultArea; 
    public ModeTwentySeven(FrameManager frame, ViewTable viewTable) {
        this.frame = frame;
        this.viewTable = viewTable;
        setLayout(null);
        JButton backButton = new JButton("Return");
        backButton.setBounds(10, 10, 100, 30);
        add(backButton);

        JButton runButton = new JButton("RUN CHECK (27 Threads)");
        runButton.setBounds(250, 50, 300, 50);
        runButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(runButton);
        
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(680, 10, 80, 30);
        add(exitButton);
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(50, 120, 700, 400);
        add(scroll);
        exitButton.addActionListener(e -> System.exit(0));
        backButton.addActionListener(e -> frame.previousPanel());
        
        runButton.addActionListener(e -> runValidation());
    }

    private void runValidation() {
        String filePath = viewTable.getCurrentFilePath();
        
        if (filePath == null) {
            JOptionPane.showMessageDialog(this, "No CSV file selected! Go back and Open CSV.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        csvManager manager = csvManager.getInstance(filePath);
        int[][] board = manager.getTable();

        ValidationReport report = new ValidationReport();
        ModeTwentySevenSolve solver = new ModeTwentySevenSolve();

        resultArea.setText("Running 27-Thread Analysis...\n");
        
        solver.solve(board, report);

        if (report.isValid()) {
            resultArea.append("\n RESULT: VALID SUDOKU\n");
            JOptionPane.showMessageDialog(this, "Valid Sudoku!");
        } else {
            resultArea.append("\n RESULT: INVALID SUDOKU\nFound Errors:\n");
            for (String err : report.getErrors()) {
                resultArea.append("- " + err + "\n");
            }
        }
    }
}