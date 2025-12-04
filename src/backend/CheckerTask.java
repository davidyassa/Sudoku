/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.HashSet;
import java.util.Set;

/**
 * CheckerTask: checks one region (row/column/box) or all regions of a type. -
 * If index >= 0: check that single region. - If index == -1: check all regions
 * of that type.
 *
 * Error messages are pushed into ValidationReport (thread-safe). Message format
 * used: "ROW 1, #value, [positions/list]" similar to earlier format.
 */
public class CheckerTask implements Runnable {

    private final int[][] board;
    private final RegionType type;
    private final int index;
    private final ValidationReport report;

    public CheckerTask(int[][] board, RegionType type, int index, ValidationReport report) {
        this.board = board;
        this.type = type;
        this.index = index;
        this.report = report;
    }

    @Override
    public void run() {
        switch (type) {
            case ROW ->
                checkRow(index);
            case COLUMN ->
                checkColumn(index);
            case BOX ->
                checkBox(index);
        }

    }

    private void checkRow(int r) {
        boolean[] seen = new boolean[10];
        Set<Integer> dupValues = new HashSet<>();
        Set<Integer> emptyValues = new HashSet<>();
        // detect duplicates values
        for (int c = 0; c < 9; c++) {
            int v = board[r][c];
            if (v < 0 || v > 9) {
                continue;
            }
            if (v == 0) {
                emptyValues.add(v);
            }
            if (seen[v]) {
                dupValues.add(v);
            }
            seen[v] = true;
        }
        if (!dupValues.isEmpty()) {
            // For each duplicated value collect positions (1-based columns)
            for (int val : dupValues) {
                StringBuilder positions = new StringBuilder("[");
                boolean first = true;
                for (int c = 0; c < 9; c++) {
                    if (board[r][c] == val) {
                        if (!first) {
                            positions.append(", ");
                        }
                        positions.append(c + 1);
                        first = false;
                    }
                }
                positions.append("]");
                String msg = String.format("ROW %d, #%d, %s", r + 1, val, positions.toString());
                report.addError(msg);
            }
        }
        if (!emptyValues.isEmpty()) {
            for (int val : emptyValues) {
                StringBuilder positions = new StringBuilder("[");
                boolean first = true;
                for (int c = 0; c < 9; c++) {
                    if (board[r][c] == 0) {
                        if (!first) {
                            positions.append(", ");
                        }
                        positions.append(r + 1);
                        first = false;
                    }
                }
                positions.append("]");
                String msg = String.format("ROW %d, %s", r + 1, val, positions.toString());
                report.addNull(msg);
            }
        }
    }

    private void checkColumn(int c) {
        boolean[] seen = new boolean[10];
        Set<Integer> dupValues = new HashSet<>();
        for (int r = 0; r < 9; r++) {
            int v = board[r][c];
            if (v < 1 || v > 9) {
                continue;
            }
            if (seen[v]) {
                dupValues.add(v);
            }
            seen[v] = true;
        }
        if (!dupValues.isEmpty()) {
            for (int val : dupValues) {
                StringBuilder positions = new StringBuilder("[");
                boolean first = true;
                for (int r = 0; r < 9; r++) {
                    if (board[r][c] == 0) {
                        if (!first) {
                            positions.append(", ");
                        }
                        positions.append(r + 1);
                        first = false;
                    }
                }
                positions.append("]");
                String msg = String.format("COLUMN %d, #%d, %s", c + 1, val, positions.toString());
                report.addError(msg);
            }
        }
    }

    private void checkBox(int bi) {
        int sr = (bi / 3) * 3;
        int sc = (bi % 3) * 3;
        boolean[] seen = new boolean[10];
        Set<Integer> dupValues = new HashSet<>();
        for (int r = sr; r < sr + 3; r++) {
            for (int c = sc; c < sc + 3; c++) {
                int v = board[r][c];
                if (v < 1 || v > 9) {
                    continue;
                }
                if (seen[v]) {
                    dupValues.add(v);
                }
                seen[v] = true;
            }
        }
        if (!dupValues.isEmpty()) {
            for (int val : dupValues) {
                StringBuilder positions = new StringBuilder("[");
                boolean first = true;
                for (int r = sr; r < sr + 3; r++) {
                    for (int c = sc; c < sc + 3; c++) {
                        if (board[r][c] == val) {
                            if (!first) {
                                positions.append(", ");
                            }
                            // report local positions as global coordinates (row,col)
                            positions.append(String.format("(%d,%d)", r + 1, c + 1));
                            first = false;
                        }
                    }
                }
                positions.append("]");
                String msg = String.format("BOX %d, #%d, %s", bi + 1, val, positions.toString());
                report.addError(msg);
            }
        }
    }
}
