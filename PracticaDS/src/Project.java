import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project extends Component{
    private List<Component> componentList = new ArrayList<Component>();

    public Project()
    {
        //void
    }
    public Project(String nameProject, Component father)
    {
        super(nameProject, father);
    }
    public Object getIList(int i)
    {
        return componentList.get(i);
    }
    public int getSizeList() { return componentList.size(); }
    public void add(Component c)
    {
        componentList.add(c);
    }
    public void remove(Component c)
    {
        componentList.remove(c);
    }
    public Duration getActiveTime()
    {
        Duration activeTimeTotal = Duration.ofSeconds(0);

        for (int i = 0; i < componentList.size(); i++)
        {
            Duration activeIimeInterval = componentList.get(i).getActiveTime();
            activeTimeTotal = activeTimeTotal.plus(activeIimeInterval);

            if (finalDate == null)
            {
                finalDate = componentList.get(i).getFinalDate();
            }
            else if(componentList.get(i).getFinalDate() != null)
            {
                if (componentList.get(i).getFinalDate().isAfter(finalDate))
                {
                    finalDate = componentList.get(i).getFinalDate();
                }
            }
        }

        initialDate = componentList.get(0).getInitialDate();
        return activeTimeTotal;
    }
}
