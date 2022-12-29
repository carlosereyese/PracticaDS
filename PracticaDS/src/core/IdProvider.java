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
  public void setId(int id) {
    if (this.id < id)
    {
      this.id = id;
    }
  }
  public int generateId() {
    id++;
    return id;
  }
}
