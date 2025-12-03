package frontend;

import backend.ValidationReport;

public class ValidatorThread implements Runnable {

    public enum Type {
        ROW, COLUMN, BOX
    }

    private int[][] board;
    private ValidationReport report;
    private Type type;

    public ValidatorThread(int[][] board, ValidationReport report, Type type) {
        this.board = board;
        this.report = report;
        this.type = type;
    }

    @Override
    public void run() {

        switch (type) {
            case ROW ->
                checkRows();
            case COLUMN ->
                checkColumns();
            case BOX ->
                checkBoxes();
        }

        System.out.println(type + " THREAD FINISHED ✅");
    }

    private void checkRows() {
        for (int row = 0; row < 9; row++) {
            boolean[] seen = new boolean[10];

            for (int col = 0; col < 9; col++) {
                int value = board[row][col];

                if (seen[value]) {
                    report.addError("Duplicate in ROW " + (row + 1)
                            + " value = " + value);
                }
                seen[value] = true;
            }
        }
    }

    private void checkColumns() {
        for (int col = 0; col < 9; col++) {
            boolean[] seen = new boolean[10];

            for (int row = 0; row < 9; row++) {
                int value = board[row][col];

                if (seen[value]) {
                    report.addError("Duplicate in COLUMN " + (col + 1)
                            + " value = " + value);
                }
                seen[value] = true;
            }
        }
    }

    private void checkBoxes() {
        int box = 1;

        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {

                boolean[] seen = new boolean[10];

                for (int row = startRow; row < startRow + 3; row++) {
                    for (int col = startCol; col < startCol + 3; col++) {

                        int value = board[row][col];

                        if (seen[value]) {
                            report.addError(
                                    "Duplicate in BOX " + box
                                    + " value = " + value
                                    + " at (" + (row + 1) + "," + (col + 1) + ")"
                            );
                        }

                        seen[value] = true;
                    }
                }
                box++;
            }
        }
    }
}
