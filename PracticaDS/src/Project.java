import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project extends Activity{
    private List<Activity> activityList = new ArrayList<Activity>();

    public Project(){
        nameActivity = "";
        initialDate = null;
        finalDate = null;
        running = false;
        activityList = null;
    }
    public Project(String nameProject, Activity father)
    {
        super(nameProject, father);
    }

    public Project(JSONObject jsonObj){
        nameActivity = jsonObj.getString("nameActivity");

        if (!jsonObj.isNull("initialDate")){
            String tiempo = jsonObj.getString("initialDate");
            LocalDateTime asda = LocalDateTime.parse(tiempo);
            initialDate = LocalDateTime.parse(jsonObj.getString("initialDate"));
        }
        else
            initialDate = null;

        if (!jsonObj.isNull("finalDate"))
            finalDate = LocalDateTime.parse(jsonObj.getString("finalDate"));
        else
            finalDate = null;


        running = jsonObj.getBoolean("running");

        JSONArray jsonList = jsonObj.getJSONArray("activityList");
        for (int i = 0; i < jsonList.length(); i++){
            if(jsonList.getJSONObject(i).has("intervalList")){
                activityList.add(new Task(jsonList.getJSONObject(i)));
            }else {
                activityList.add(new Project(jsonList.getJSONObject(i)));
            }
        }
    }

    public Object getIList(int i)
    {
        return activityList.get(i);
    }
    public int getSizeList() { return activityList.size(); }
    public boolean getRunning()
    {
        boolean run = false;
        int i = 0;

        while ((run == false) && (i < activityList.size()))
        {
            if (activityList.get(i).getRunning() == true)
            {
                run = true;
            }
            else
            {
                i = i + 1;
            }
        }

        running = run;
        return running;
    }

    public void add(Activity a)
    {
        activityList.add(a);
    }
    public Duration calculateTotalTime()
    {
        Duration duration = Duration.ofSeconds(0);

        for(int i = 0; i < activityList.size(); i++)
        {
            duration = duration.plus(activityList.get(i).calculateTotalTime());
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

        if(father != null)
        {
            father.changeTime(initialDate, finalDate);
        }
    }
    @Override
    public void acceptVisitor(Visitor visitor)
    {
        visitor.visitProject(this);
    }
    @Override
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
        for (int i = 0; i < activityList.size(); i++){
            ja.put(activityList.get(i).toJSON());
        }

        compJSON.put("activityList", ja);

        return compJSON;
    }
}
