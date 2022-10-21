public class Client {
    public static void main(String[] args) throws
        InterruptedException {

        Thread threadClock = new threadClock();
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


        Thread.sleep(4000);
        t1.start();

        Thread.sleep(4000);
        t2.start();

        Thread.sleep(2000);
        t3.start();

        Thread.sleep(2000);
        t1.stop();

        Thread.sleep(2000);
        t2.stop();

        Thread.sleep(2000);
        t3.stop();
    }
}

class threadClock extends Thread {
    @Override
    public void run() {
        ClockTimer.getInstance().startTimer();
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
