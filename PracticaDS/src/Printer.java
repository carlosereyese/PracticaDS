import java.time.Duration;

public class Printer {
    Component root;

    public Printer(Component root)
    {
        this.root = root;
    }

    public void print()
    {
        if (root instanceof Task)
        {
            System.out.println("Task " + root.getNameComponent() + " child of " + root.getNameFather() + " " +
                    root.getInitialDate() + " " + root.getFinalDate() + " " + root.getActiveTime().getSeconds());

            for (int i = 0; i < root.getSizeList(); i++)
            {
                Object o = root.getIList(i);
                if ((((Interval) o).getInitialDate() != null) && (((Interval) o).getFinalDate() != null))
                {
                    Duration activeIimeInterval = Duration.between(((Interval) o).getInitialDate() , ((Interval) o).getFinalDate());
                    System.out.println("Interval child of " + root.getNameComponent() + " " +
                                    ((Interval) o).getInitialDate() + " " + ((Interval) o).getFinalDate() + " "
                                    + activeIimeInterval.getSeconds());
                }
                else
                {

                    System.out.println("Interval child of " + root.getNameComponent() + " null " + " null " + "0");
                }
            }
        }
        else
        {
            System.out.println("Project " + root.getNameComponent() + " child of " + root.getNameFather() + " " +
                    root.getInitialDate() + " " + root.getFinalDate() + " " + root.getActiveTime().getSeconds());

            for(int i = 0; i < root.getSizeList(); i++)
            {
                Object o = root.getIList(i);
                Printer printer = new Printer(((Component) o));
                printer.print();
            }
        }
    }
}
