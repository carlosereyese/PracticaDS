/*The test class contains all the tests necessary to check the correct functionality of the
Time Tracker program.*/
public class Test {
    private static Test instance = null;
    private Thread threadClock = new ThreadClock();
    private void Test()
    {
        //void
    }
    public static Test getInstance()
    {
        if (instance ==  null)
        {
            instance = new Test();
        }

        return instance;
    }
    public Activity testA() throws InterruptedException /*Used to test if the project-task-interval tree is
    generated correctly.*/
    {
        Project root = new Project("root", null);
        Project p1 = new Project("software design", root);
        Project p2 = new Project("software testing", root);
        Project p3 = new Project("databases", root);
        Task t1 = new Task("transportation", root);
        Project p4 = new Project("problems", p1);
        Project p5 = new Project("project time tracker", p1);
        Task t2 = new Task("first list", p4);
        Task t3 = new Task("second list", p4);
        Task t4 = new Task("read handout", p5);
        Task t5 = new Task("first milestone", p5);

        return root;
    }
    public Activity testB() throws InterruptedException /*It is used to check if the tasks are started and
    stopped correctly by printing the contents of each task every 2 seconds.*/
    {
        threadClock.setPriority(Thread.MAX_PRIORITY);
        threadClock.start();
        //ClockTimer.getInstance().startTimer();

        /*
        String jsonPath = "activitiesJSON.json";
        Project root = new Project();
        try{
            String jsonString = new String((Files.readAllBytes(Paths.get(jsonPath))));
            JSONObject jsonObj = new JSONObject(jsonString);
            root = new Project(jsonObj);
        } catch (IOException e){
            e.printStackTrace();
        }
        */

        Project root = new Project("root", null);
        Project p1 = new Project("software design", root);
        Project p2 = new Project("software testing", root);
        Project p3 = new Project("databases", root);
        Task t1 = new Task("transportation", root);
        Project p4 = new Project("problems", p1);
        Project p5 = new Project("project time tracker", p1);
        Task t2 = new Task("first list", p4);
        Task t3 = new Task("second list", p4);
        Task t4 = new Task("read handout", p5);
        Task t5 = new Task("first milestone", p5);

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

        threadClock.stop();
        System.out.println("end of test");

        /*
        try(FileWriter file = new FileWriter(jsonPath)){
            file.write(root.toJSON().toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        */

        return root;
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
