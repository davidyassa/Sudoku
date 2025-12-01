/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// to print errors
package backend;

import java.util.List;

public class SudokuReporter {

    public static void printResult(ValidationResult r) {
        if (r.isValid()) {
            System.out.println("VALID");
            return;
        }

        System.out.println("INVALID");
        printBlock(r.getRowErrors());
        System.out.println("------------------------------------------");
        printBlock(r.getColErrors());
        System.out.println("------------------------------------------");
        printBlock(r.getBoxErrors());
    }

    private static void printBlock(List<String> errors) {
        for (String err : errors) {
            System.out.println(err);
        }
    }
}
