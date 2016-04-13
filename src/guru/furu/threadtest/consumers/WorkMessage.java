package guru.furu.threadtest.consumers;

import java.time.LocalTime;

/**
 * Created by richard on 3/10/16.
 */
public class WorkMessage implements Comparable {
    public final LocalTime timeCreated;
    public final int payload;

    public WorkMessage(int payload) {
        this.timeCreated = LocalTime.now();
        this.payload = payload;
    }


    @Override
    public int compareTo(Object o) {
        WorkMessage other = (WorkMessage) o;
        return timeCreated.compareTo(other.timeCreated);
    }
}
