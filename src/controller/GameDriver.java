/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import backend.*;
import java.util.HashMap;

/**
 *
 * @author DELL 7550
 */
public class GameDriver {

    private final int[][] board;
    private ValidationResult res;
    private final HashMap<Difficulty, int[][]> games = new HashMap<>();

    public GameDriver(String filepath) {
        board = csvManager.getInstance(filepath).getTable();
    }

    public void driveGames() throws InvalidGame {

        if (this.validateBoard() != Validity.VALID) {
            throw new InvalidGame("Source board not valid");
        }

        int[][] easyBoard = new GenerateGame(this.getBoard(), Difficulty.EASY).generate();
        int[][] mediumBoard = new GenerateGame(this.getBoard(), Difficulty.MEDIUM).generate();
        int[][] hardBoard = new GenerateGame(this.getBoard(), Difficulty.HARD).generate();
        games.put(Difficulty.EASY, easyBoard);
        games.put(Difficulty.MEDIUM, mediumBoard);
        games.put(Difficulty.HARD, hardBoard);

        // ↓↓↓↓ then save boards to folders ↓↓↓↓
        // for(int[][] board : games) csvManager.save(board); //7aga keda
    }

    public Validity validateBoard() {
        res = new SequentialValidation(board).generateReport();

        return res.validate();

    }

    public int[][] getBoard() {
        return board;
    }

    public ValidationResult getResult() {
        return res;
    }

    public HashMap<Difficulty, int[][]> getGames() {
        return games;
    }

}
