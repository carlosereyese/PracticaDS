import java.util.Observer;

public interface Visitor {
    public abstract void printProject(Project project);
    public abstract void printTask(Task task);
}
