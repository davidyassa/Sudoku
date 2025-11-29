package backend;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class csvManager {

    private static csvManager instance;
    private static String filename;

    private int[][] table;

    private csvManager(String file) {
        filename = file;
        try {
            table = loadTable();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static csvManager getInstance(String file) {
        if (instance == null) {
            instance = new csvManager(file);
        }
        return instance;
    }

    public int[][] getTable() {
        return table;
    }

    public final int[][] loadTable() throws IOException {
        int[][] arr = new int[9][9];

        try (FileReader fr = new FileReader(filename); Scanner sc = new Scanner(fr)) {

            int row = 0;

            while (sc.hasNextLine() && row < 9) {
                String line = sc.nextLine().trim();
                String[] parts = line.split(",");

                for (int col = 0; col < 9; col++) {
                    if (col >= parts.length || parts[col].trim().isEmpty()) {
                        arr[row][col] = 0; //null
                    } else {
                        arr[row][col] = Integer.parseInt(parts[col].trim());
                    }
                }
                row++;
            }
        }
        return arr;
    }
}
