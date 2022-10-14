import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Project extends Component{
    private LocalDateTime initialDate = null;
    private LocalDateTime finalDate = null;
    private List<Component> componentList;

    public Project()
    {
        //void
    }
    public Project(Component c)
    {
        this.add(c);
    }
    public LocalDateTime getInitialDate()
    {
        return initialDate;
    }
    public LocalDateTime getFinalDate()
    {
        return finalDate;
    }
    public List<Component> getComponentList()
    {
        return componentList;
    }
    public void add(Component c)
    {
        componentList.add(c);
    }
    public void remove(Component c)
    {
        componentList.remove(c);
    }
}
