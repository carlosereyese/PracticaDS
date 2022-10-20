import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private LocalDateTime initialDate = null;
    private LocalDateTime finalDate = null;
    private boolean active = true;
    public Interval()
    {
        //void
    }
    public boolean getActive() { return active; }
    public LocalDateTime getInitialDate()
    {
        return initialDate;
    }
    public LocalDateTime getFinalDate()
    {
        return finalDate;
    }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public void update(Observable observable, Object object)
    {
        if (active == true)
        {
            if (initialDate == null)
            {
                initialDate = (LocalDateTime) object;
            }
            finalDate = (LocalDateTime) object;
        }
    }
}
