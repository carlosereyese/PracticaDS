package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*ThreadClock is a class derived from Thread and creates a thread in parallel
to the main thread to execute the ClockTimer.*/
class ThreadClock extends Thread {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");

  @Override
  public void run() {
    try {
      ClockTimer clock = ClockTimer.getInstance();
      clock.startTimer();
    } catch (InterruptedException e) {
      loggerMilestone1.error("Error: ", e);
      throw new RuntimeException(e);
    }
  } /*The run method is the method that has to be executed to start a second thread in order to
  run the clock in parallel to the main program.*/
}
