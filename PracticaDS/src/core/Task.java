package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


/**
* Task is a class that inherits from Activity and contains execution intervals that indicate
* how long the task has been active.
* A task is an activity that cannot be decomposed into other activities such as the project,
* an activity is made up of intervals that indicate the time slots in which the task has
* been active.
*/
public class Task extends Activity {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  private static final Logger loggerMilestone2 = LogManager.getLogger("Milestone 2");
  private final List<Interval> intervalList = new ArrayList<>();

  /**
   * This is the constructor of the
   * Task class, it initializes the attributes of this class with the parameters it receives.
   */
  public Task(String nameActivity, List<String> listOfTags, Activity father) {
    super(nameActivity, listOfTags, father);
    loggerMilestone1.debug("The parent constructor has just been executed to "
        + "initialize the Task class with the values passed as parameters.");
    assert (this.nameActivity != null && this.father != null); //Post condition
    checkInvariant(); //Invariant
  }

  /**
   * It is a constructor used to load data from the JSON file to
   * initialize the task.
   */
  public Task(JSONObject jsonObj) {
    loggerMilestone2.debug("Entering the task constructor from JSON file.");
    nameActivity = jsonObj.getString("nameActivity");

    id = jsonObj.getInt("id");

    father = new Project();

    JSONArray jsonListTags = jsonObj.getJSONArray("listOfTags");
    for (int i = 0; i < jsonListTags.length(); i++) {
      listOfTags.add(jsonListTags.getString(i));
    }

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

    duration = Duration.parse(jsonObj.getString("duration"));
    running = jsonObj.getBoolean("running");

    JSONArray jsonList = jsonObj.getJSONArray("intervalList");
    for (int i = 0; i < jsonList.length(); i++) {
      intervalList.add(new Interval(jsonList.getJSONObject(i)));
    }

    for (Interval interval : intervalList) {
      interval.setFather(this);
    }

    assert (this.nameActivity != null && this.father != null); //Post condition
    checkInvariant(); //Invariant
    loggerMilestone2.debug("Exiting in the task builder from JSON file.");
  }

  private void checkInvariant() {
    assert ((nameActivity == null && father == null)
        || (nameActivity != null && father != null));
    assert (Objects.requireNonNull(nameActivity).charAt(0) != ' ');
    assert ((initialDate == null && finalDate == null)
        || (initialDate != null && finalDate != null));
    assert ((duration == Duration.ofSeconds(0))
        || (duration.toDays() >= Duration.ofSeconds(0).toDays()
        && duration.toHours() >= Duration.ofSeconds(0).toHours()
        && duration.toMinutes() >= Duration.ofSeconds(0).toMinutes()
        && duration.toSeconds() >= Duration.ofSeconds(0).toSeconds()
        && duration.toMillis() >= Duration.ofSeconds(0).toMillis()));
  } /*The check Invariant method is used to apply the design by contract, this
  method is used to check if the values of the attributes are valid.*/

  public int getSizeList() {
    return intervalList.size();
  } /*This is a getter that is used to return the "intervalList" size in order to
  know how many execution time intervals does the task have.*/

  public Object getElementsFromList(int i) {
    return intervalList.get(i);
  } /*This is a getter used to return an interval indicated by parameter that is part
  of the project.*/

  public boolean getRunning() {
    return running;
  } /*This method has the objective of returning if the activity is being
    executed or not.*/

  /**
   * This method is used to indicate that a task becomes active, when activating the rate a new
   * execution interval is created.
   */
  public void start() {
    checkInvariant(); //Invariant
    if (running) { //Pre condition
      loggerMilestone1.error("An attempt is being made to initiate a transaction that "
          + "has already been initiated");
      throw new IllegalArgumentException("An attempt is being made to initiate a "
          + "transaction that has already been initiated");
    }

    loggerMilestone1.info(nameActivity + " starts");
    Interval newInterval = new Interval();
    newInterval.setFather(this);
    newInterval.setId(IdProvider.getInstance().generateId());
    intervalList.add(newInterval);
    running = true;

    assert (running); //Post condition
    assert (intervalList != null); //Post condition
    checkInvariant(); //Invariant
  }

