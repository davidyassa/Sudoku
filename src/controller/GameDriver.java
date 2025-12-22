/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import backend.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author DELL 7550
 */
public class GameDriver {

    private int[][] board = null;
    private ValidationResult res;
    private final HashMap<Difficulty, int[][]> games = new HashMap<>();
    private final Stack<int[][]> history = new Stack<>();

    // Undo Log
    private final Stack<Move> undoStack = new Stack<>();

    // Constructor that accepts path (Fixes Singleton Issue)
    public GameDriver(String path) {
        if (path != null) {
            csvManager.getInstance(path);
        }
        board = csvManager.getInstance().getTable();

    }

    public GameDriver() {
    }

    public boolean canSolve(int[][] currentBoard) {
        int emptyCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (currentBoard[i][j] == 0) {
                    emptyCount++;
                }
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

    public Validity verifyCurrentBoard(int[][] currentBoard) {
        res = new SequentialValidation(currentBoard).generateReport();
        return res.validate();
    }

    public void driveGames() throws InvalidGame {
        if (this.validateBoard() != Validity.VALID) {
            throw new InvalidGame("Source board not valid");
        }

        games.put(Difficulty.EASY, new GenerateGame(this.getBoard(), Difficulty.EASY).generate());
        games.put(Difficulty.MEDIUM, new GenerateGame(this.getBoard(), Difficulty.MEDIUM).generate());
        games.put(Difficulty.HARD, new GenerateGame(this.getBoard(), Difficulty.HARD).generate());

        long t = System.currentTimeMillis();
        csvManager.getInstance().saveBoard(games.get(Difficulty.EASY), "1-EASY", "game_" + t);
        csvManager.getInstance().saveBoard(games.get(Difficulty.MEDIUM), "2-MEDIUM", "game_" + t);
        csvManager.getInstance().saveBoard(games.get(Difficulty.HARD), "3-HARD", "game_" + t);
    }

    public void updateCell(int r, int c, int newValue) {
        int oldValue = board[r][c];
        if (oldValue != newValue) {
            undoStack.push(new Move(r, c, oldValue, newValue));
            board[r][c] = newValue;
        }
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Move lastMove = undoStack.pop();
            board[lastMove.row][lastMove.col] = lastMove.oldValue;
        }
    }

    public void saveUnfinishedGame() {
        long t = System.currentTimeMillis();
        csvManager.getInstance().saveBoard(board, "4-INCOMPLETE", "unfinished_" + t);
    }

    public boolean checkWinAndDelete() {
        if (validateBoard() == Validity.VALID) {
            return csvManager.getInstance().deleteCurrentFile();
        }
        return false;
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

    public static ArrayList<String> detectUnfinishedGames() {
        ArrayList<String> unfinished = new ArrayList<>();
        File incompleteDir = new File("./SudokuStorage/4-INCOMPLETE");

        if (!incompleteDir.exists()) {
            return unfinished;
        }

        File[] files = incompleteDir.listFiles();
        if (files == null) {
            return unfinished;
        }

        for (File f : files) {
            if (f.isFile()) {
                unfinished.add(f.getAbsolutePath());
            }
        }
        return unfinished;
    }

    public Difficulty[] detectAvailableDifficulties() {
        return Difficulty.values();
    }

}
