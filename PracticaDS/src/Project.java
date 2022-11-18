import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


/*
Project is a class that inherits from Activity and that contains more projects and tasks
(it acts as a container), this means a project is a set of projects and tasks that have
been created in the application. A project can be decomposed into more activities, it can be
decomposed into more projects and tasks.
*/
public class Project extends Activity {
  private List<Activity> activityList = new ArrayList<>();

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
  }

  public Project() {
    nameActivity = "";
    initialDate = null;
    finalDate = null;
    running = false;
    activityList = null;

    assert (this.nameActivity != null); //Post condition
  }

  public Project(String nameActivity, List<String> listOfTags, Activity father) {
    super(nameActivity, listOfTags, father);

    assert (this.nameActivity != null); //Post condition
    checkInvariant(); //Invariant
  }

  public Project(JSONObject jsonObj) {
    nameActivity = jsonObj.getString("nameActivity");
    father = null;

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
  }

  public Object getElementFromList(int i) {
    return activityList.get(i);
  }

  public int getSizeList() {
    return activityList.size();
  }

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

  public void add(Activity a) {
    checkInvariant(); //Invariant
    if (a == null) { //Pre condition
      throw new IllegalArgumentException("Illegal activity");
    }

    activityList.add(a);

    assert (activityList != null);
    checkInvariant(); //Invariant
  }

  public void calculateTotalTime() { /*Calculates the active time of the project consisting of
    the sum of the active time of all its children (projects+tasks).*/
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
  }

  @Override
  public JSONObject toJson() {
    JSONObject compJson = new JSONObject();
    compJson.put("nameActivity", nameActivity);

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
    for (Activity activity : activityList) {
      ja.put(activity.toJson());
    }
    compJson.put("activityList", ja);

    checkInvariant(); //Invariant
    return compJson;
  } /*It is a function used to write the project data in a JSON file so that
    it can be loaded in the future.*/
}
