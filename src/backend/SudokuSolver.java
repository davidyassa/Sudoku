/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.List;

public class SudokuSolver {

    public int[] solve(int[][] board) throws InvalidGame {
        EmptyCellFinder finder = new EmptyCellFinder();
        List<int[]> emptyCells = finder.findEmptyCells(board);

        if (emptyCells.size() != 5) {
            throw new InvalidGame("Solver requires exactly 5 empty cells.");
        }

        // Validate initial board
        ValidationResult initial = new SequentialValidation(board).generateReport();
        if (initial.validate() == Validity.INVALID) {
            throw new InvalidGame("Board is invalid. Cannot solve.");
        }

        // Flyweight view of the board
        FlyweightBoardView view = new FlyweightBoardView(board, emptyCells);

        // Permutation iterator for empty cells
        PermutationIterator iterator = new PermutationIterator(emptyCells.size());

        while (iterator.hasNext()) {
            int[] candidate = iterator.next();

            // Validate candidate using Flyweight view
            if (isValidWithPermutation(view, candidate)) {
                // Apply candidate permanently to the board
                for (int i = 0; i < emptyCells.size(); i++) {
                    int[] pos = emptyCells.get(i);
                    board[pos[0]][pos[1]] = candidate[i];
                }
                return candidate; // Solution found
            }
        }

        throw new InvalidGame("No valid solution found.");
    }

    private boolean isValidWithPermutation(FlyweightBoardView view, int[] candidate) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int val = view.getCell(row, col, candidate);
                if (val != 0 && !isValidAtPosition(view, row, col, val, candidate)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidAtPosition(FlyweightBoardView view, int row, int col, int val, int[] candidate) {
        // Check row
        for (int c = 0; c < 9; c++) {
            if (c != col && view.getCell(row, c, candidate) == val) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < 9; r++) {
            if (r != row && view.getCell(r, col, candidate) == val) {
                return false;
            }
        }

        // Check 3x3 block
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if ((r != row || c != col) && view.getCell(r, c, candidate) == val) {
                    return false;
                }
            }
        }

        return true;
    }
}
