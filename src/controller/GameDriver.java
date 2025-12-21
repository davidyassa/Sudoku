/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import backend.*;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author DELL 7550
 */
public class GameDriver {

    private final int[][] board;
    private ValidationResult res;
    private final HashMap<Difficulty, int[][]> games = new HashMap<>();

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
        board = csvManager.getInstance().getTable();
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
}
