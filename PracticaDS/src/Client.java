public class Client {
  public static void main(String[] args) throws InterruptedException {
    Test.getInstance().testA();
    Test.getInstance().testB();
    Test.getInstance().testC();
  } /*This method is the first method that executes the program at startup, this method is the one that calls the
  different tests to be executed.*/
}

