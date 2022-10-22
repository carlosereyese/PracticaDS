import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project extends Component{
    private List<Component> componentList = new ArrayList<Component>();

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
    public Duration calculateTotalTime()
    {
        Duration duration = Duration.ofSeconds(0);

        for(int i = 0; i < componentList.size(); i++)
        {
            duration = duration.plus(componentList.get(i).calculateTotalTime());
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
        visitor.printProject(this);
    }
}
