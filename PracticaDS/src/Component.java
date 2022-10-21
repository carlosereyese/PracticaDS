import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Component{
    private String nameComponent;
    private Component father;

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
    public String getNameFather()
    {
        if (father == null)
        {
            return "null";
        }
        else
        {
            return father.getNameComponent();
        }
    }
    public Component getFather()
    {
        return father;
    }
    public LocalDateTime getInitialDate()
    {
        return initialDate;
    }
    public LocalDateTime getFinalDate()
    {
        return finalDate;
    }
    public abstract Duration getActiveTime();
    public abstract Object getIList(int i);
    public abstract int getSizeList();
    public void add(Component c) {
        //void
    }
    public void remove(Component r) {
        //void
    }


}
