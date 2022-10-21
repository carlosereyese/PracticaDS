import java.time.Duration;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor, Observer
{
    private static Visitor instance;
    private static Component root;

    private Printer(Component component)
    {
        ClockTimer.getInstance().addObserver(this);
        root = component;
    }

    @Override
    public void printProject(Project project)
    {
        for (int i = 0; i < project.getSizeList(); i ++)
        {
            Object o = project.getIList(i);
            ((Component) o).acceptVisitor(instance);
        }
        if ((project.getInitialDate() != null) && (project.getFinalDate() != null))
        {
            System.out.println("activity: " + project.getNameComponent() + " " + project.getInitialDate()
                    + " " + project.getFinalDate() + " " + project.getDuration().getSeconds());
        }
    }
    @Override
    public void printTask(Task task)
    {
        for (int i = 0; i < task.getSizeList(); i++)
        {
            Object o = task.getIList(i);
            if ((((Interval) o).getInitialDate() != null) && (((Interval) o).getFinalDate() != null))
            {
                Duration activeIimeInterval = Duration.between(((Interval) o).getInitialDate() , ((Interval) o).getFinalDate());
                System.out.println("interval: " + root.getNameComponent() + " " +
                        ((Interval) o).getInitialDate() + " " + ((Interval) o).getFinalDate() + " "
                        + activeIimeInterval.getSeconds());
            }
        }

        if ((task.getInitialDate() != null) && (task.getFinalDate() != null))
        {
            System.out.println("activity: " + task.getNameComponent() + " " + task.getInitialDate() + " "
                    + task.getFinalDate() + " " + task.getDuration().getSeconds());
        }
    }
    public static void setInstance(Component root)
    {
        if (instance ==  null)
        {
            instance = new Printer(root);
        }
    }
    @Override
    public void update(Observable o, Object arg)
    {
        root.acceptVisitor(instance);
    }
}