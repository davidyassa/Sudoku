/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author OMEN
 */
package frontend;

import main.FrameManager;
import controller.GameDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class CatalogueScreen extends JPanel {

    private FrameManager frame;
    private JComboBox<String> unfinishedBox;

    public CatalogueScreen(FrameManager frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));

        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        add(center, BorderLayout.CENTER);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        add(top, BorderLayout.NORTH);

        center.add(createColumn("Easy", "./SudokuStorage/1-EASY"));
        center.add(createColumn("Medium", "./SudokuStorage/2-MEDIUM"));
        center.add(createColumn("Hard", "./SudokuStorage/3-HARD"));

        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        add(bottom, BorderLayout.SOUTH);

        JPanel resumePanel = new JPanel();
        resumePanel.add(new JLabel("Resume unfinished game:"));
        unfinishedBox = new JComboBox<>(loadFiles("./SudokuStorage/4-INCOMPLETE"));
        resumePanel.add(unfinishedBox);

        JButton resume = new JButton("Resume");
        JButton generate = new JButton("Generate Games");
        JButton back = new JButton("Back");

        resumePanel.add(resume);
        resumePanel.add(generate);
        resumePanel.add(back);
        bottom.add(resumePanel, BorderLayout.CENTER);

        generate.addActionListener(e -> frame.switchPanel(new PlayPanel(frame)));
        back.addActionListener(e -> frame.previousPanel());

        resume.addActionListener(e -> {
            String file = (String) unfinishedBox.getSelectedItem();
            if (file == null || file.equals("No games found")) {
                return;
            }
            frame.setGameDriver(new GameDriver("./SudokuStorage/4-INCOMPLETE/" + file));
            frame.switchPanel(new ViewTable(frame));
        });

    }

    private JPanel createColumn(String title, String dir) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);

        String[] files = loadFiles(dir);
        JList<String> list = new JList<>(files);
        if (files.length == 1 && files[0].equals("No games found")) {
            list.setForeground(Color.GRAY);
        }
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton play = new JButton("Play");
        play.putClientProperty("list", list);
        play.putClientProperty("dir", dir);
        panel.add(play, BorderLayout.SOUTH);

        play.addActionListener(playListener);

        return panel;
    }

    private String[] loadFiles(String path) {
        File dir = new File(path);
        if (!dir.exists() || dir.listFiles() == null || dir.listFiles().length == 0) {
            return new String[]{"No games found"};
        }
        return dir.list((d, name) -> name.endsWith(".csv"));
    }

    private final ActionListener playListener = e -> {
        JButton src = (JButton) e.getSource();
        JList<String> list = (JList<String>) src.getClientProperty("list");
        String dir = (String) src.getClientProperty("dir");

        String file = list.getSelectedValue();
        if (file == null || file.equals("No games found")) {
            return;
        }

        frame.setGameDriver(new GameDriver(dir + "/" + file));
        frame.switchPanel(new ViewTable(frame));
    };
}
