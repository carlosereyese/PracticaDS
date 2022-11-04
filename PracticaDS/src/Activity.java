import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
/*The class Activitat is a component from which the classes task and project inherit, it is used to represent the different activities of the
Time Tracker.*/
public abstract class Activity{
    protected String nameActivity;
    protected Activity father;
    protected LocalDateTime initialDate;
    protected LocalDateTime finalDate;
    protected Duration duration;
    protected boolean running;

    public Activity() {
        initialDate = null;
        finalDate = null;
        running = false;
        duration = Duration.ofSeconds(0);
    }
    public Activity(String nameActivity, Activity father)
    {
        duration = Duration.ofSeconds(0);
        if (father != null)
        {
            father.add(this);
        }
        this.nameActivity = nameActivity;
        this.father = father;
        running =  false;
    }

    public String getNameActivity()
    {
        if (nameActivity == null)
        {
            return "null";
        }
        else
        {
            return nameActivity;
        }
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
    public abstract boolean getRunning();
    public abstract Object getIList(int i);
    public abstract int getSizeList();

    public void add(Activity a) {
        //void
    }
    public abstract void calculateTotalTime();
    public abstract void changeTime(LocalDateTime initialDate, LocalDateTime finalDate);
    public abstract void acceptVisitor(Visitor visitor);
    public abstract JSONObject toJSON();
}
