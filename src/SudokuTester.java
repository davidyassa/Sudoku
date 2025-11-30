/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class SudokuTester {
     // string of all csv files paths
    public void runAllTests(String[] files) {
        for (String file : files) {
            testFile(file);
        }
    }

    private void testFile(String filepath) {

        System.out.println("Testing file: " + filepath);

        long t0 = runMode(filepath, 0);
        long t3 = runMode(filepath, 3);
        long t27 = runMode(filepath, 27);

        System.out.println("Execution Times:");
        System.out.println("0-mode: " + t0 + " ms");
        System.out.println("3-mode: " + t3 + " ms");
        System.out.println("27-mode: " + t27 + " ms");
        System.out.println("-----------------------------------\n");
    }

    private long runMode(String filepath, int mode) {
        SudokuBoard board = CSVLoader.load(filepath);

        Validator validator;

        if (mode == 0)
            validator = new SequentialValidator(board);
        else if (mode == 3)
            validator = new ThreeThreadValidator(board);
        else
            validator = new TwentySevenThreadValidator(board);

        long start = System.currentTimeMillis();
        ValidationResult result = validator.validate();
        long end = System.currentTimeMillis();

      
        return end - start;
    }
}