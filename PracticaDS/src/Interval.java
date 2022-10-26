import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private Task father;
    private Duration duration;
    private boolean running;

    public Interval()
    {
        initialDate = null;
        finalDate = null;
        running =  true;
        ClockTimer.getInstance().addObserver(this);
    }
    public LocalDateTime getInitialDate()
    {
        return initialDate;
    }
    public LocalDateTime getFinalDate()
    {
        return finalDate;
    }
    public Duration getDuration() { return duration; }
    public Task getFather() { return father; }
    public boolean getRunning() { return running; }

    public void setFather(Task father) { this.father = father; }
    public void stop()
    {
        ClockTimer.getInstance().deleteObserver(this);
        running = false;
    }
    @Override
    public void update(Observable observable, Object object)
    {
        if (initialDate == null)
        {
            initialDate = ((LocalDateTime) object).minus(Duration.ofSeconds(2));
        }
        finalDate = (LocalDateTime) object;
        duration = Duration.between(initialDate, finalDate);
        father.changeTime(initialDate, finalDate);
    }
}
