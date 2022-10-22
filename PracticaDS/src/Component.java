import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Component{
    protected String nameComponent;
    protected Component father;
    protected LocalDateTime initialDate;
    protected LocalDateTime finalDate;

    public Component() {
        initialDate = null;
        finalDate = null;
    }
    public Component(String nameComponent, Component father)
    {
        if (father != null)
        {
            father.add(this);
        }
        this.nameComponent = nameComponent;
        this.father = father;
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

    public void add(Component c) {
        //void
    }
    public abstract Duration calculateTotalTime();
    public abstract void changeTime(LocalDateTime initialDate, LocalDateTime finalDate);
    public abstract void acceptVisitor(Visitor visitor);
}
