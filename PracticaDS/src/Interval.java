import org.json.JSONObject;

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

    public Interval (JSONObject jsonObj){
        if (!jsonObj.isNull("initialDate"))
            initialDate = LocalDateTime.parse(jsonObj.getString("initialDate"));
        else
            initialDate = null;

        if (!jsonObj.isNull("finalDate"))
            finalDate = LocalDateTime.parse(jsonObj.getString("finalDate"));
        else
            finalDate = null;

        running = jsonObj.getBoolean("running");
        duration = Duration.parse(jsonObj.getString("duration"));
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
    public JSONObject toJSON(){
        JSONObject intervalJSON = new JSONObject();
        String tempDate;
        if (initialDate == null)
            intervalJSON.put("initialDate", JSONObject.NULL);
        else{
            tempDate = initialDate.toString();
            intervalJSON.put("initialDate", tempDate);
        }

        if (finalDate == null)
            intervalJSON.put("finalDate", JSONObject.NULL);
        else{
            tempDate = finalDate.toString();
            intervalJSON.put("finalDate", tempDate);
        }
        intervalJSON.put("duration", duration.toString());
        intervalJSON.put("running", running);

        return intervalJSON;
    }
}
