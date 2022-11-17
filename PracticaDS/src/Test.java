import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*The test class contains all the tests necessary to check the correct functionality of the
Time Tracker program.*/
public class Test {
    private static Test instance = null;
    private final Thread threadClock = new ThreadClock();

    public static Test getInstance()
    {
        if (instance ==  null)
        {
            instance = new Test();
        }

        return instance;
    }
    public void testA()  /*Used to test if the project-task-interval tree is
    generated correctly.*/
    {
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
    }
    public void testB() throws InterruptedException /*It is used to check if the tasks are started and
    stopped correctly by printing the contents of each task every 2 seconds.*/
    {
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
        try(FileWriter file = new FileWriter(jsonPath)){
            file.write(root.toJSON().toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
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

        Activity activity1 = SearchByName.getInstance(root).searchByName("software design");
        System.out.println("ACTIVITY: " + activity1.getNameActivity());
        System.out.println("DURATION: " + activity1.getDuration());

        List<Activity> activity2 = SearchByTag.getInstance(root).searchByTag("Dart");
        System.out.println("ACTIVITY: " + activity2.get(0).getNameActivity());
        System.out.println("DURATION: " + activity2.get(0).getDuration());
    }
}

/*ThreadClock is a class derived from Thread and creates a thread in parallel to the main thread to execute the
ClockTimer.*/
class ThreadClock extends Thread {
    @Override
    public void run() {
        try {
            ClockTimer clock = ClockTimer.getInstance();
            clock.startTimer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
