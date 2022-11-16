import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*Task is a class that inherits from Activity and contains execution intervals that indicate how long the
task has been active.*/
public class Task extends Activity{
    private final List<Interval> intervalList = new ArrayList<>();

    public Task(String nameTask, List<String> listOfTags, Activity father)
    {
        super(nameTask, listOfTags, father);
    }

    public Task(JSONObject jsonObj){
        nameActivity = jsonObj.getString("nameActivity");

        JSONArray jsonListTags = jsonObj.getJSONArray("listOfTags");
        for (int i = 0; i < jsonListTags.length(); i++){
            listOfTags.add(jsonListTags.getString(i));
        }

        if (!jsonObj.isNull("initialDate"))
            initialDate = LocalDateTime.parse(jsonObj.getString("initialDate"));
        else
            initialDate = null;

        if (!jsonObj.isNull("finalDate"))
            finalDate = LocalDateTime.parse(jsonObj.getString("finalDate"));
        else
            finalDate = null;

        duration = Duration.parse(jsonObj.getString("duration"));
        running = jsonObj.getBoolean("running");

        JSONArray jsonList = jsonObj.getJSONArray("intervalList");
        for (int i = 0; i < jsonList.length(); i++){
            intervalList.add(new Interval(jsonList.getJSONObject(i)));
        }
    } /*It is a constructor used to load data from the JSON file to
    initialize the task.*/

    public int getSizeList() { return intervalList.size(); }
    public Object getIList(int i)
    {
        return intervalList.get(i);
    }
    public boolean getRunning()
    {
        return running;
    }
    public void start() {
        System.out.println(nameActivity + " starts");
        Interval newInterval = new Interval();
        newInterval.setFather(this);
        intervalList.add(newInterval);
        running = true;
    }
    public void stop() {
        intervalList.get(intervalList.size() - 1).stop();
        System.out.println(nameActivity + " stops");
        running = false;
    }
    public void calculateTotalTime()/*Calculates the active time of the task consisting of the sum of
    the active time of all of its intervals.*/
    {
        duration = Duration.ofSeconds(0);

        for (Interval interval : intervalList) {
            duration = duration.plus(interval.getDuration());
        }
    }
    public void changeTime(LocalDateTime initialDate, LocalDateTime finalDate)
    {
        if (this.initialDate == null)
        {
            this.initialDate = initialDate;
        }
        this.finalDate = finalDate;
        this.calculateTotalTime();
        father.changeTime(initialDate, finalDate);
    }
    @Override
    public void acceptVisitor(Visitor visitor)
    {
        visitor.visitTask(this);
    }

    public JSONObject toJSON()/*It is a function used to write the task data in a JSON file so that
    it can be loaded in the future.*/
    {

        JSONObject compJSON = new JSONObject();
        compJSON.put("nameActivity", nameActivity);

        JSONArray jl = new JSONArray();
        for (String tag : listOfTags) {
            jl.put(tag);
        }
        compJSON.put("listOfTags", jl);

        String tempDate;
        if (initialDate == null)
            compJSON.put("initialDate", JSONObject.NULL);
        else{
            tempDate = initialDate.toString();
            compJSON.put("initialDate", tempDate);
        }

        if (finalDate == null)
            compJSON.put("finalDate", JSONObject.NULL);
        else{
            tempDate = finalDate.toString();
            compJSON.put("finalDate", tempDate);
        }

        compJSON.put("duration", duration.toString());
        compJSON.put("running", running);

        JSONArray ja = new JSONArray();
        for (Interval interval : intervalList) {
            ja.put(interval.toJSON());
        }

        compJSON.put("intervalList",ja);
        return compJSON;
    }
}
