package guru.furu.threadtest.pollforsignal;

import java.util.Scanner;

/**
 * Created by richard on 3/10/16.
 */
public class PollController {

    private SleepingThread st;
    private Scanner scanner;

    public PollController() {
        scanner = new Scanner(System.in);

        startThread();
    }


    private void startThread() {
        System.out.print("Enter a time for the thread to sleep: ");
        long timeToSleep = scanner.nextLong();

        st = new SleepingThread(timeToSleep);

        st.start();

        pollThread();
    }

    private void report(String s) {
        System.out.println(s);
    }

    private void pollThread() {

        long counter = 0;
        while(true) {
            counter++;

            if (st.getState() == Thread.State.TERMINATED) {
                System.out.println("The thread finished!");
                System.out.println("It took " + counter + " iterations of the while loop!");
                break;
            }

        }

        System.exit(0);
    }
}
