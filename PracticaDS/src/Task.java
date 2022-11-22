import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;



/*
Task is a class that inherits from Activity and contains execution intervals that indicate
how long the task has been active.
A task is an activity that cannot be decomposed into other activities such as the project,
an activity is made up of intervals that indicate the time slots in which the task has been active.
*/
public class Task extends Activity {
  private final List<Interval> intervalList = new ArrayList<>();

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
  }

  public Task(String nameActivity, List<String> listOfTags, Activity father) {
    super(nameActivity, listOfTags, father);

    assert (this.nameActivity != null && this.father != null); //Post condition
    checkInvariant(); //Invariant
  }

  public Task(JSONObject jsonObj) {
    nameActivity = jsonObj.getString("nameActivity");

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
  } /*It is a constructor used to load data from the JSON file to
    initialize the task.*/

  public int getSizeList() {
    return intervalList.size();
  }

  public Object getElementsFromList(int i) {
    return intervalList.get(i);
  }

  public boolean getRunning() {
    return running;
  }

  public void start() {
    checkInvariant(); //Invariant
    if (running) { //Pre condition
      throw new IllegalArgumentException("An attempt is being made to initiate a "
                                       + "transaction that has already been initiated");
    }

    System.out.println(nameActivity + " starts");
    Interval newInterval = new Interval();
    newInterval.setFather(this);
    intervalList.add(newInterval);
    running = true;

    assert (running); //Post condition
    assert (intervalList != null); //Post condition
    checkInvariant(); //Invariant
  }

  public void stop() {
    checkInvariant(); //Invariant
    if (!running) { //Pre condition
      throw new IllegalArgumentException("An attempt is being made to "
                                       + "finalize a completed share");
    }

    intervalList.get(intervalList.size() - 1).stop();
    System.out.println(nameActivity + " stops");
    running = false;

    assert (!running); //Post condition
    checkInvariant(); //Invariant
  }

  public void calculateTotalTime() { /*Calculates the active time of the task consisting of
    the sum of the active time of all of its intervals.*/

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
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }

  public JSONObject toJson() {

    checkInvariant(); //Invariant

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
    for (Interval interval : intervalList) {
      ja.put(interval.toJson());
    }

    compJson.put("intervalList", ja);

    checkInvariant(); //Invariant
    return compJson;
  } /*It is a function used to write the task data in a JSON file so that
    it can be loaded in the future.*/
}
