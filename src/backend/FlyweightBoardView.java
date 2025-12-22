/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.List;

public class FlyweightBoardView {

    private final int[][] board;
    private final List<int[]> emptyCells;

    public FlyweightBoardView(int[][] board, List<int[]> emptyCells) {
        this.board = board;
        this.emptyCells = emptyCells;
    }

    /**
     * @return Returns cell value considering permutation overlay
     */
    public int getCell(int row, int col, int[] permutation) {
        for (int i = 0; i < emptyCells.size(); i++) {
            int[] pos = emptyCells.get(i);
            if (pos[0] == row && pos[1] == col) {
                return permutation[i];
            }
        }
        return board[row][col];
    }
}
