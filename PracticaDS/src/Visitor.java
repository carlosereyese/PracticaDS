public interface Visitor {
    public abstract void visitProject(Project project);
    public abstract void visitTask(Task task);

    public abstract void visitInterval(Interval interval);
}
