import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
The "SearchByName" class is used to search for an activity by name,
this class implements the "Visitor".
*/
public class SearchByName implements Visitor {
  private final Activity root;
  private Activity foundActivity;
  private String name;
  private static Logger loggerMilestone2 = LogManager.getLogger("Milestone 2");

  public SearchByName(Activity activity) {
    loggerMilestone2.debug("Entering the SearchByName constructor.");
    root = activity;
    loggerMilestone2.debug("Exiting the SearchByName constructor");
  } /*This is a constructor of the SearchByName class, the utility of this class is to initialize the "root" variable
  (the "root" variable is used to indicate from which activity to start searching for an activity by name) with the
  value that is passed by the parameter.*/

  @Override
  public void visitProject(Project project) {
    if (project.getNameActivity().equalsIgnoreCase(name)) {
      foundActivity = project;
    } else {
      for (int i = 0; i < project.getSizeList(); i++) {
        Object o = project.getElementFromList(i);
        ((Activity) o).acceptVisitor(this);
      }
    }
  } /*This method is the one used by the project to check if it is the
  project searched by name. It is also the same method used by the project in case it is not the wanted activity to
  check if any of the activities that form it is the wanted one. This method is executed when the project receives a
  call to its "acceptVisitor" method with a visitor "SearchByName".*/

  @Override
  public void visitTask(Task task) {
    if (task.getNameActivity().equalsIgnoreCase(name)) {
      foundActivity = task;
    }
  } /*This method is the one used by the task to check if it is the
  task searched by name. This method is executed when the task receives a call to its "acceptVisitor" method with a
  visitor "SearchByName".*/

  @Override
  public void visitInterval(Interval interval) {
        //This method is not implemented because the intervals have no name.
  } /*This method is implemented but not used and therefore has no logic
  inside. It is implemented because the class is obliged to do it because it implements the visitor class and it is one
  of its methods.*/

  public Activity searchByName(String name) {
    loggerMilestone2.debug("Entering the searchByName method.");
    this.name = name;
    root.acceptVisitor(this);
    loggerMilestone2.debug("Exiting the searchByName method.");
    return foundActivity;
  } /*This method is used so that an activity can be searched by name.
  What this method does is to store the name to be searched and calls the "acceptVisitor" method of the root to start a
  recursive search.*/
}
