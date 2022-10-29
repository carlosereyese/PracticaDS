import java.time.Duration;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor, Observer
{
    private static Visitor instance;
    private static Activity root;

    private Printer(Activity activity)
    {
        ClockTimer.getInstance().addObserver(this);
        root = activity;
    }
    public static void setInstance(Activity root)
    {
        if (instance ==  null)
        {
            instance = new Printer(root);
        }
    }

    @Override
    public void visitProject(Project project)
    {
        for (int i = 0; i < project.getSizeList(); i ++)
        {
            Object o = project.getIList(i);
            ((Activity) o).acceptVisitor(instance);
        }

        if (((project.getInitialDate() != null) && (project.getFinalDate() != null)) && (project.getRunning() == true))
        {
            System.out.println("activity: " + project.getNameActivity() + "\t" + project.getInitialDate()
                    + "\t" + project.getFinalDate() + "\t" + project.calculateTotalTime().getSeconds());
        }
    }
    @Override
    public void visitTask(Task task)
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
            System.out.println("activity: " + task.getNameActivity() + "\t" + task.getInitialDate() + "\t"
                    + task.getFinalDate() + "\t" + task.calculateTotalTime().getSeconds());
        }
    }
    @Override
    public void update(Observable o, Object arg)
    {
        root.acceptVisitor(instance);
    }
}