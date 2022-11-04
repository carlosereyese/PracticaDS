import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*Task is a class that inherits from Activity and contains execution intervals that indicate how long the
task has been active.*/
public class Task extends Activity{
    private List<Interval> intervalList = new ArrayList<Interval>();

    public Task(String nameTask, Activity father)
    {
        super(nameTask, father);
    }

    public Task(JSONObject jsonObj){
        nameActivity = jsonObj.getString("nameActivity");

        if (!jsonObj.isNull("initialDate"))
            initialDate = LocalDateTime.parse(jsonObj.getString("initialDate"));
        else
            initialDate = null;

        if (!jsonObj.isNull("finalDate"))
            finalDate = LocalDateTime.parse(jsonObj.getString("finalDate"));
        else
            finalDate = null;

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

        for (int i = 0; i < intervalList.size(); i++)
        {
            duration = duration.plus(intervalList.get(i).getDuration());
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

    public JSONObject toJSON(){

        JSONObject compJSON = new JSONObject();
        compJSON.put("nameActivity", nameActivity);

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

        compJSON.put("running", running);

        JSONArray ja = new JSONArray();
        for (int i = 0; i < intervalList.size(); i++){
            ja.put(intervalList.get(i).toJSON());
        }

        compJSON.put("intervalList",ja);
        return compJSON;
    } /*It is a function used to write the task data in a JSON file so that
    it can be loaded in the future.*/
}
