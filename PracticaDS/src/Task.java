import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Component{
    private List<Interval> intervalList = new ArrayList<Interval>();

    public Task(String nameTask, Component father)
    {
        super(nameTask, father);
    }

    public Task(JSONObject jsonObj){
        nameComponent = jsonObj.getString("nameComponent");

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
    }

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
        System.out.println(nameComponent + " starts");
        Interval newInterval = new Interval();
        newInterval.setFather(this);
        intervalList.add(newInterval);
        running = true;
    }
    public void stop() {
        intervalList.get(intervalList.size() - 1).stop();
        System.out.println(nameComponent + " stops");
        running = false;
    }
    public Duration calculateTotalTime()
    {
        Duration duration = Duration.ofSeconds(0);

        for (int i = 0; i < intervalList.size(); i++)
        {
            duration = duration.plus(intervalList.get(i).getDuration());
        }

        return duration;
    }
    public void changeTime(LocalDateTime initialDate, LocalDateTime finalDate)
    {
        if (this.initialDate == null)
        {
            this.initialDate = initialDate;
        }
        this.finalDate = finalDate;
        father.changeTime(initialDate, finalDate);
    }
    @Override
    public void acceptVisitor(Visitor visitor)
    {
        visitor.printTask(this);
    }

    public JSONObject toJSON(){

        JSONObject compJSON = new JSONObject();
        compJSON.put("nameComponent", nameComponent);

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
    }
}
