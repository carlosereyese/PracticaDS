import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private Task father;
    private Duration duration;

    public Interval()
    {
        initialDate = null;
        finalDate = null;
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
    public Task getFather() { return father; }
    public void setFather(Task father) { this.father = father; }
    public void stop()
    {
        ClockTimer.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object object)
    {
        if (initialDate == null)
        {
            initialDate = (LocalDateTime) object;
        }
        finalDate = (LocalDateTime) object;
        this.duration = Duration.between(initialDate, finalDate);
        father.changeTime(initialDate, finalDate, this.duration);
    }
}
