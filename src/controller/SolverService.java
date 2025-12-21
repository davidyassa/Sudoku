/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import backend.InvalidGame;
import backend.SudokuSolver;

/**
 *
 * @author DELL 7550
 */
public class SolverService {

    public static void solve(String filepath) throws InvalidGame {
        GameDriver gd = new GameDriver(filepath);
        SudokuSolver solver = new SudokuSolver();
        solver.solve(gd.getBoard());
    }
}
