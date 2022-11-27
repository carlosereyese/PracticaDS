package core;

public class IdProvider {
  private static IdProvider instance = null;
  private int id;
  private IdProvider() {
    id = -1;
  }

  public static IdProvider getInstance() {

    if (instance == null) {
      instance = new IdProvider();
    }

    return instance;
  }

  public int generateId() {
    id++;
    return id;
  }
}
