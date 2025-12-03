package backend;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// Singleton
public class csvManager {

    private static csvManager instance;
    private final String filename;
    private final int[][] table;

    // PRIVATE constructor -> loads table immediately
    private csvManager(String file) {
        this.filename = file;
        this.table = loadTableInternal();
    }

    public static csvManager getInstance(String file) {
        // create new instance if needed
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
                        arr[row][col] = 0;  // empty cell
                    } else {
                        try {
                            arr[row][col] = Integer.parseInt(parts[col].trim());
                        } catch (NumberFormatException e) {
                            arr[row][col] = 0; // fallback safe default
                        }
                    }
                }
                row++;
            }
        } catch (IOException e) {
            System.out.println("csvManager ERROR: " + e.getMessage());
        }

        return arr;
    }
}
