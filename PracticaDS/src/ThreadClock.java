/*ThreadClock is a class derived from Thread and creates a thread in parallel
to the main thread to execute the ClockTimer.*/
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
