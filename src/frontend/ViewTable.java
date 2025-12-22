/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frontend;

import controller.GameDriver;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import main.FrameManager;
import backend.Validity;

public class ViewTable extends JPanel {

    private FrameManager frame;
    private JTable table;
    private String currentFilePath = null;
    private GameDriver gd = new GameDriver();

    public ViewTable(FrameManager frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        JButton openButton = new JButton("Open CSV");
        top.add(openButton);
        add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        JButton TestButton = new JButton("Test");
        JButton exitButton = new JButton("Exit");
        JButton verifyBtn = new JButton("Verify");
        JButton solveBtn = new JButton("Solve");
        JButton undoBtn = new JButton("Undo");

        bottom.add(TestButton);
        bottom.add(verifyBtn); 
        bottom.add(solveBtn);  
        bottom.add(undoBtn);   
        bottom.add(exitButton);
        add(bottom, BorderLayout.SOUTH);

        table = new JTable(9, 9);
        table.setTableHeader(null);
        table.setRowHeight(50);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, col);

                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setFont(new Font("SansSerif", Font.BOLD, 22));

                int top = (row == 0) ? 3 : (row % 3 == 0 ? 2 : 1);
                int left = (col == 0) ? 3 : (col % 3 == 0 ? 2 : 1);
                int bottom = (row == 8) ? 3 : ((row + 1) % 3 == 0 ? 2 : 1);
                int right = (col == 8) ? 3 : ((col + 1) % 3 == 0 ? 2 : 1);

                cell.setBorder(BorderFactory.createMatteBorder(
                        top, left, bottom, right, Color.BLACK));

                return cell;
            }
        });

        openButton.addActionListener(e -> chooseAndLoadCSV());
        TestButton.addActionListener(e -> frame.switchPanel(new Test(frame, this)));
        exitButton.addActionListener(e -> System.exit(0));
        
        verifyBtn.addActionListener(e -> {
            int[][] currentBoard = getBoardFromUI();
            Validity status = gd.verifyCurrentBoard(currentBoard);
            JOptionPane.showMessageDialog(this, "Validation Result: " + status);
        });
        
        solveBtn.addActionListener(e -> {
            int[][] currentBoard = getBoardFromUI();
            if (gd.canSolve(currentBoard)) {
                JOptionPane.showMessageDialog(this, "Solving...");
            } else {
                JOptionPane.showMessageDialog(this, "Solve only allowed if 5 cells are empty!");
            }
        });
        
        undoBtn.addActionListener(e -> {
            int[][] prev = gd.undo();
            if (prev != null) {
                updateTableUI(prev);
            }
        });
        
    }
    
    private int[][] getBoardFromUI() {
        int[][] b = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Object val = table.getValueAt(r, c);
                b[r][c] = (val == null || val.toString().isEmpty()) ? 0 : Integer.parseInt(val.toString());
            }
        }
        return b;
    }

    private void updateTableUI(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                table.setValueAt(board[r][c] == 0 ? "" : board[r][c], r, c);
            }
        }
    }
    

    private void chooseAndLoadCSV() {
        JFileChooser fc = new JFileChooser("./TestCases");
        fc.setDialogTitle("Choose Sudoku CSV");

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            currentFilePath = f.getAbsolutePath();

            GameDriver gd = new GameDriver();
            int[][] board = gd.getBoard();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    model.setValueAt(board[r][c] == 0 ? "" : board[r][c], r, c);
                }
            }
        }
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }
}
