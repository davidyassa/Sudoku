/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author DELL 7550
 */
package backend;

public class SequentialValidation {

    private final int[][] board;
    private CheckerTask ct;

    public SequentialValidation(int[][] board) {
        this.board = board;
    }

    public ValidationResult validate() {

        ValidationReport report = new ValidationReport();
        for (int i = 0; i < 9; i++) {
            new CheckerTask(board, RegionType.ROW, i, report).run();
            new CheckerTask(board, RegionType.COLUMN, i, report).run();
            new CheckerTask(board, RegionType.BOX, i, report).run();
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
        for (String err : report.getNulls()) {
            result.addNull(err);

        }

        return result;
    }

}
