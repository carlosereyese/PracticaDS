import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONObject;


/*The test class contains all the tests necessary to check the correct functionality of the
Time Tracker program.*/
public class Test {
    private static Test instance = null;
    private final Thread threadClock = new ThreadClock();

    public static Test getInstance() {
        if (instance ==  null) {
            instance = new Test();
        }

        return instance;
    } /*This method is a getter that serves to return the unique instance of
    the test that shares all the program, in case that an instance does not exist it creates it and returns it.*/

    public void testA() {

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
    } /*Used to test if the project-task-interval tree is
    generated correctly.*/

    public void testB() throws InterruptedException { /*It is used to check if
    the tasks are started and stopped correctly by printing the contents of
    each task every 2 seconds.*/

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
        System.out.println("end of test");

        String jsonPath = "activityJSON.json";
        try (FileWriter file = new FileWriter(jsonPath)) {
            file.write(root.toJson().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } /*This method is the one that executes the second test to
    check if the execution of the activities starts and stops well.*/

    public void testC() {
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

        System.out.println("---------------- SEARCH BY TAG: java ----------------");
        List<Activity> activity1 = searchTag.searchByTag("java");
        for (int i = 0; i < activity1.size(); i++)
        {
            System.out.println("ACTIVITY: " + activity1.get(i).getNameActivity());
        }

        System.out.println("---------------- SEARCH BY TAG: Java ----------------");
        searchTag.resetList();
        List<Activity> activity2 = searchTag.searchByTag("Java");
        for (int i = 0; i < activity2.size(); i++)
        {
            System.out.println("ACTIVITY: " + activity2.get(i).getNameActivity());
        }

        System.out.println("--------------- SEARCH BY TAG: intellij --------------");
        searchTag.resetList();
        List<Activity> activity3 = searchTag.searchByTag("intellij");
        for (int i = 0; i < activity3.size(); i++)
        {
            System.out.println("ACTIVITY: " + activity3.get(i).getNameActivity());
        }

        System.out.println("---------------- SEARCH BY TAG: c++ ------------------");
        searchTag.resetList();
        List<Activity> activity4 = searchTag.searchByTag("c++");
        for (int i = 0; i < activity4.size(); i++)
        {
            System.out.println("ACTIVITY: " + activity4.get(i).getNameActivity());
        }

        System.out.println("---------------- SEARCH BY TAG: python ---------------");
        searchTag.resetList();
        List<Activity> activity5 = searchTag.searchByTag("python");
        for (int i = 0; i < activity5.size(); i++)
        {
            System.out.println("ACTIVITY: " + activity5.get(i).getNameActivity());
        }

    } /*This method is the one that executes the third test to check if when searching for an
    activity by tag the search does well.*/
}

