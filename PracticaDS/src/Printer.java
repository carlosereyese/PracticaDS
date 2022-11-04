import java.util.Observable;
import java.util.Observer;
/*Printer is a class that contains the methods to screen print the generated tree to keep track of which activities are
active.*/
public class Printer implements Visitor, Observer
{
    private static Visitor instance = null;;
    private static Activity root;

    private Printer(Activity activity)
    {
        ClockTimer.getInstance().addObserver(this);
        root = activity;
    }
    public static Visitor getInstance(Activity root)
    {
        if (instance ==  null)
        {
            instance = new Printer(root);
        }
        return instance;
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
                    + "\t" + project.getFinalDate() + "\t" + project.getDuration().getSeconds());
        }
    }
    @Override
    public void visitTask(Task task)
    {
        boolean printTask = false;

        for (int i = 0; i < task.getSizeList(); i++)
        {
            Object interval = task.getIList(i);
            ((Interval) interval).acceptVisitor(instance);

            if ((((Interval) interval).getInitialDate() != null) && (((Interval) interval).getFinalDate() != null) && (((Interval) interval).getRunning() == true))
            {
                printTask = true;
            }
        }

        if (((task.getInitialDate() != null) && (task.getFinalDate() != null)) && (printTask == true))
        {
            System.out.println("activity: " + task.getNameActivity() + "\t" + task.getInitialDate() + "\t"
                    + task.getFinalDate() + "\t" + task.getDuration().getSeconds());
        }
    }
    @Override
    public void visitInterval(Interval interval)
    {
        if ((interval.getInitialDate() != null) && (interval.getFinalDate() != null) && (interval.getRunning() == true))
        {
            System.out.println("interval: " + "\t" + interval.getInitialDate() + "\t" + interval.getFinalDate() + "\t"
                    + interval.getDuration().getSeconds());
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        root.acceptVisitor(instance);
    }
}