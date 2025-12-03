/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {

    private final List<String> rowErrors = new ArrayList<>();
    private final List<String> colErrors = new ArrayList<>();
    private final List<String> boxErrors = new ArrayList<>();

    public void addRowError(String e) {
        rowErrors.add(e);
    }

    public void addColError(String e) {
        colErrors.add(e);
    }

    public void addBoxError(String e) {
        boxErrors.add(e);
    }

    public boolean isValid() {
        return rowErrors.isEmpty() && colErrors.isEmpty() && boxErrors.isEmpty();
    }

    public List<String> getRowErrors() {
        return rowErrors;
    }

    public List<String> getColErrors() {
        return colErrors;
    }

    public List<String> getBoxErrors() {
        return boxErrors;
    }

    public static ValidationResult fromDup(int[][] dup, int[][] table) {
        ValidationResult vr = new ValidationResult();
        final int ROW = 1, COL = 2, BOX = 4;

        // Rows: group duplicates by row and by value -> list of column indices
        for (int r = 0; r < 9; r++) {
            // map value -> list of column positions (1-based)
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int c = 0; c < 9; c++) {
                if ((dup[r][c] & ROW) != 0) {
                    int val = table[r][c];
                    map.computeIfAbsent(val, k -> new ArrayList<>()).add(c + 1);
                }
            }
            for (Map.Entry<Integer, List<Integer>> e : map.entrySet()) {
                vr.addRowError(String.format("ROW %d, #%d, %s", r + 1, e.getKey(), e.getValue().toString()));
            }
        }

        // Columns
        for (int c = 0; c < 9; c++) {
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int r = 0; r < 9; r++) {
                if ((dup[r][c] & COL) != 0) {
                    int val = table[r][c];
                    map.computeIfAbsent(val, k -> new ArrayList<>()).add(r + 1);
                }
            }
            for (Map.Entry<Integer, List<Integer>> e : map.entrySet()) {
                vr.addColError(String.format("COL %d, #%d, %s", c + 1, e.getKey(), e.getValue().toString()));
            }
        }

        // Boxes: index boxes 0..8 (left->right, top->down)
        final int[] boxStartR = {0, 0, 0, 3, 3, 3, 6, 6, 6};
        final int[] boxStartC = {0, 3, 6, 0, 3, 6, 0, 3, 6};
        for (int bi = 0; bi < 9; bi++) {
            int r0 = boxStartR[bi];
            int c0 = boxStartC[bi];
            Map<Integer, List<String>> map = new HashMap<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int r = r0 + i;
                    int c = c0 + j;
                    if ((dup[r][c] & BOX) != 0) {
                        int val = table[r][c];
                        String pos = String.format("(%d,%d)", r + 1, c + 1);
                        map.computeIfAbsent(val, k -> new ArrayList<>()).add(pos);
                    }
                }
            }
            for (Map.Entry<Integer, List<String>> e : map.entrySet()) {
                vr.addBoxError(String.format("BOX %d, #%d, %s", bi + 1, e.getKey(), e.getValue().toString()));
            }
        }

        return vr;
    }
}
