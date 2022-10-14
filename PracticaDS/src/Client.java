public class Client {
    public static void main(String[] args) {
        Component task = new Task();
        Component project = new Project(task);
    }

}
