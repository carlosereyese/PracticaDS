import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Component{
    protected String nameComponent;
    protected Component father;
    protected LocalDateTime initialDate;
    protected LocalDateTime finalDate;
    protected Duration duration;

    public Component() {
        initialDate = null;
        finalDate = null;
        duration = Duration.ofSeconds(0);
    }
    public Component(String nameComponent, Component father)
    {
        if (father != null)
        {
            father.add(this);
        }
        this.nameComponent = nameComponent;
        this.father = father;
        this.duration = Duration.ofSeconds(0);
    }

    public String getNameComponent()
    {
        if (nameComponent == null)
        {
            return "null";
        }
        else
        {
            return nameComponent;
        }
    }
    public LocalDateTime getInitialDate()
    {
        return initialDate;
    }
    public LocalDateTime getFinalDate()
    {
        return finalDate;
    }
    public abstract Object getIList(int i);
    public abstract int getSizeList();
    public Duration getDuration()
    {
        return duration;
    }

    public void add(Component c) {
        //void
    }
    public abstract void changeTime(LocalDateTime initialDate, LocalDateTime finalDate, Duration duration);
    public abstract void acceptVisitor(Visitor visitor);
}
