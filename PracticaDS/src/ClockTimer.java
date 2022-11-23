import java.time.LocalDateTime;
import java.util.Observable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/*
The "ClockTimer" class is the class responsible for reporting the time
every 2 seconds for the interval to update its time and that of its
parents as well as for notifying the printer to display the active
activities on the screen. This class applies the "Observer-Observable"
pattern, because this class is observed by intervals and printers so that
it can notify them of the time update. It also applies the "singleton"
pattern because we do not want there to be more than one instance of this
class, and that a single instance is used by all classes to ensure that
the printer and activities are synchronized.
 */

public class ClockTimer extends Observable {
  private static ClockTimer instance = null;
  private static Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  private LocalDateTime dateTime;
  private boolean loop = true;

  private ClockTimer() {
    loggerMilestone1.debug("Entering the ClockTimer private constructor.");
    this.dateTime = null;
    loggerMilestone1.trace("The default value of the dateTime attribute is: {}", dateTime);
    loggerMilestone1.debug("Exiting the ClockTimer private builder");
  } /*The ClockTimer is a private constructor used to create an instance of this class. It is
  private to prevent anyone outside the class from creating more than one instance, since the
  idea is that there is only one clock for the whole program.*/

  public static ClockTimer getInstance() {
    loggerMilestone1.debug("Entering the getInstance method of ClockTimer.");
    if (instance == null) {
      instance = new ClockTimer();
    }

    loggerMilestone1.debug("Exiting the getInstance method of ClockTimer.");
    return instance;
  } /*This method is a getter that serves to return the unique instance of
  the clock that shares all the program, in case that an instance does not
  exist it creates it and returns it.*/

  private void tick() {
    loggerMilestone1.debug("Entering the tick method of ClockTimer.");
    this.dateTime = LocalDateTime.now();
    setChanged();
    notifyObservers(this.dateTime);
    loggerMilestone1.debug("Exiting the tick method of ClockTimer.");
  } /*The "tick" method is the method that is called every 2 seconds to notify the current
    time to all observers.*/

  public void startTimer() throws InterruptedException {
    loggerMilestone1.debug("Entering the startTimer method of ClockTimer.");
    loggerMilestone1.trace("The value of the loop attribute is: {}", loop);
    while (loop) {
      tick();
      Thread.sleep(2000);
    }
    loggerMilestone1.trace("The value of the loop attribute is: {}", loop);
    loggerMilestone1.debug("Exiting the startTimer method of ClockTimer.");
  } /*The "startTimer" method is the method used to start
    the execution of the clock to notify the observers (intervals and printer).*/

  public void stopTimer() {
    loggerMilestone1.debug("Entering the stopTimer method of ClockTimer.");
    loop = false;
    loggerMilestone1.trace("The value of the loop attribute in ClockTimer is: {}", loop);
    loggerMilestone1.debug("Exiting the stopTimer method of ClockTimer.");
  } /*The "stopTimer" method is the method used to stop the execution of the clock
  and therefore stop the execution of the entire program.*/
}