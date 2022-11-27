package core;

import java.util.Observable;
import java.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
* Printer is a class that contains the methods to screen print the generated tree
* to keep track of which activities are active. This class implements the "Visitor"
* interface so that the project, the task and the interval can be self-printed.
* In addition to implementing the "Visitor" pattern, it also implements the
* "Observer-Observable" pattern so that the clock can notify the printer when active
* activities need to be printed on the screen.
*/

public class Printer implements Visitor, Observer {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  private static Printer instance = null;
  private static Activity root;

  private Printer(Activity activity) {
    loggerMilestone1.debug("Entering the Printer private constructor.");
    ClockTimer.getInstance().addObserver(this);
    root = activity;
    loggerMilestone1.debug("Exiting the Printer private constructor");
  } /*This is a private constructor of the "Printer" class, and it is used to
  initialize the "root" variable (the root variable is the activity from which
  the printer will always start printing) with the value passed as a parameter.
  It is a private constructor to avoid creating more than one instance of this
  class, since we only want a single printer in the program.*/

  /**
   * This getter is used to access the only instance of the printer
   * class in order to be able to use its methods, if the instance does not exist,
   * it creates it at the moment.
   */
  public static void getInstance(Activity root) {
    loggerMilestone1.debug("Entering the getInstance method of Printer.");
    if (instance == null) {
      instance = new Printer(root);
    }
    loggerMilestone1.debug("Exiting the getInstance method of Printer.");
  }

  @Override
  public void visitProject(Project project) {
    for (int i = 0; i < project.getSizeList(); i++) {
      Object o = project.getElementFromList(i);
      ((Activity) o).acceptVisitor(instance);
    }

    if (((project.getInitialDate() != null)
        && (project.getFinalDate() != null))
        && (project.getRunning())) {
      loggerMilestone1.info("activity: " + project.getNameActivity() + "\t"
          + project.getInitialDate() + "\t" + project.getFinalDate() + "\t"
          + project.getDuration().getSeconds());
    }
  } /*This method is the method that the project uses to print its
  content when the project receives a call to its "acceptVisitor" method with a "Printer" visitor.*/

  @Override
  public void visitTask(Task task) {
    boolean printTask = false;

    for (int i = 0; i < task.getSizeList(); i++) {
      Object interval = task.getElementsFromList(i);
      ((Interval) interval).acceptVisitor(instance);

      if ((((Interval) interval).getInitialDate() != null)
          && (((Interval) interval).getFinalDate() != null)
          && (((Interval) interval).getRunning())) {
        printTask = true;
      }
    }

    if (((task.getInitialDate() != null) && (task.getFinalDate() != null)) && (printTask)) {
      loggerMilestone1.info("activity: " + task.getNameActivity() + "\t"
          + task.getInitialDate() + "\t" + task.getFinalDate() + "\t"
          + task.getDuration().getSeconds());
    }
  } /*This method is the method that the task uses to print its
  content when the project receives a call to its "acceptVisitor" method with a "Printer" visitor.*/

  @Override
  public void visitInterval(Interval interval) {
    if ((interval.getInitialDate() != null) && (interval.getFinalDate() != null)
        && (interval.getRunning())) {
      loggerMilestone1.info("interval: " + "\t" + interval.getInitialDate()
          + "\t" + interval.getFinalDate() + "\t"
          + interval.getDuration().getSeconds());
    }
  } /*This method is the method that the interval uses to print its
  content when the project receives a call to its "acceptVisitor" method with a "Printer" visitor.*/

  @Override
  public void update(Observable o, Object arg) {
    root.acceptVisitor(instance);
  } /*With the "update" method it is possible to receive the
  notifications of the observed class "ClockTimer" in order to proceed
  to print the active projects, tasks and activities.*/
}