package core;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


/**
* The test class contains all the tests necessary to check the correct functionality of the
* Time Tracker program.
*/
public class Test {
  private static final Logger loggerMilestone1 = LogManager.getLogger("Milestone 1");
  private static final Logger loggerMilestone2 = LogManager.getLogger("Milestone 2");
  private static Test instance = null;
  private final Thread threadClock = new ThreadClock();

  /**
   * This method is a getter that serves to return the unique instance of
   * the test that shares all the program, in case that an instance does not
   * exist it creates it and returns it.
   */
  public static Test getInstance() {
    if (instance == null) {
      instance = new Test();
    }

    return instance;
  }

  /**
   * Used to test if the project-task-interval tree is
   * generated correctly.
   */
  public void testA() {
    loggerMilestone1.debug("Starting test A");
    Project root = new Project("root", List.of(), null);
    Project p1 = new Project("software design", List.of("java", "flutter"), root);
    Project p2 = new Project("software testing", List.of("c++", "Java", "python"), root);
    Project p3 = new Project("databases", List.of("SQL", "python", "C++"), root);
    Task t1 = new Task("transportation", List.of(), root);
    Project p4 = new Project("problems", List.of(), p1);
    Project p5 = new Project("project time tracker", List.of(), p1);
    Task t2 = new Task("first list", List.of("java"), p4);
    Task t3 = new Task("second list", List.of("Dart"), p4);
    Task t4 = new Task("read handout", List.of(), p5);
    Task t5 = new Task("first milestone", List.of("Java", "IntelliJ"), p5);
    loggerMilestone1.debug("Finishing test A");
  }

  /**
   * This method is the one that executes the second test to
   * check if the tasks are started and stopped correctly by printing the
   * contents of each task every 2 seconds.
   */
  public void testB() throws InterruptedException {
    loggerMilestone1.debug("Starting test B");
    threadClock.setPriority(Thread.MAX_PRIORITY);
    threadClock.start();

    Project root = new Project("root", List.of(), null);
    Project p1 = new Project("software design", List.of("java", "flutter"), root);
    Project p2 = new Project("software testing", List.of("c++", "Java", "python"), root);
    Project p3 = new Project("databases", List.of("SQL", "python", "C++"), root);
    Task t1 = new Task("transportation", List.of(), root);
    Project p4 = new Project("problems", List.of(), p1);
    Project p5 = new Project("project time tracker", List.of(), p1);
    Task t2 = new Task("first list", List.of("java"), p4);
    Task t3 = new Task("second list", List.of("Dart"), p4);
    Task t4 = new Task("read handout", List.of(), p5);
    Task t5 = new Task("first milestone", List.of("Java", "IntelliJ"), p5);

    Printer.getInstance(root);
    Thread.sleep(1500);

    t1.start();
    Thread.sleep(6000);
    t1.stop();

    Thread.sleep(2000);

    t2.start();
    Thread.sleep(6000);

    t3.start();
    Thread.sleep(4000);

    t2.stop();

    Thread.sleep(2000);
    t3.stop();

    Thread.sleep(2000);

    t1.start();
    Thread.sleep(4000);
    t1.stop();

    ClockTimer.getInstance().stopTimer();
    loggerMilestone1.info("end of test");

    String jsonPath = "activityJSON.json";
    try (FileWriter file = new FileWriter(jsonPath)) {
      file.write(root.toJson(200).toString());
    } catch (IOException e) {
      loggerMilestone1.warn("Error: ", e);
    }
    loggerMilestone1.debug("Finishing test B");
  }

  /**
   * This method is the one that executes the third test to check if when searching for an
   * activity by tag the search does well.
   */
  public void testC() {
    loggerMilestone2.debug("Starting test C");
    String jsonPath = "activityJSON.json";
    Project root = new Project();
    try {
      String jsonString = new String((Files.readAllBytes(Paths.get(jsonPath))));
      JSONObject jsonObj = new JSONObject(jsonString);
      root = new Project(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    }

    SearchByTag searchTag = new SearchByTag(root);

    loggerMilestone2.info("---------------- SEARCH BY TAG: java ----------------");
    List<Activity> activity1 = searchTag.searchByTag("java");
    for (Activity activity : activity1) {
      loggerMilestone2.info("ACTIVITY: " + activity.getNameActivity());
    }

    loggerMilestone2.info("---------------- SEARCH BY TAG: Java ----------------");
    searchTag.resetList();
    List<Activity> activity2 = searchTag.searchByTag("Java");
    for (Activity activity : activity2) {
      loggerMilestone2.info("ACTIVITY: " + activity.getNameActivity());
    }

    loggerMilestone2.info("--------------- SEARCH BY TAG: intellij --------------");
    searchTag.resetList();
    List<Activity> activity3 = searchTag.searchByTag("intellij");
    for (Activity activity : activity3) {
      loggerMilestone2.info("ACTIVITY: " + activity.getNameActivity());
    }

    loggerMilestone2.info("---------------- SEARCH BY TAG: c++ ------------------");
    searchTag.resetList();
    List<Activity> activity4 = searchTag.searchByTag("c++");
    for (Activity activity : activity4) {
      loggerMilestone2.info("ACTIVITY: " + activity.getNameActivity());
    }

    loggerMilestone2.info("---------------- SEARCH BY TAG: python ---------------");
    searchTag.resetList();
    List<Activity> activity5 = searchTag.searchByTag("python");
    for (Activity activity : activity5) {
      loggerMilestone2.info("ACTIVITY: " + activity.getNameActivity());
    }
    loggerMilestone2.debug("Finishing test C");

  }
}