  /**
   * This method is used to indicate that a transaction is no longer running.
   */
  public void stop() {
    checkInvariant(); //Invariant
    if (!running) { //Pre condition
      loggerMilestone1.error("An attempt is being made to finalize a completed share");
      throw new IllegalArgumentException("An attempt is being made to "
          + "finalize a completed share");
    }

    intervalList.get(intervalList.size() - 1).stop();
    loggerMilestone1.info(nameActivity + " stops");
    running = false;

    assert (!running); //Post condition
    checkInvariant(); //Invariant
  }

  /**
   * Calculates the active time of the task consisting of
   * the sum of the active time of all its intervals.
   */
  public void calculateTotalTime() {

    checkInvariant(); //Invariant

    duration = Duration.ofSeconds(0);
    for (Interval interval : intervalList) {
      duration = duration.plus(interval.getDuration());
    }

    assert ((duration == Duration.ofSeconds(0))
        || (duration.toDays() >= Duration.ofSeconds(0).toDays()
        && duration.toHours() >= Duration.ofSeconds(0).toHours()
        && duration.toMinutes() >= Duration.ofSeconds(0).toMinutes()
        && duration.toSeconds() >= Duration.ofSeconds(0).toSeconds()
        && duration.toMillis() >= Duration.ofSeconds(0).toMillis())); //Post condition
    checkInvariant(); //Invariant
  }

  /**
   * This method is used so that when
   * one of its intervals updates its time, the interval can update the time of the
   * rate it belongs to by calling this method.
   */
  public void changeTime(LocalDateTime initialDate, LocalDateTime finalDate) {
    checkInvariant(); //Invariant
    if (initialDate == null) { //Pre condition
      throw new IllegalArgumentException("Illegal initialDate");
    } else if (finalDate == null) { //Pre condition
      throw new IllegalArgumentException("Illegal finalDate");
    }

    if (this.initialDate == null) {
      this.initialDate = initialDate;
    }
    this.finalDate = finalDate;
    this.calculateTotalTime();
    father.changeTime(initialDate, finalDate);

    assert (this.initialDate != null && this.finalDate != null); //Post condition
    checkInvariant(); //Invariant
  }

  @Override
  public Activity findActivityById(int id) {
    if (this.id == id)
    {
      return this;
    } else {
      return null;
    }
  }
  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  } /*This method is used for a visitor to call an activity so that the Project can
  execute one of the visitor's functionalities.*/

  /**
   * It is a function used to write the task data in a JSON file so that
   * it can be loaded in the future.
   */
  public JSONObject toJson(int depth) {
    JSONObject compJson = new JSONObject();
    if (depth != 0) {
      loggerMilestone1.debug("Entering the toJson method of Task.");
      checkInvariant(); //Invariant


      compJson.put("nameActivity", nameActivity);

      compJson.put("id", id);

      JSONArray jl = new JSONArray();
      for (String tag : listOfTags) {
        jl.put(tag);
      }
      compJson.put("listOfTags", jl);

      String tempDate;
      if (initialDate == null) {
        compJson.put("initialDate", JSONObject.NULL);
      } else {
        tempDate = initialDate.toString();
        compJson.put("initialDate", tempDate);
      }

      if (finalDate == null) {
        compJson.put("finalDate", JSONObject.NULL);
      } else {
        tempDate = finalDate.toString();
        compJson.put("finalDate", tempDate);
      }

      compJson.put("duration", duration.toString());
      compJson.put("running", running);

      JSONArray ja = new JSONArray();
      for (Interval interval : intervalList) {
        ja.put(interval.toJson());
      }

      compJson.put("intervalList", ja);

      checkInvariant(); //Invariant
      loggerMilestone1.debug("Exiting the toJson method of Task.");
    }
    depth--;
    return compJson;
  }
}
