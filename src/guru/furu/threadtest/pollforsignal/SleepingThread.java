package guru.furu.threadtest.pollforsignal;

/**
 * Created by richard on 3/10/16.
 */
public class SleepingThread extends Thread {

    private long timeToSleep;

    public SleepingThread(long timeToSleep) {
        this.timeToSleep = timeToSleep;
    }


    @Override
    public void run() {

        System.out.println("Sleeper thread starting...");

        try {
            Thread.sleep(timeToSleep);

        } catch (InterruptedException ie) {
            System.out.println("You interrupted my nap!");
        }
    }

}
