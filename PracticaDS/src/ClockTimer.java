import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;

public class ClockTimer extends Observable {
    private LocalDateTime dateTime;

    private void tick()
    {
        this.dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(this.dateTime);
    }

    public void startTimer()
    {
        while(true)
        {
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                System.out.println("ERROR: Thread sleep interrupted");
            }
            tick();
        }
    }
}
