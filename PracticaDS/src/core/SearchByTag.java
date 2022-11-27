package core;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
* The "SearchByTag" class is used to search for all activities that has a specific tag,
* this class implements the "Visitor".
*/

public class SearchByTag implements Visitor {
  private static final Logger loggerMilestone2 = LogManager.getLogger("Milestone 2");
  private final Activity root;
  private final List<Activity> foundActivity = new ArrayList<>();
  private String tag;

  /**
   * This is a constructor of the SearchByTag class, the utility of this
   * class is to initialize the "root" variable (the "root" variable is used to
   * indicate from which activity to start searching for activities by tag) with the
   * value that is passed by the parameter.
   */
  public SearchByTag(Activity activity) {
    loggerMilestone2.debug("Entering the SearchByTag constructor.");
    root = activity;
    loggerMilestone2.debug("Exiting the SearchByTag constructor");
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
  } /*This method is the one used by the project to check if it is the
  project searched by tag. It is also the same method used by the project
  to check if any of the activities that form it is one of the activities
  searched by tag. This method is executed when the project receives a call to its
  "acceptVisitor" method with a visitor "SearchByTag".*/

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
  } /*This method is the one used by the task to check if it is the task searched
  by tag. This method is executed when the task receives a call to its  "acceptVisitor"
  method with a visitor "SearchByTag".*/

  @Override
  public void visitInterval(Interval interval) {
    //This method is not implemented because the intervals have no tag.
  } /*This method is implemented but not used and therefore has no logic
  inside. It is implemented because the class is obliged to do it because it
  implements the visitor class, and it is one of its methods.*/

  public void resetList() {
    foundActivity.clear();
  } /*This method is used to clean up the list of activities that are generated.*/

  /**
   * This method is used so that an activity can be searched by tag.
   * What this method does is to store the name to be searched and calls
   * the "acceptVisitor" method of the root to start a recursive search.
   */
  public List<Activity> searchByTag(String tag) {
    loggerMilestone2.debug("Entering the searchByTag method.");
    this.tag = tag;
    root.acceptVisitor(this);
    loggerMilestone2.debug("Exiting the searchByTag method.");
    return foundActivity;
  }
}
