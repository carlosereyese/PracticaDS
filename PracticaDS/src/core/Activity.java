package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * The class Activity is a component from which the classes task and project inherit,
 * it is used to represent the different activities of the Time Tracker. The design
 * pattern applied in this class is the "composite", because an activity is composed
 * of tasks and projects and at the same time projects are composed of more projects and tasks.
 */
public abstract class Activity {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  protected String nameActivity;
  protected int id;
  protected List<String> listOfTags = new ArrayList<>();
  protected Activity father;
  protected LocalDateTime initialDate;
  protected LocalDateTime finalDate;
  protected Duration duration;
  protected boolean running;


  /**
   * Initialize variables by default.
   */
  public Activity() {
    initialDate = null;
    finalDate = null;
    running = false;
    duration = Duration.ofSeconds(0);
  }

  /**
   * This is the constructor of the Activity class, it initializes the attributes of this class
   * with the parameters it receives.
   */
  public Activity(String nameActivity, List<String> listOfTags, Activity father) {
    loggerMilestone1.debug("It has just entered the constructor by parameters of "
        + "the class Activity.");
    duration = Duration.ofSeconds(0);
    loggerMilestone1.trace("The default value of Duration is: {}", duration);
    if (father != null) {
      father.add(this);
    }
    this.nameActivity = nameActivity;
    loggerMilestone1.trace("The default value of nameActivity is: {}", nameActivity);
    this.id = IdProvider.getInstance().generateId();
    this.listOfTags = listOfTags;
    this.father = father;
    running = false;
    loggerMilestone1.trace("The default value of running is: {}", running);
    loggerMilestone1.debug("The attributes of the Activity class have been initialized with "
        + "the values passed by parameter to the constructor.");
  }

  public String getNameActivity() {
    return Objects.requireNonNullElse(nameActivity, "null");
  } /*This is a getter that returns the name of the activity.*/

  public LocalDateTime getInitialDate() {
    return initialDate;
  } /*This is a getter that returns the date on which the activity was first started.*/

  public LocalDateTime getFinalDate() {
    return finalDate;
  } /*This is a getter that returns the date on which the activity was last executed.*/

  public Duration getDuration() {
    return duration;
  } /*This is a getter that returns the duration that the activity has been active.*/

  public List<String> getListOfTags() {
    return listOfTags;
  } /*This is a getter that returns the activity tags.*/

  public void add(Activity a) {
    //void
  } /*This method is used to add one more activity to the list attribute.*/

  public abstract boolean getRunning(); /*This is an abstract method that is implemented by
  the classes that inherit from the activity class (project and task). This method has the
  objective of returning if the activity is being executed or not.*/

  public abstract void changeTime(LocalDateTime initialDate, LocalDateTime finalDate);
  /*This is an abstract method that is implemented by the classes that inherit from
  the activity class (project and task). The purpose of this method is to update the
  time of the activity.*/

  public abstract void acceptVisitor(Visitor visitor); /*This is an abstract method
  that is implemented by the classes that inherit from the activity class
  (project and task). This method is used for a visitor to call an activity so
  that the activity can execute one of the visitor's functionalities.*/

  public abstract Activity findActivityById(int id);

  public abstract JSONObject toJson(int depth); /*This is an abstract method that is implemented
  by the classes that inherit from the activity class (project and task). The "toJson"
  method is used so that before closing the program or ending the execution, the data
  generated from the activities can be stored in JSON format.*/
}
