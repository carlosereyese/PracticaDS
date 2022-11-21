import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
The "SearchByTag" class is used to search for all activities that has a specific tag,
this class implements the "Visitor".
*/

public class SearchByTag implements Visitor {
  private final Activity root;
  private final List<Activity> foundActivity = new ArrayList<>();
  private String tag;

  public SearchByTag(Activity activity) {
    root = activity;
  }

  @Override
  public void visitProject(Project project) {
    boolean found = false;
    int i = 0;

    while ((i < project.getListOfTags().size()) && (!found)) {
      if (project.getListOfTags().get(i).equalsIgnoreCase(tag)) {
        foundActivity.add(project);
        found = true;
      } else {
        i = i + 1;
      }
    }

    for (i = 0; i < project.getSizeList(); i++) {
      Object o = project.getElementFromList(i);
      ((Activity) o).acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task task) {
    boolean found = false;
    int i = 0;

    while ((i < task.getListOfTags().size()) && (!found)) {
      if (task.getListOfTags().get(i).equalsIgnoreCase(tag)) {
        foundActivity.add(task);
        found = true;
      } else {
        i = i + 1;
      }
    }
  }

  @Override
  public void visitInterval(Interval interval) {
        //This method is not implemented because the intervals have no tag.
  }

  public List<Activity> searchByTag(String tag) {
    this.tag = tag;
    root.acceptVisitor(this);
    return foundActivity;
  }
}
