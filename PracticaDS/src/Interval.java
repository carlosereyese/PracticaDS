import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/*
Interval is a class that contains the start and end time of a time interval contained in a task
class. The design pattern that applies to this class is the "Observer-Observable" pattern,
because this class is pending of clock warnings to update the start-end data of an interval.
*/
public class Interval implements Observer {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  private static final Logger loggerMilestone2 = LogManager.getLogger("Milestone 2");
  private LocalDateTime initialDate;
  private LocalDateTime finalDate;
  private Task father;
  private Duration duration;
  private boolean running;

  public Interval() {
    initialDate = null;
    finalDate = null;
    running = true;
    ClockTimer.getInstance().addObserver(this);
  }

  public Interval(JSONObject jsonObj) {
    loggerMilestone2.debug("Entering the interval constructor from JSON file.");
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

    if (!jsonObj.isNull("duration")) {
      duration = Duration.parse(jsonObj.getString("duration"));
    } else {
      duration = null;
    }

    running = jsonObj.getBoolean("running");
    loggerMilestone2.debug("Exiting in the interval builder from JSON file.");
  } /*It is a constructor used to load data from the JSON file to
    initialize the interval.*/

  public LocalDateTime getInitialDate() {
    return initialDate;
  } /*This is a getter that returns the date on which the activity was first started.*/

  public LocalDateTime getFinalDate() {
    return finalDate;
  } /*This is a getter that returns the date on which the activity was last executed.*/

  public Duration getDuration() {
    return duration;
  } /*This is a getter that returns the duration that the activity has been active.*/

  public boolean getRunning() {
    return running;
  } /* This method has the objective of returning if the activity is being
  executed or not.*/

  public void setFather(Task father) {
    this.father = father;
  } /*This method is a setter that serves to indicate who is the parent of the interval,
  in other words it serves to initialize the variable father with the task to which the
  interval belongs.*/

  public void stop() {
    loggerMilestone1.debug("Entering the stop method of Interval.");
    ClockTimer.getInstance().deleteObserver(this);
    loggerMilestone1.debug("Exiting the stop method of Interval.");
    running = false;
    loggerMilestone1.trace("The value of the running attribute in Interval is: {}", running);
  } /*This method is used to indicate when the interval has finished its execution.*/

  @Override
  public void update(Observable observable, Object object) {
    if (initialDate == null) {
      initialDate = ((LocalDateTime) object).minus(Duration.ofSeconds(2));
    }
    finalDate = (LocalDateTime) object;
    duration = Duration.between(initialDate, finalDate);
    father.changeTime(initialDate, finalDate); /*With this line of code,
    its parent is notified recursively to update its times.*/
  } /*The update method is used so that the interval can
  receive notifications from the ClockTimer class when there is an update of the time
  for the interval to change its dates.*/

  public void acceptVisitor(Visitor visitor) {
    visitor.visitInterval(this);
  } /*This method is used for a visitor to call the interval so that the interval can
  execute one of the visitor's functionalities.*/

  public JSONObject toJson() {
    loggerMilestone1.debug("Entering the toJson method of Interval.");
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

    if (duration == null) {
      intervalJson.put("duration", JSONObject.NULL);
    } else {
      String tempDuration = duration.toString();
      intervalJson.put("duration", tempDuration);
    }

    intervalJson.put("duration", duration.toString());
    intervalJson.put("running", running);

    loggerMilestone1.debug("Exiting the toJson method of Interval.");
    return intervalJson;
  } /*It is a function used to write the interval data in a JSON file so that
    it can be loaded in the future.*/
}
