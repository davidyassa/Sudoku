/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import backend.*;
import java.util.HashMap;
import java.util.Stack;
import java.util.List;  
import java.util.Arrays; 
import java.util.ArrayList;

/**
 *
 * @author DELL 7550
 */
public class GameDriver {

    private final int[][] board;
    private ValidationResult res;
    private final HashMap<Difficulty, int[][]> games = new HashMap<>();
    private final Stack<int[][]> history = new Stack<>();

    public GameDriver() {
        board = csvManager.getInstance().getTable();
    }
    
    public boolean canSolve(int[][] currentBoard) {
        int emptyCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (currentBoard[i][j] == 0) emptyCount++;
            }
        }
        return emptyCount == 5;
    }
    
    public void saveToHistory(int[][] currentBoard) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(currentBoard[i], 0, copy[i], 0, 9);
        }
        history.push(copy);
    }
    
    public int[][] undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }

    public Validity verifyCurrentBoard(int[][] currentBoard) {
        res = new SequentialValidation(currentBoard).generateReport();
        return res.validate(); 
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

public List<String> detectUnfinishedGames() {
        
       
        return Arrays.asList("Game (Incomplete)", "Game (Paused)");
    }

    
    public Difficulty[] detectAvailableDifficulties() {
        return Difficulty.values(); 
    }

}
