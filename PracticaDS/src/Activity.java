import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

/*
The class Activity is a component from which the classes task and project inherit,
it is used to represent the different activities of the Time Tracker. The design
pattern applied in this class is the "composite", because an activity is composed
of tasks and projects and at the same time projects are composed of more projects and tasks.
*/
public abstract class Activity {
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

  public Activity(String nameActivity, List<String> listOfTags, Activity father) {
    duration = Duration.ofSeconds(0);
    if (father != null) {
      father.add(this);
    }
    this.nameActivity = nameActivity;
    this.listOfTags = listOfTags;
    this.father = father;
    running =  false;
  } /*This is the constructor of the
  Activity class, it initializes the attributes of this class with the parameters it receives.*/

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
  } /*This is a getter that returns the duration that the activity has been active.*/

  public void add(Activity a) {
        //void

  }

  public abstract boolean getRunning(); /*This is an abstract method that is implemented by the classes that inherit
  from the activity class (project and tasca). This method has the objective of returning if the activity is being
  executed or not.*/

  public abstract void changeTime(LocalDateTime initialDate, LocalDateTime finalDate); /*This is an abstract method that
  is implemented by the classes that inherit from the activity class (project and tasca). The purpose of this method is
  to update the time of the activity.*/

  public abstract void acceptVisitor(Visitor visitor); /*This is an abstract method that is implemented by the classes
  that inherit from the activity class (project and tasca). This method is used for a visitor to call an activity so
  that the activity can execute one of the visitor's functionalities.*/

  public abstract JSONObject toJson(); /*This is an abstract method that is implemented by the classes that inherit from
  the activity class (project and tasca). The "toJson" method is used so that before closing the program or ending the
  execution, the data generated from the activities can be stored in JSON format.*/

}
