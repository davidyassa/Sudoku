package backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class csvManager {

    private static csvManager instance;
    private final String filename;
    private final int[][] table;

    private static final String STORAGE_ROOT = "SudokuStorage";

    private csvManager(String file) {
        this.filename = file;
        this.table = loadTableInternal();
        createDirectories();
    }

    public static csvManager getInstance(String file) {
        if (instance == null || (file != null && !instance.filename.equals(file))) {
            if (file == null) {
                throw new IllegalStateException("First call must include filename");
            }
            instance = new csvManager(file);
        }
        return instance;
    }

    public static csvManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("csvManager not initialized");
        }
        return instance;
    }

    public int[][] getTable() {
        return table;
    }

    private void createDirectories() {
        new File(STORAGE_ROOT + "/1-EASY").mkdirs();
        new File(STORAGE_ROOT + "/2-MEDIUM").mkdirs();
        new File(STORAGE_ROOT + "/3-HARD").mkdirs();
        new File(STORAGE_ROOT + "/4-INCOMPLETE").mkdirs();
    }

    public void saveBoard(int[][] board, String subFolder, String name) {
        String path = STORAGE_ROOT + "/" + subFolder + "/" + name + ".csv";
        try (FileWriter fw = new FileWriter(path)) {
            for (int r = 0; r < 9; r++) {
                StringBuilder line = new StringBuilder();
                for (int c = 0; c < 9; c++) {
                    line.append(board[r][c]);
                    if (c < 8) {
                        line.append(",");
                    }
                }
                fw.write(line.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public boolean deleteCurrentFile() {
        if (filename == null) {
            return false;
        }
        File f = new File(filename);
        return f.exists() && f.delete();
    }

    private int[][] loadTableInternal() {
        int[][] arr = new int[9][9];
        try (FileReader fr = new FileReader(filename); Scanner sc = new Scanner(fr)) {
            int row = 0;
            while (sc.hasNextLine() && row < 9) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    row++;
                    continue;
                }
                String[] parts = line.split(",");
                for (int col = 0; col < 9; col++) {
                    if (col >= parts.length || parts[col].trim().isEmpty()) {
                        arr[row][col] = 0;
                    } else {
                        try {
                            arr[row][col] = Integer.parseInt(parts[col].trim());
                        } catch (NumberFormatException e) {
                            arr[row][col] = 0;
                        }
                    }
                }
                row++;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return arr;
    }

    public static int[][] clone(int[][] board) {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = board[r].clone();
        }
        return copy;
    }
}
