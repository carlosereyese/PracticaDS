package webserver;

import core.*;
import java.util.List;

public class MainWebServer {
  private static final Thread threadClock = new ThreadClock();
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    threadClock.setPriority(Thread.MAX_PRIORITY);
    threadClock.start();
    final Activity root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout
    new WebServer(root);
    // start your clock
  }

  private static Activity makeTreeCourses() {
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
    return root;
  }
}