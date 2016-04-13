package guru.furu.threadtest.consumers;

import java.io.*;
import java.nio.channels.FileChannel;
import java.time.LocalTime;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by richard on 3/10/16.
 */
public class Multiplier extends Worker {
    private int toMultiply;

    public static final String FILENAME = "multiplierReport.txt";


    public Multiplier(Corrdinator c, int toMultiply) {
        this.master = c;
        this.inputQueue = new PriorityBlockingQueue<>();
        this.toMultiply = toMultiply;

        this.reportFile = new File(FILENAME);

        if(!reportFile.exists()) {
            try {
                reportFile.createNewFile();
            } catch (Exception e) {
                System.out.println("ERROR CREATING ADDER REPORT FILE: " + e.getMessage());
            }

        } else {
            // truncate file
            try(FileChannel outChan = new FileOutputStream(FILENAME, true).getChannel()) {
                outChan.truncate(0);
                outChan.close();
            } catch (Exception e) {
                System.out.println("ERROR TRUNCATING FILE: " + e.getMessage());
            }

        }
    }

    @Override
    public void run() {

        StringBuilder builder = new StringBuilder();

        while(true) {

            try {
                WorkMessage msg = (WorkMessage) ((PriorityBlockingQueue) inputQueue).take();

                int payload = msg.payload;
                LocalTime time = msg.timeCreated;

                String report = "Multiplier got message at: " + time + ". Result: " + (payload * toMultiply) + "\n";

                builder.append(report);

            } catch (InterruptedException ie) {
                writeToReportFile(builder.toString());
            }

        }
    }

    public void sendToQueue(int payload) {
        WorkMessage newMsg = new WorkMessage(payload);

        this.inputQueue.add(newMsg);
    }

    private void writeToReportFile(String s) {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME, true)))) {
            out.println(s);

        }catch (IOException e) {
            System.out.println("ERRROR APPENDING TO ADDER LOG FILE: " + e.getMessage());
        }
    }
}
