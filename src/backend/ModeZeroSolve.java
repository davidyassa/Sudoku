/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author DELL 7550
 */
public class ModeZeroSolve {

    private csvManager csvm;
    private int[][] table;

    public int[][] Solve(String filename) {
        csvm = csvManager.getInstance(filename);
        table = csvm.getTable();
        final int ROW_DUP = 1;
        final int COL_DUP = 2;
        final int BOX_DUP = 3;
        int[][] dup = new int[9][9];

        //ROWS and COLUMNS//
        for (int i = 0; i < 9; i++) {
            int[] freqR = new int[9];
            int[] freqC = new int[9];
            int[] firstOccR = new int[9];
            int[] firstOccC = new int[9];

            for (int j = 0; j < 9; j++) {
                int r = table[i][j];
                int c = table[j][i];

                // ROW check (r)
                if (r != 0) {
                    int fr = ++freqR[r - 1]; // mapping 1–9 to 0–8

                    if (fr == 1) {
                        firstOccR[r - 1] = j;
                    }
                    if (fr > 1) {
                        dup[i][firstOccR[r - 1]] = ROW_DUP;
                        dup[i][j] = ROW_DUP;
                    }
                }

                // COLUMN check (c)
                if (c != 0) {
                    int fc = ++freqC[c - 1];

                    if (fc == 1) {
                        firstOccC[c - 1] = j;
                    }
                    if (fc > 1) {
                        dup[firstOccC[c - 1]][i] = COL_DUP;
                        dup[j][i] = COL_DUP;
                    }
                }
            }
        }

        //example box 0 rows = {{0,0,0},{1,1,1},{2,2,2}}, cols = {{0,1,2},{0,1,2},{0,1,2}}
        //example box 2 rows = {{0,0,0},{1,1,1},{2,2,2}}, cols = {{6,7,8},{6,7,8},{6,7,8}}
        //example box 3 rows = {{3,3,3},{4,4,4},{5,5,5}}, cols = {{0,1,2},{0,1,2},{0,1,2}}
        //example box 4 rows = {{3,3,3},{4,4,4},{5,5,5}}, cols = {{3,4,5},{3,4,5},{3,4,5}}
        // BOXES// (3x3)
        for (int br = 0; br < 3; br++) {          // box row index {0,1,2}
            for (int bc = 0; bc < 3; bc++) {      // box col index {0,1,2}

                int[] freq = new int[9];
                int[] firstOcc = new int[9];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        int row = br * 3 + i;     // (i, i+1, i+2) → same box row
                        int col = bc * 3 + j;     // (j, j+1, j+2) → same box col

                        int b = table[row][col];  // value inside box 1 → 9

                        if (b != 0) {
                            int fb = ++freq[b - 1];

                            if (fb == 1) {
                                firstOcc[b - 1] = row * 9 + col;
                                // storing position as a single number
                            }

                            if (fb > 1) {
                                int pos = firstOcc[b - 1];
                                int r1 = pos / 9;     // decode row
                                int c1 = pos % 9;     // decode col

                                dup[r1][c1] = BOX_DUP;
                                dup[row][col] = BOX_DUP;
                            }
                        }
                    }
                }
            }
        }

        return dup;
    }
}
