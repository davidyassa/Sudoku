/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;
import java.util.ArrayList;
import java.util.List;

public class EmptyCellFinder {

  
    // Finds all cells with value 0 
   //return an arraylistof arrays with its coordinates
     
    public List<int[]> findEmptyCells(int[][] board) {
        List<int[]> empty = new ArrayList<>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    empty.add(new int[]{r, c});
                }
            }
        }
        return empty;
    }
}
