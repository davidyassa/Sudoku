/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import backend.*;

/**
 *
 * @author DELL 7550
 */
public class GameDriver {

    private final int[][] board;
    private final SequentialValidation seq;
    private final ValidationResult res;

    public GameDriver() {
        board = csvManager.getInstance().getTable();
        seq = new SequentialValidation(board);
        res = seq.generateReport();
    }

    /**
     *
     * @return String -> full report
     */
    public Validity boardValidity() {
        return res.validate();
    }

    public String getReportOrValid() {
        Validity v = boardValidity();
        StringBuilder sb = new StringBuilder("TEST RESULT:\n");
        switch (v) {
            case VALID ->
                sb.append("\nVALID SUDOKU\n");
            case INCOMPLETE -> {
                res.getNulls().forEach(e -> sb.append(e).append("\n"));
            }
            case INVALID -> {
                sb.append("\nINVALID\n\n");
                res.getRowErrors().forEach(e -> sb.append(e).append("\n"));
                res.getColErrors().forEach(e -> sb.append(e).append("\n"));
                res.getBoxErrors().forEach(e -> sb.append(e).append("\n"));
            }
        }
        return sb.toString();
    }

}
