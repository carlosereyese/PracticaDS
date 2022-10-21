import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
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
    }
}
