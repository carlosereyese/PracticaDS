import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  private static final Logger loggerMilestone2 = LogManager.getLogger("Milestone 2");

  public static void main(String[] args) throws InterruptedException {
    loggerMilestone1.debug("Initiating the milestone 1 test call from the main method "
        + "of the class Client.");
    Test.getInstance().testA();
    Test.getInstance().testB();
    loggerMilestone1.debug("Finalizing the milestone 1 test call from the main method "
        + "of the class Client.");
    loggerMilestone2.debug("Initiating the milestone 2 test call from the main method "
        + "of the class Client.");
    Test.getInstance().testC();
    loggerMilestone2.debug("Finalizing the milestone 2 test call from the main method "
        + "of the class Client.");
  } /*This method is the first method that
  executes the program at startup, this method is the one that calls the different tests
  to be executed.*/
}

