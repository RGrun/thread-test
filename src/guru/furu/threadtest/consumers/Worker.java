package guru.furu.threadtest.consumers;

import java.io.File;
import java.util.Queue;

/**
 * Created by richard on 3/10/16.
 */
public abstract class Worker implements Runnable {

    protected Queue<WorkMessage> inputQueue;
    protected Corrdinator master;
    protected File reportFile;



}
