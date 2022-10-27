import java.time.LocalDateTime;
import java.util.Observable;

public class ClockTimer extends Observable {
    private LocalDateTime dateTime;
    private static ClockTimer instance;

    private ClockTimer()
    {
        this.dateTime = null;
    }
    public static ClockTimer getInstance()
    {
        if (instance ==  null)
        {
            instance = new ClockTimer();
        }

        return instance;
    }

    private void tick()
    {
        this.dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(this.dateTime);
    }
    public void startTimer() throws InterruptedException {
        while(true)
        {
            tick();
            Thread.sleep(2000);
        }
    }
}
