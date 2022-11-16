import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*Project is a class that inherits from Activity and that contains more projects and tasks (it acts as a container),
this means a project is a set of projects and tasks that have been created in the application.*/
public class Project extends Activity{
    private List<Activity> activityList = new ArrayList<>();

    public Project(){
        nameActivity = "";
        initialDate = null;
        finalDate = null;
        running = false;
        activityList = null;
    }
    public Project(String nameTask, List<String> listOfTags, Activity father)
    {
        super(nameTask, listOfTags, father);
    }

    public Project(JSONObject jsonObj){
        nameActivity = jsonObj.getString("nameActivity");

        JSONArray jsonListTags = jsonObj.getJSONArray("listOfTags");
        for (int i = 0; i < jsonListTags.length(); i++){
            listOfTags.add(jsonListTags.getString(i));
        }

        if (!jsonObj.isNull("initialDate")){
            initialDate = LocalDateTime.parse(jsonObj.getString("initialDate"));
        }
        else
            initialDate = null;

        if (!jsonObj.isNull("finalDate"))
            finalDate = LocalDateTime.parse(jsonObj.getString("finalDate"));
        else
            finalDate = null;

        duration = Duration.parse(jsonObj.getString("duration"));
        running = jsonObj.getBoolean("running");

        JSONArray jsonList = jsonObj.getJSONArray("activityList");
        for (int i = 0; i < jsonList.length(); i++){
            if(jsonList.getJSONObject(i).has("intervalList")){
                activityList.add(new Task(jsonList.getJSONObject(i)));
            }else {
                activityList.add(new Project(jsonList.getJSONObject(i)));
            }
        }
    } /*It is a constructor used to load data from the JSON file to
    initialize the project.*/

    public Object getIList(int i)
    {
        return activityList.get(i);
    }
    public int getSizeList() { return activityList.size(); }
    public boolean getRunning()
    {
        boolean run = false;
        int i = 0;

        while ((!run) && (i < activityList.size()))
        {
            if (activityList.get(i).getRunning())
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
    public void calculateTotalTime() /*Calculates the active time of the project consisting of the sum of
    the active time of all its children (projects+tasks).*/
    {
        duration = Duration.ofSeconds(0);

        for (Activity activity : activityList) {
            duration = duration.plus(activity.getDuration());
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
        for (Activity activity : activityList) {
            ja.put(activity.toJSON());
        }
        compJSON.put("activityList", ja);

        return compJSON;
    } /*It is a function used to write the project data in a JSON file so that
    it can be loaded in the future.*/
}
