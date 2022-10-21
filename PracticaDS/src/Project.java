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
    public void changeTime(LocalDateTime initialDate, LocalDateTime finalDate, Duration duration)
    {
        if (this.initialDate == null)
        {
            this.initialDate = initialDate;
        }
        this.finalDate = finalDate;
        this.duration = this.duration.plus(duration);
    }
    @Override
    public void acceptVisitor(Visitor visitor)
    {
        visitor.printProject(this);
    }
}
