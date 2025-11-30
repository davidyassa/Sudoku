
package backend;

import frontend.ValidatorThread;

public class ThreadCoordinator {

    public static boolean runThreeMode(int[][] board) {

        ValidationReport report = new ValidationReport();

        Thread rowThread = new Thread(
                new ValidatorThread(board, report, ValidatorThread.Type.ROW));

        Thread colThread = new Thread(
                new ValidatorThread(board, report, ValidatorThread.Type.COLUMN));

        Thread boxThread = new Thread(
                new ValidatorThread(board, report, ValidatorThread.Type.BOX));

        rowThread.start();
        colThread.start();
        boxThread.start();

        try {
 
            rowThread.join();
            colThread.join();
            boxThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted");
        }

        if (report.isValid()) {
            return true;
        } else {
            for (String err : report.getErrors()) {
                System.out.println(err);
            }
            return false;
        }
    }
}
