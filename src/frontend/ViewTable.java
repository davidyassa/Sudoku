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

public class ViewTable extends JPanel {

    private FrameManager frame;
    private JTable table;
    private String currentFilePath = null;
    private GameDriver gd;
    private boolean isProgrammaticChange = false;

    public ViewTable(FrameManager frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        JButton openButton = new JButton("Open CSV / Start Game");
        top.add(openButton);
        add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        JButton undoButton = new JButton("Undo");
        JButton saveButton = new JButton("Save & Exit");
        JButton checkButton = new JButton("Check & Submit");
        JButton backButton = new JButton("Back");

        bottom.add(undoButton);
        bottom.add(saveButton);
        bottom.add(checkButton);
        bottom.add(backButton);
        add(bottom, BorderLayout.SOUTH);

        table = new JTable(9, 9);
        table.setTableHeader(null);
        table.setRowHeight(50);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        
        table.setModel(new DefaultTableModel(9, 9));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int col) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, col);

                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setFont(new Font("SansSerif", Font.BOLD, 22));

                int t = (row == 0) ? 3 : (row % 3 == 0 ? 2 : 1);
                int l = (col == 0) ? 3 : (col % 3 == 0 ? 2 : 1);
                int b = (row == 8) ? 3 : ((row + 1) % 3 == 0 ? 2 : 1);
                int r = (col == 8) ? 3 : ((col + 1) % 3 == 0 ? 2 : 1);

                cell.setBorder(BorderFactory.createMatteBorder(t, l, b, r, Color.BLACK));
                
                if (value == null || value.toString().isEmpty() || value.toString().equals("0")) {
                     cell.setBackground(new Color(240, 240, 240));
                } else {
                     cell.setBackground(Color.WHITE);
                }
                return cell;
            }
        });

        openButton.addActionListener(e -> chooseAndLoadCSV());
        backButton.addActionListener(e -> frame.previousPanel());

        undoButton.addActionListener(e -> {
            if (gd != null) {
                gd.undo(); 
                refreshTableFromBoard(); 
            }
        });

        saveButton.addActionListener(e -> {
            if (gd != null) {
                gd.saveUnfinishedGame();
                JOptionPane.showMessageDialog(this, "Game Saved in 'INCOMPLETE' folder!");
                frame.showMainMenu();
            }
        });

        checkButton.addActionListener(e -> {
            if (gd != null) {
                Validity v = gd.validateBoard();
                if (v == Validity.VALID) {
                    boolean deleted = gd.checkWinAndDelete();
                    String msg = deleted ? "Valid! File Deleted." : "Valid! (File not local)";
                    JOptionPane.showMessageDialog(this, msg);
                    frame.showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(this, "Not Valid:\n" + v, "Result", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        table.getModel().addTableModelListener(e -> {
            if (isProgrammaticChange || gd == null) return;
            
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                try {
                    Object val = table.getValueAt(row, col);
                    int num = 0;
                    if (val != null && !val.toString().trim().isEmpty()) {
                        num = Integer.parseInt(val.toString().trim());
                    }
                    gd.updateCell(row, col, num);
                } catch (NumberFormatException ex) {
                    SwingUtilities.invokeLater(() -> {
                        isProgrammaticChange = true;
                        table.setValueAt("", row, col);
                        isProgrammaticChange = false;
                    });
                }
            }
        });
    }

    private void chooseAndLoadCSV() {
        JFileChooser fc = new JFileChooser("./SudokuStorage"); 
        if (!new File("./SudokuStorage").exists()) {
             fc = new JFileChooser(".");
        }
        
        fc.setDialogTitle("Choose Sudoku CSV");

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            currentFilePath = f.getAbsolutePath();
            gd = new GameDriver(currentFilePath);
            refreshTableFromBoard();
        }
    }

    private void refreshTableFromBoard() {
        if (gd == null) return;
        isProgrammaticChange = true;
        
        int[][] board = gd.getBoard();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                model.setValueAt(board[r][c] == 0 ? "" : board[r][c], r, c);
            }
        }
        isProgrammaticChange = false;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }
}