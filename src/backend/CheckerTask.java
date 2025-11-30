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
    private final String type; 
    private final int index;   
    private final ValidationReport report;

    public CheckerTask(int[][] board, String type, int index, ValidationReport report) {
        this.board = board;
        this.type = type;
        this.index = index;
        this.report = report;
    }

    @Override
    public void run() {
        List<Integer> data = new ArrayList<>();

      
        if (type.equals("ROW")) {
            for (int c = 0; c < 9; c++) data.add(board[index][c]);
        } else if (type.equals("COL")) {
            for (int r = 0; r < 9; r++) data.add(board[r][index]);
        } else if (type.equals("BOX")) {
            int rStart = (index / 3) * 3;
            int cStart = (index % 3) * 3;
            for (int r = rStart; r < rStart + 3; r++) {
                for (int c = cStart; c < cStart + 3; c++) {
                    data.add(board[r][c]);
                }
            }
        }

    
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        
        for (int num : data) {
            if (num < 1 || num > 9) continue;
            if (seen.contains(num)) {
                duplicates.add(num);
            } else {
                seen.add(num);
            }
        }


        if (!duplicates.isEmpty()) {
            
            String errorMsg = type + " " + (index + 1) + ", #" + duplicates.toString() + ", " + data.toString();
            report.addError(errorMsg);
        }
        
    }
}