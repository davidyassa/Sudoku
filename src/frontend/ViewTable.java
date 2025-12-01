/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package frontend;

import backend.csvManager;
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

    public ViewTable(FrameManager frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // ======= TOP BAR =======
        JPanel top = new JPanel();
        JButton openButton = new JButton("Open CSV");
        top.add(openButton);
        add(top, BorderLayout.NORTH);

        // ======= BOTTOM BUTTONS =======
        JPanel bottom = new JPanel();
        JButton mode0Button = new JButton("Mode 0");
        JButton mode3Button = new JButton("Mode 3");
        JButton mode27Button = new JButton("Mode 27");
        JButton exitButton = new JButton("Exit");
        JButton testButton = new JButton("Performance Test");
        testButton.setBounds(620, 492, 150, 30);
        add(testButton);

        testButton.addActionListener(e -> frame.switchPanel(new ModeTestPanel(frame, this)));

        bottom.add(mode0Button);
        bottom.add(mode3Button);
        bottom.add(mode27Button);
        bottom.add(exitButton);
        add(bottom, BorderLayout.SOUTH);

        // ======= TABLE =======
        table = new JTable(9, 9);
        table.setTableHeader(null);
        table.setRowHeight(50);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ======= BORDER RENDERER =======
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

        // ======= ACTIONS =======
        openButton.addActionListener(e -> chooseAndLoadCSV());
        mode0Button.addActionListener(e -> frame.switchPanel(new ModeZero(frame, this)));
        mode3Button.addActionListener(e -> frame.switchPanel(new ModeThree(frame, this)));
        mode27Button.addActionListener(e -> frame.switchPanel(new ModeTwentySeven(frame, this)));
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void chooseAndLoadCSV() {
        JFileChooser fc = new JFileChooser(".");
        fc.setDialogTitle("Choose Sudoku CSV");

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            currentFilePath = f.getAbsolutePath();

            csvManager mgr = csvManager.getInstance(currentFilePath);
            int[][] data = mgr.getTable();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    model.setValueAt(data[r][c] == 0 ? "" : data[r][c], r, c);
                }
            }
        }
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }
}
