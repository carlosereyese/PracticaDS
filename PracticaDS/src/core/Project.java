package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


/**
* Project is a class that inherits from Activity and that contains more projects and tasks
* (it acts as a container), this means a project is a set of projects and tasks that have
* been created in the application. A project can be decomposed into more activities, it can be
* decomposed into more projects and tasks.
*/
public class Project extends Activity {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  private static final Logger loggerMilestone2 = LogManager.getLogger("Milestone 2");
  private List<Activity> activityList = new ArrayList<>();

  /**
   * Initialize variables by default.
   */
  public Project() {
    nameActivity = "";
    initialDate = null;
    finalDate = null;
    running = false;
    activityList = null;

    assert (this.nameActivity != null); //Post condition
  }

  /**
   * This is the constructor of the
   * Project class, it initializes the attributes of this class with the parameters it receives.
   */
  public Project(String nameActivity, List<String> listOfTags, Activity father) {
    super(nameActivity, listOfTags, father);
    loggerMilestone1.debug("The parent constructor has just been executed to initialize "
        + "the Project class with the values passed as parameters.");
    assert (this.nameActivity != null); //Post condition
    checkInvariant(); //Invariant
  }

  /**
   * This is a constructor of the Project class, this constructor initializes
   * the attributes of this class with the data that it has stored in a JSON format
   * file that it had generated at the end of the last execution. The objective of
   * this constructor is to recover the last state of the class before shutting
   * down the program.
   */
  public Project(JSONObject jsonObj) {
    loggerMilestone2.debug("Entering the project constructor from JSON file.");
    nameActivity = jsonObj.getString("nameActivity");
    father = null;
    id = jsonObj.getInt("id");

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

    JSONArray jsonList = jsonObj.getJSONArray("activityList");
    for (int i = 0; i < jsonList.length(); i++) {
      if (jsonList.getJSONObject(i).has("intervalList")) {
        activityList.add(new Task(jsonList.getJSONObject(i)));
      } else {
        activityList.add(new Project(jsonList.getJSONObject(i)));
      }
    }

    for (Activity activity : activityList) {
      activity.father = this;
    }

    assert (this.nameActivity != null); //Post condition
    checkInvariant(); //Invariant
    loggerMilestone2.debug("Exiting in the project builder from JSON file.");
  }

  private void checkInvariant() {
    assert nameActivity == null || nameActivity.charAt(0) != ' ';
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

  public Object getElementFromList(int i) {
    return activityList.get(i);
  } /*This is a getter used to return an activity indicated by parameter that is
  part of the project.*/

  public int getSizeList() {
    return activityList.size();
  } /*This is a getter that is used to return the "activityList" size in order to
  know how many activities are part of a project.*/

  @Override
  public Activity findActivityById(int id) {
    if (this.id == id)
    {
      return this;
    } else {
      Activity foundActivity = null;
      for (Activity activity : activityList) {
         foundActivity = activity.findActivityById(id);
         if (foundActivity != null)
         {
           break;
         }
      }
      return foundActivity;
    }
  }
  /**
   * This method has the objective of returning if the activity is being executed or
   * not. To find out if the project is in execution, the method looks at all the activities
   * that make up the project to see if there is any active activity.
   */
  public boolean getRunning() {
    boolean run = false;
    int i = 0;

    while ((!run) && (i < activityList.size())) {
      if (activityList.get(i).getRunning()) {
        run = true;
      } else {
        i = i + 1;
      }
    }

    running = run;
    return running;
  }

  /**
   * This method is used to add one more activity to the list attribute.
   */
  public void add(Activity a) {
    checkInvariant(); //Invariant
    if (a == null) { //Pre condition
      loggerMilestone1.error("Illegal activity");
      throw new IllegalArgumentException("Illegal activity");
    }

    activityList.add(a);

    assert (activityList != null);
    checkInvariant(); //Invariant
  }

  /**
   * Calculates the active time of the project consisting of
   * the sum of the active time of all its children (projects+tasks).
   */
  public void calculateTotalTime() {
    checkInvariant(); //Invariant

    duration = Duration.ofSeconds(0);
    for (Activity activity : activityList) {
      duration = duration.plus(activity.getDuration());
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
   * This method is used so that if
   * any activity that is part of this project updates its time it can update the time
   * of its parent (this project) for consistency by calling this method.
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

    if (father != null) {
      father.changeTime(initialDate, finalDate);
    }

    assert (this.initialDate != null && this.finalDate != null); //Post condition
    checkInvariant(); //Invariant
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitProject(this);
  } /*This method is used for a visitor to call an activity so that the Project
  can execute one of the visitor's functionalities.*/

  @Override
  public JSONObject toJson(int depth) {
    JSONObject compJson = new JSONObject();
    compJson.put("class", "Project");
    if (depth > 0)
    {
      loggerMilestone1.debug("Entering the toJson method of Project.");
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

      compJson.put("duration", duration.toSeconds());
      compJson.put("running", running);

      JSONArray ja = new JSONArray();
      for (Activity activity : activityList) {
        ja.put(activity.toJson(depth--));
      }
      compJson.put("activityList", ja);

      checkInvariant(); //Invariant
      loggerMilestone1.debug("Exiting the toJson method of Project.");
    }

    return compJson;
  } /*It is a function used to write the project data in a JSON file so that
    it can be loaded in the future.*/
}