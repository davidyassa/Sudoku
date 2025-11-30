/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.ArrayList;
import java.util.List;

public class ModeTwentySevenSolve {

    public void solve(int[][] board, ValidationReport report) {
        List<Thread> threads = new ArrayList<>();

        
        for (int i = 0; i < 9; i++) {
            threads.add(new Thread(TaskFactory.createChecker(board, RegionType.ROW, i, report)));
            threads.add(new Thread(TaskFactory.createChecker(board, RegionType.COLUMN, i, report)));
            threads.add(new Thread(TaskFactory.createChecker(board, RegionType.BOX, i, report)));
        }

        long startTime = System.nanoTime();

        for (Thread t : threads) t.start();

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                System.err.println("Thread interrupted");
            }
        }
        
        long endTime = System.nanoTime();
        System.out.println("Mode 27 Execution Time: " + (endTime - startTime) / 1000000 + " ms");
    }
}