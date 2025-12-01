/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

public class SudokuTester {

    public void runAllTests(String[] files) {
        for (String file : files) {
            testFile(file);
        }
    }

    private void testFile(String filepath) {

        System.out.println("====================================");
        System.out.println("Testing file: " + filepath);
        System.out.println("====================================");

        // load board once
        int[][] board = csvManager.getInstance(filepath).getTable();

        long t0 = runMode("Mode 0", new ModeZeroSolve(board));
        long t3 = runMode("Mode 3", new ModeThreeSolve(board));
        long t27 = runMode("Mode 27", new ModeTwentySevenSolve(board));

        System.out.println("\nExecution Times:");
        System.out.println("0-mode:  " + t0 + " ms");
        System.out.println("3-mode:  " + t3 + " ms");
        System.out.println("27-mode: " + t27 + " ms");
        System.out.println("----------------------------------------\n");
    }

    private long runMode(String label, SudokuValidator validator) {

        long start = System.currentTimeMillis();
        ValidationResult result = validator.solve();
        long end = System.currentTimeMillis();

        System.out.println(label + " result:");
        printResult(result);

        return end - start;
    }

    private void printResult(ValidationResult r) {
        if (r.isValid()) {
            System.out.println("VALID");
            return;
        }

        System.out.println("INVALID\n");

        for (String err : r.getRowErrors()) {
            System.out.println(err);
        }

        for (String err : r.getColErrors()) {
            System.out.println(err);
        }

        for (String err : r.getBoxErrors()) {
            System.out.println(err);
        }

        System.out.println();
    }
}
