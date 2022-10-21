public class TestB {
    private static TestB instance;
    public static TestB getInstance()
    {
        if (instance ==  null)
        {
            instance = new TestB();
        }

        return instance;
    }
    public void testB() throws InterruptedException
    {
        Thread threadClock = new threadClock();
        threadClock.start();

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
        Thread.sleep(4000);
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
    }
}

class threadClock extends Thread {
    @Override
    public void run() {
        ClockTimer.getInstance().startTimer();
    }
}
