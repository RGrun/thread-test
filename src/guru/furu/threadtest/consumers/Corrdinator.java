package guru.furu.threadtest.consumers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by richard on 3/10/16.
 */
public class Corrdinator {

    private Scanner scanner;
    private Adder adder;
    private Multiplier multiplier;
    private Thread adderThread;
    private Thread multiplierThread;

    public Corrdinator() {
        this.scanner = new Scanner(System.in);
        this.adder = new Adder(this, 5);
        this.adderThread = new Thread(adder);
        this.multiplier = new Multiplier(this, 5);
        this.multiplierThread = new Thread(multiplier);

        startThreads();

        loop();
    }

    private void loop() {
        while(true) {
            System.out.print("Input new value: ");
            try {
                int newPayload = scanner.nextInt();

                // zero signals end of input
                if (newPayload == 0) {

                    adderThread.interrupt();
                    multiplierThread.interrupt();

                    // report out statuses
                    try {

                        List<String> adderLines =
                                Files.readAllLines(Paths.get(Adder.FILENAME), StandardCharsets.UTF_8);
                        List<String> multiplierLines =
                                Files.readAllLines(Paths.get(Multiplier.FILENAME), StandardCharsets.UTF_8);

                        // extra line of padding
                        System.out.print("\n");

                        adderLines.forEach(System.out::println);
                        multiplierLines.forEach(System.out::println);

                    } catch (Exception e) {
                        System.out.println("ERROR READING REPORT FILES: " + e.getMessage());
                    }

                    break;
                } else {
                    sendToAdder(newPayload);
                    sendToMultiplier(newPayload);
                }
            } catch (InputMismatchException ime) {
                System.out.println("Please enter only a number, or zero to end input.");
                scanner.next();
            }
        }

        System.exit(0);

    }

    private void startThreads() {
        adderThread.start();
        multiplierThread.start();
    }

    public void sendToAdder(int payload) {
        adder.sendToQueue(payload);
    }

    public void sendToMultiplier(int payload) {
        multiplier.sendToQueue(payload);
    }

}
