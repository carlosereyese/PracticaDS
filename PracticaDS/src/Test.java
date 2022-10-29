import org.json.JSONObject;

import java.nio.channels.spi.AbstractSelectionKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;
import java.io.*;
public class Test {
    private static Test instance;
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
    public Activity testA() throws InterruptedException
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
    public Activity testB() throws InterruptedException
    {
        threadClock.setPriority(Thread.MAX_PRIORITY);
        threadClock.start();

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

        Printer.setInstance(root);

        t1.start();
        Thread.sleep(4200);
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

class ThreadClock extends Thread {
    @Override
    public void run() {
        try {
            ClockTimer.getInstance().startTimer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}