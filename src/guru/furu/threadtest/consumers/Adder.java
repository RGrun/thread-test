package guru.furu.threadtest.consumers;

import java.io.*;
import java.nio.channels.FileChannel;
import java.time.LocalTime;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by richard on 3/10/16.
 */
public class Adder extends Worker {
    private int toAdd;

    public static final String FILENAME = "adderReport.txt";

    public Adder(Corrdinator c, int toAdd) {
        this.master = c;
        this.inputQueue = new PriorityBlockingQueue<>();
        this.toAdd = toAdd;
        this.reportFile = new File(FILENAME);

        // does report file exist?
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
                // THIS CALL BLOCKS
                WorkMessage msg = (WorkMessage) ((PriorityBlockingQueue) inputQueue).take();

                int payload = msg.payload;
                LocalTime time = msg.timeCreated;

                String report = "Adder got message at: " + time + ". Result: " + (payload + toAdd) + "\n";

                builder.append(report);

            } catch (InterruptedException ie) {
                // catch InterruptedException to end loop and print report
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
            System.out.println("ERROR APPENDING TO ADDER LOG FILE: " + e.getMessage());
        }
    }

}
