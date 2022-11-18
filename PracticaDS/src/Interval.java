import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;

/*
Interval is a class that contains the start and end time of a time interval contained in a task
class. The design pattern that applies to this class is the "Observer-Observable" pattern,
because this class is pending of clock warnings to update the start-end data of an interval.
*/
public class Interval implements Observer {
  private LocalDateTime initialDate;
  private LocalDateTime finalDate;
  private Task father;
  private Duration duration;
  private boolean running;

  public Interval() {
    initialDate = null;
    finalDate = null;
    running =  true;
    ClockTimer.getInstance().addObserver(this);
  }

  public Interval(JSONObject jsonObj) {
    if (!jsonObj.isNull("initialDate")) {
      initialDate = LocalDateTime.parse(jsonObj.getString("initialDate"));
    } else {
      initialDate = null;
    }
    if (!jsonObj.isNull("finalDate")) {
      finalDate = LocalDateTime.parse(jsonObj.getString("finalDate"));
    } else {
      finalDate = null;
    }

    running = jsonObj.getBoolean("running");
    duration = Duration.parse(jsonObj.getString("duration"));
  } /*It is a constructor used to load data from the JSON file to
    initialize the interval.*/

  public LocalDateTime getInitialDate() {
    return initialDate;
  }

  public LocalDateTime getFinalDate() {
    return finalDate;
  }

  public Duration getDuration() {
    return duration;
  }

  public boolean getRunning() {
    return running;
  }

  public void setFather(Task father) {
    this.father = father;
  }

  public void stop() {
    ClockTimer.getInstance().deleteObserver(this);
    running = false;
  }

  @Override
    public void update(Observable observable, Object object) { /*With the "update" method
    it is possible to receive the notifications of the observed class "ClockTimer" in
    order to proceed with the update of the times.*/
    if (initialDate == null) {
      initialDate = ((LocalDateTime) object).minus(Duration.ofSeconds(2));
    }
    finalDate = (LocalDateTime) object;
    duration = Duration.between(initialDate, finalDate);
    father.changeTime(initialDate, finalDate); /*With this line of code,
    its parent is notified recursively to update its times.*/
  }

  public void acceptVisitor(Visitor visitor) {
    visitor.visitInterval(this);
  }

  public JSONObject toJson() {
    JSONObject intervalJson = new JSONObject();
    String tempDate;
    if (initialDate == null) {
      intervalJson.put("initialDate", JSONObject.NULL);
    } else {
      tempDate = initialDate.toString();
      intervalJson.put("initialDate", tempDate);
    }

    if (finalDate == null) {
      intervalJson.put("finalDate", JSONObject.NULL);
    } else {
      tempDate = finalDate.toString();
      intervalJson.put("finalDate", tempDate);
    }
    intervalJson.put("duration", duration.toString());
    intervalJson.put("running", running);

    return intervalJson;
  } /*It is a function used to write the interval data in a JSON file so that
    it can be loaded in the future.*/
}
