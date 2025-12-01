/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author DELL 7550
 */
package backend;

public class ModeZeroSolve implements SudokuValidator {

    private final int[][] board;

    public ModeZeroSolve(int[][] board) {
        this.board = board;
    }

    @Override
    public ValidationResult validate() {
        ValidationResult vr = new ValidationResult();

        // ROWS 
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[10];
            boolean[] dup = new boolean[10];

            for (int j = 0; j < 9; j++) {
                int v = board[i][j];
                if (v < 1 || v > 9) {
                    continue;
                }

                if (seen[v]) {
                    dup[v] = true;
                }
                seen[v] = true;
            }

            for (int v = 1; v <= 9; v++) {
                if (dup[v]) {
                    // collect positions
                    StringBuilder pos = new StringBuilder("[");
                    boolean first = true;

                    for (int j = 0; j < 9; j++) {
                        if (board[i][j] == v) {
                            if (!first) {
                                pos.append(", ");
                            }
                            pos.append(j + 1);
                            first = false;
                        }
                    }

                    pos.append("]");
                    vr.addRowError("ROW " + (i + 1) + ", #" + v + ", " + pos);
                }
            }
        }

        // COLUMNS
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[10];
            boolean[] dup = new boolean[10];

            for (int j = 0; j < 9; j++) {
                int v = board[j][i];
                if (v < 1 || v > 9) {
                    continue;
                }

                if (seen[v]) {
                    dup[v] = true;
                }
                seen[v] = true;
            }

            for (int v = 1; v <= 9; v++) {
                if (dup[v]) {
                    StringBuilder pos = new StringBuilder("[");
                    boolean first = true;

                    for (int j = 0; j < 9; j++) {
                        if (board[j][i] == v) {
                            if (!first) {
                                pos.append(", ");
                            }
                            pos.append(j + 1);
                            first = false;
                        }
                    }

                    pos.append("]");
                    vr.addColError("COLUMN " + (i + 1) + ", #" + v + ", " + pos);
                }
            }
        }

        // -------------------- BOXES (3x3) --------------------
        // example box 0 rows = {{0,0,0},{1,1,1},{2,2,2}}, cols = {{0,1,2},{0,1,2},{0,1,2}}
        // example box 2 rows = {{0,0,0},{1,1,1},{2,2,2}}, cols = {{6,7,8},{6,7,8},{6,7,8}}
        // example box 3 rows = {{3,3,3},{4,4,4},{5,5,5}}, cols = {{0,1,2},{0,1,2},{0,1,2}}
        // example box 4 rows = {{3,3,3},{4,4,4},{5,5,5}}, cols = {{3,4,5},{3,4,5},{3,4,5}}
        for (int b = 0; b < 9; b++) {

            int r0 = (b / 3) * 3; // (i, i+1, i+2) → same box row group
            int c0 = (b % 3) * 3; // (j, j+1, j+2) → same box col group

            boolean[] seen = new boolean[10];
            boolean[] dup = new boolean[10];

            // scan the 3x3 box
            for (int r = r0; r < r0 + 3; r++) {
                for (int c = c0; c < c0 + 3; c++) {
                    int v = board[r][c];
                    if (v < 1 || v > 9) {
                        continue;
                    }

                    if (seen[v]) {
                        dup[v] = true;
                    }
                    seen[v] = true;
                }
            }

            // clean final message
            for (int v = 1; v <= 9; v++) {
                if (dup[v]) {
                    StringBuilder pos = new StringBuilder("[");
                    boolean first = true;

                    for (int r = r0; r < r0 + 3; r++) {
                        for (int c = c0; c < c0 + 3; c++) {
                            if (board[r][c] == v) {
                                if (!first) {
                                    pos.append(", ");
                                }
                                pos.append("(" + (r + 1) + "," + (c + 1) + ")");
                                first = false;
                            }
                        }
                    }

                    pos.append("]");
                    vr.addBoxError("BOX " + (b + 1) + ", #" + v + ", " + pos);
                }
            }
        }

        return vr;
    }

}
