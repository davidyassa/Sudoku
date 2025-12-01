/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.Locale;

/**
 * SudokuTester
 *
 * Usage: new SudokuTester().runOnce("path/to/board.csv", 5); or run from main:
 * java backend.SudokuTester path/to/board.csv [runs]
 *
 * Behavior: - Loads the CSV via csvManager - Warms up each validator once -
 * Runs each validator `runs` times and prints per-run time + average - Prints
 * the ValidationResult for correctness comparison
 */
public class SudokuTester {

    private static final int DEFAULT_RUNS = 5;

    /**
     * Run a full comparison for a single file (with default runs).
     */
    public void runOnce(String filepath) {
        runOnce(filepath, DEFAULT_RUNS);
    }

    /**
     * Run a full comparison for a single file, repeating each mode `runs`
     * times.
     */
    public void runOnce(String filepath, int runs) {
        System.out.println("==== SudokuTester ====");
        System.out.println("File: " + filepath);
        System.out.println("Runs per mode: " + runs);
        System.out.println();

        // initialize csv manager and board
        csvManager.getInstance(filepath);
        int[][] board = csvManager.getInstance().getTable();

        // Mode 0
        System.out.println("Mode 0 (sequential):");
        benchmarkAndReport("Mode 0", () -> {
            SudokuValidator v = new ModeZeroSolve(board);
            return v.validate();
        }, runs);

        // Mode 3
        System.out.println("\nMode 3 (3 threads):");
        benchmarkAndReport("Mode 3", () -> {
            SudokuValidator v = new ModeThreeSolve(board);
            return v.validate();
        }, runs);

        // Mode 27
        System.out.println("\nMode 27 (27 threads):");
        benchmarkAndReport("Mode 27", () -> {
            SudokuValidator v = new ModeTwentySevenSolve(board);
            return v.validate();
        }, runs);

        System.out.println("Test Over");
    }

    /**
     * Functional interface for running a validation and returning a
     * ValidationResult.
     */
    @FunctionalInterface
    private interface ValidationRun {

        ValidationResult run();
    }

    /**
     * Warm up once, then run the validation `runs` times and print per-run
     * times and average.
     */
    private void benchmarkAndReport(String label, ValidationRun runner, int runs) {
        // Warm-up (single run, not timed in the same way)
        ValidationResult warm = runner.run();

        // Print correctness of warm-up (short)
        System.out.println("Warm-up result (summary): " + (warm.isValid() ? "VALID" : "INVALID"));

        long[] times = new long[runs];
        ValidationResult lastResult = null;

        for (int i = 0; i < runs; i++) {
            long t0 = System.nanoTime();
            lastResult = runner.run();
            long t1 = System.nanoTime();
            times[i] = t1 - t0;
            System.out.printf(Locale.US, "Run %2d: %8.3f ms%n", i + 1, times[i] / 1_000_000.0);
        }

        // average
        double avgMs = 0;
        for (long t : times) {
            avgMs += t / 1_000_000.0;
        }
        avgMs /= runs;

        System.out.printf(Locale.US, "Average: %.3f ms%n", avgMs);

        // print the detailed ValidationResult once
        System.out.println("Detailed validation result (last run):");
        SudokuReporter.printResult(lastResult);
    }
}
