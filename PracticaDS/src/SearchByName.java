/*
The "SearchByName" class is used to search for an activity by name, this class implements the "Visitor".
*/
public class SearchByName implements Visitor{
    private static SearchByName instance = null;
    private static Activity root;
    private static Activity foundActivity;
    private static String name;

    private SearchByName(Activity activity)
    {
        root = activity;
    }
    public static SearchByName getInstance(Activity root)
    {
        if (instance ==  null)
        {
            instance = new SearchByName(root);
        }
        return instance;
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
                ((Activity) o).acceptVisitor(instance);
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
        root.acceptVisitor(instance);
        return foundActivity;
    }
}
