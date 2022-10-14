import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;

public class ClockTimer extends Observable {
    private Timer timer;
    private LocalDateTime dateTime;

    private void tick()
    {
        dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(dateTime);
    }

    public void startTimer()
    {
        while(true)
        {
            try {
                Thread.sleep(1500);
            } catch(InterruptedException e) {
                System.out.println("ERROR: Thread sleep interrupted");
            }
            tick();
        }
    }
}
