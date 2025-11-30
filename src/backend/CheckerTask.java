/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<Integer> data = extractData();

        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        
        for (int num : data) {
            if (num < 1 || num > 9) continue; 
            if (!seen.add(num)) { 
                duplicates.add(num);
            }
        }

        if (!duplicates.isEmpty()) {
            String errorMsg = String.format("%s %d, #%s, %s", 
                    type, (index + 1), duplicates.toString(), data.toString());
            report.addError(errorMsg);
        }
    }

    private List<Integer> extractData() {
        List<Integer> sectionData = new ArrayList<>();
        
        switch (type) {
            case ROW -> {
                for (int c = 0; c < 9; c++) sectionData.add(board[index][c]);
            }
            case COLUMN -> {
                for (int r = 0; r < 9; r++) sectionData.add(board[r][index]);
            }
            case BOX -> {
                int rStart = (index / 3) * 3;
                int cStart = (index % 3) * 3;
                for (int r = rStart; r < rStart + 3; r++) {
                    for (int c = cStart; c < cStart + 3; c++) {
                        sectionData.add(board[r][c]);
                    }
                }
            }
        }
        return sectionData;
    }
}