/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.*;

public class ModeTwentySevenSolve implements SudokuValidator {

    private final int[][] board;

    public ModeTwentySevenSolve(int[][] board) {
        this.board = board;
    }

    @Override
    public ValidationResult validate() {

        ValidationReport report = new ValidationReport();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            threads.add(new Thread(new CheckerTask(board, RegionType.ROW, i, report)));
            threads.add(new Thread(new CheckerTask(board, RegionType.COLUMN, i, report)));
            threads.add(new Thread(new CheckerTask(board, RegionType.BOX, i, report)));
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (Exception e) {
            }
        }

        ValidationResult result = new ValidationResult();
        for (String err : report.getErrors()) {
            if (err.startsWith("ROW")) {
                result.addRowError(err);
            } else if (err.startsWith("COLUMN")) {
                result.addColError(err);
            } else {
                result.addBoxError(err);
            }
        }

        return result;
    }
}
