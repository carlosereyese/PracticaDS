import java.util.Observable;
import java.util.Observer;

public class Client {
    public static void main(String[] args) throws
            InterruptedException {

        ClockTimer clock = new ClockTimer();
        Thread threadClock = new threadClock(clock);
        threadClock.start();

        Project root = new Project("root", null);
        Project p1 = new Project("P1", root);
        Project p2 = new Project("P2", root);
        Task t1 = new Task("T1", root);
        Task t2 = new Task("T2", p1);
        Task t3 = new Task("T3", p2);

        Printer printer = new Printer(root);
        Thread threadPrinter = new threadPrinter(printer);
        threadPrinter.start();

        Observer intervalObserver;

        Thread.sleep(4000);
        intervalObserver = t1.start();
        clock.addObserver(intervalObserver);

        Thread.sleep(4000);
        intervalObserver = t2.start();
        clock.addObserver(intervalObserver);

        Thread.sleep(2000);
        intervalObserver = t3.start();
        clock.addObserver(intervalObserver);

        Thread.sleep(2000);
        intervalObserver = t1.stop();
        clock.deleteObserver(intervalObserver);

        Thread.sleep(2000);
        intervalObserver = t2.stop();
        clock.deleteObserver(intervalObserver);

        Thread.sleep(2000);
        intervalObserver = t3.stop();
        clock.deleteObserver(intervalObserver);
    }
}

class threadClock extends Thread {
    private ClockTimer clock;
    public threadClock(ClockTimer clock)
    {
        this.clock = clock;
    }
    @Override
    public void run() {
        clock.startTimer();
    }
}
class threadPrinter extends Thread {
    private Printer printer;
    public threadPrinter(Printer printer)
    {
        this.printer = printer;
    }
    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.printer.print();
            System.out.println("\n");
        }
    }
}
