/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author DELL 7550
 */
package backend;

public class SequentialValidation implements SudokuValidator {

    private final int[][] board;
    private CheckerTask ct;

    public SequentialValidation(int[][] board) {
        this.board = board;
    }

    @Override
    public ValidationResult validate() {

        ValidationReport report = new ValidationReport();
        for (int i = 0; i < 9; i++) {
            ct = new CheckerTask(board, RegionType.ROW, i, report);
            ct.run();
            ct = new CheckerTask(board, RegionType.COLUMN, i, report);
            ct.run();
            ct = new CheckerTask(board, RegionType.BOX, i, report);
            ct.run();
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
