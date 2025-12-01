package backend;

public class ModeThreeSolve implements SudokuValidator {

    private final int[][] board;

    public ModeThreeSolve(int[][] board) {
        this.board = board;
    }

    @Override
    public ValidationResult solve() {

        ValidationReport report = new ValidationReport();

        Thread tRow = new Thread(new CheckerTask(board, RegionType.ROW,    -1, report));
        Thread tCol = new Thread(new CheckerTask(board, RegionType.COLUMN, -1, report));
        Thread tBox = new Thread(new CheckerTask(board, RegionType.BOX,    -1, report));

        tRow.start();
        tCol.start();
        tBox.start();

        try {
            tRow.join();
            tCol.join();
            tBox.join();
        } catch (InterruptedException e) {}

        // convert report → result
        ValidationResult result = new ValidationResult();
        for (String err : report.getErrors()) {
            if (err.startsWith("ROW")) result.addRowError(err);
            else if (err.startsWith("COLUMN")) result.addColError(err);
            else result.addBoxError(err);
        }

        return result;
    }
}
