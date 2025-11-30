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

        JButton openButton = new JButton("Open CSV File");
        add(openButton, BorderLayout.NORTH);

        JButton exitButton = new JButton("Exit");
        add(exitButton, BorderLayout.SOUTH);

        JButton mode0Button = new JButton("Mode 0");
        mode0Button.setBounds(200, 492, 100, 30);
        add(mode0Button);

        JButton mode3Button = new JButton("Mode 3");
        mode3Button.setBounds(350, 492, 100, 30);
        add(mode3Button);

        JButton mode27Button = new JButton("Mode 27");
        mode27Button.setBounds(500, 492, 100, 30);
        add(mode27Button);

        mode0Button.addActionListener(e -> frame.switchPanel(new ModeZero(frame, this)));
        mode3Button.addActionListener(e -> frame.switchPanel(new ModeThree(frame)));
        mode27Button.addActionListener(e -> frame.switchPanel(new ModeTwentySeven(frame, this)));
        exitButton.addActionListener(e -> System.exit(0));
        openButton.addActionListener(e -> chooseAndLoadCSV());

        table = new JTable(9, 9);
        table.setTableHeader(null);          // remove header
        table.setRowHeight(50);              // make squares look good
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);

        // PANEL that keeps table SQUARE
        JPanel centerPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                int size = Math.min(frame.getWidth(), frame.getHeight() - 100);
                return new Dimension(size, size);
            }
        };
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(table, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, col);

                // Center the numbers
                cell.setHorizontalAlignment(SwingConstants.CENTER);

                // Bigger font
                cell.setFont(new Font("SansSerif", Font.BOLD, 22));

                // --- CONSISTENT BORDER LOGIC ---
                int top = (row == 0) ? 3 : (row % 3 == 0 ? 2 : 1);
                int left = (col == 0) ? 3 : (col % 3 == 0 ? 2 : 1);
                int bottom = ((row == 8) ? 3 : ((row + 1) % 3 == 0 ? 2 : 1));
                int right = ((col == 8) ? 3 : ((col + 1) % 3 == 0 ? 2 : 1));

                cell.setBorder(BorderFactory.createMatteBorder(
                        top, left, bottom, right, Color.BLACK));

                return cell;
            }
        });

    }

    private void chooseAndLoadCSV() {
        JFileChooser fc = new JFileChooser(".");
        fc.setDialogTitle("Choose a 9x9 Sudoku CSV");

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            currentFilePath = f.getAbsolutePath();
            csvManager cm = csvManager.getInstance(f.getAbsolutePath());
            int[][] data = cm.getTable();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    model.setValueAt(data[i][j] == 0 ? "" : data[i][j], i, j);
                }
            }
        }
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

}
