/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author Mostafa
 */
public class TaskFactory {
    public static Runnable createChecker(int[][] board, RegionType type, int index, ValidationReport report) {
        return new CheckerTask(board, type, index, report);
    }
}
