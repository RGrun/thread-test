package guru.furu.threadtest;

import guru.furu.threadtest.consumers.Corrdinator;
import guru.furu.threadtest.pollforsignal.PollController;

public class Main {

    public static void main(String[] args) {

        if(args.length > 0) {
            switch (args[0]) {
                case "consumers":
                    new Corrdinator();
                    break;
                case "poll":
                    new PollController();
                    break;
                default:
                    System.out.println("Options: comsumers or poll");
                    break;
            }
        } else {
            System.out.println("Options: comsumers or poll");
        }

        //new Corrdinator();

        //new PollController();
    }
}
