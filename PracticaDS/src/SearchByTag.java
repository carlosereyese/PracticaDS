import java.util.ArrayList;
import java.util.List;

public class SearchByTag implements Visitor{
    private static SearchByTag instance = null;
    private static Activity root;
    private static List<Activity> foundActivity = new ArrayList<>();
    private static String tag;

    private SearchByTag(Activity activity)
    {
        root = activity;
    }
    public static SearchByTag getInstance(Activity root)
    {
        if (instance ==  null)
        {
            instance = new SearchByTag(root);
        }
        return instance;
    }

    @Override
    public void visitProject(Project project) {
        boolean found = false;
        int i = 0;

        while ((i < project.getListOfTags().size()) && (!found)) {
            if (project.getListOfTags().get(i) == tag)
            {
                foundActivity.add(project);
                found = true;
            }
            else
            {
                i = i + 1;
            }
        }

        if (!found) {
            for (i = 0; i < project.getSizeList(); i++) {
                Object o = project.getIList(i);
                ((Activity) o).acceptVisitor(instance);
            }
        }
    }
    @Override
    public void visitTask(Task task) {
        boolean found = false;
        int i = 0;

        while ((i < task.getListOfTags().size()) && (!found))
        {
            if (task.getListOfTags().get(i) == tag)
            {
                foundActivity.add(task);
                found = true;
            }
            else
            {
                i = i + 1;
            }
        }
    }
    @Override
    public void visitInterval(Interval interval) {
        //This method is not implemented because the intervals have no tag.
    }

    public List<Activity> searchByTag(String tag)
    {
        this.tag = tag;
        root.acceptVisitor(instance);
        return foundActivity;
    }
}
