/*
The "visitor" interface is an interface that has the necessary methods so that project, task and interval can use the
different functionalities that they inherit from it.
This interface implements the Visitor pattern, so that the project, the task and the interval can receive a visitor of
this class and be able to call the methods they are interested in.
*/
public interface Visitor {
    void visitProject(Project project);
    void visitTask(Task task);
    void visitInterval(Interval interval);
}
