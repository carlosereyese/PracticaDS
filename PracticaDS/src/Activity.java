import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
The class Activity is a component from which the classes task and project inherit, it is used to represent the different
activities of the Time Tracker.
The design pattern applied in this class is the "composite", because an activity is composed of tasks and projects and
at the same time projects are composed of more projects and tasks.
*/
public abstract class Activity{
    protected String nameActivity;
    protected List<String> listOfTags = new ArrayList<>();
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
    public Activity(String nameActivity, List<String> listOfTags, Activity father)
    {
        duration = Duration.ofSeconds(0);
        if (father != null)
        {
            father.add(this);
        }
        this.nameActivity = nameActivity;
        this.listOfTags = listOfTags;
        this.father = father;
        running =  false;
    }

    public String getNameActivity()
    {
        return Objects.requireNonNullElse(nameActivity, "null");
    }
    public LocalDateTime getInitialDate()
    {
        return initialDate;
    }
    public LocalDateTime getFinalDate()
    {
        return finalDate;
    }
    public Duration getDuration() {  return duration; }
    public List<String> getListOfTags() { return listOfTags; }
    public void add(Activity a) {
        //void
    }

    public abstract boolean getRunning();
    public abstract void changeTime(LocalDateTime initialDate, LocalDateTime finalDate);
    public abstract void acceptVisitor(Visitor visitor);
    public abstract JSONObject toJSON();
}
