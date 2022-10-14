import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private LocalDateTime initialDate = null;
    private LocalDateTime finalDate = null;
    public Interval()
    {
        //void
    }
    public LocalDateTime getInitialDate()
    {
        return initialDate;
    }
    public LocalDateTime getFinalDate()
    {
        return finalDate;
    }
    public void update(Observable observable, Object object)
    {
        if (initialDate == null)
        {
            initialDate = (LocalDateTime) object;
        }
        finalDate = (LocalDateTime) object;
    }
}
