/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.List;

/**
 *
 * @author DELL 7550
 */
public class GenerateGame {

    private RandomPairs rnd;
    private List<int[]> pairs;
    private int[][] board;

    public GenerateGame(int[][] board, Difficulty diff) {
        this.board = board;
        rnd = new RandomPairs();
        switch (diff) {
            case EASY -> {
                pairs = rnd.generateDistinctPairs(10);
            }
            case MEDIUM -> {
                pairs = rnd.generateDistinctPairs(20);
            }
            case HARD -> {
                pairs = rnd.generateDistinctPairs(25);
            }
        }
    }

    public int[][] generate() {
        int[][] generated = csvManager.clone(board);
        for (int[] pair : pairs) {
            generated[pair[0]][pair[1]] = 0;
        }
        return generated;
    }

}
