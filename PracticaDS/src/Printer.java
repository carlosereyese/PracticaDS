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

        if (((project.getInitialDate() != null) && (project.getFinalDate() != null)) && (project.getRunning() == true))
        {
            System.out.println("activity: " + project.getNameComponent() + "\t" + project.getInitialDate()
                    + "\t" + project.getFinalDate() + "\t" + project.calculateTotalTime().getSeconds());
        }
    }
    @Override
    public void printTask(Task task)
    {
        boolean printTask = false;

        for (int i = 0; i < task.getSizeList(); i++)
        {
            Object o = task.getIList(i);
            if ((((Interval) o).getInitialDate() != null) && (((Interval) o).getFinalDate() != null) && (((Interval) o).getRunning() == true))
            {
                System.out.println("interval: " + "\t" + ((Interval) o).getInitialDate() + "\t" + ((Interval) o).getFinalDate() + "\t"
                        + ((Interval) o).getDuration().getSeconds());
                printTask = true;
            }
        }

        if (((task.getInitialDate() != null) && (task.getFinalDate() != null)) && (printTask == true))
        {
            System.out.println("activity: " + task.getNameComponent() + "\t" + task.getInitialDate() + "\t"
                    + task.getFinalDate() + "\t" + task.calculateTotalTime().getSeconds());
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