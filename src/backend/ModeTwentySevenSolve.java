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
            threads.add(new Thread(new CheckerTask(board, "ROW", i, report)));
            threads.add(new Thread(new CheckerTask(board, "COL", i, report)));
            threads.add(new Thread(new CheckerTask(board, "BOX", i, report)));
        }

        long start = System.currentTimeMillis();

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
        
        long end = System.currentTimeMillis();
        System.out.println("Mode 27 finished in: " + (end - start) + "ms");
    }
}
