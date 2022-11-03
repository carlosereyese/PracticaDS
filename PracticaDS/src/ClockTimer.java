import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class ClockTimer extends Observable {
    private Timer timer = new Timer();
    private LocalDateTime dateTime;
    private static ClockTimer instance = null;
    private final long delay = 0L;
    private final long period = 2000L;

    private ClockTimer() {
        this.dateTime = null;
    }

    public static ClockTimer getInstance() {
        if (instance == null) {
            instance = new ClockTimer();
        }

        return instance;
    }

    private void tick() {
        this.dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(this.dateTime);
    }
    /*private TimerTask tick = new TimerTask()
    {
        public void run() {
            setChanged();
            notifyObservers(LocalDateTime.now());
        }
    };*/

    public void startTimer() throws InterruptedException {
        while (true) {
            tick();
            Thread.sleep(period);
        }
        //timer.scheduleAtFixedRate(tick, delay, period);
    }
}