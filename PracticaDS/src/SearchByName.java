/*
The "SearchByName" class is used to search for an activity by name, this class implements the "Visitor".
*/
public class SearchByName implements Visitor{
    private Activity root;
    private Activity foundActivity;
    private String name;

    public SearchByName(Activity activity)
    {
        root = activity;
    }

    @Override
    public void visitProject(Project project) {
        if (project.getNameActivity().equals(name))
        {
            foundActivity = project;
        }
        else
        {
            for (int i = 0; i < project.getSizeList(); i ++)
            {
                Object o = project.getIList(i);
                ((Activity) o).acceptVisitor(this);
            }
        }
    }
    @Override
    public void visitTask(Task task) {
        if (task.getNameActivity().equals(name))
        {
            foundActivity = task;
        }
    }
    @Override
    public void visitInterval(Interval interval) {
        //This method is not implemented because the intervals have no name.
    }

    public Activity searchByName(String name) {
        this.name = name;
        root.acceptVisitor(this);
        return foundActivity;
    }
}
