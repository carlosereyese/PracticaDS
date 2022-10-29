import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Component{
    protected String nameComponent;
    protected Component father;
    protected LocalDateTime initialDate;
    protected LocalDateTime finalDate;
    protected boolean running;

    public Component() {
        initialDate = null;
        finalDate = null;
        running = false;
    }
    public Component(String nameComponent, Component father)
    {
        if (father != null)
        {
            father.add(this);
        }
        this.nameComponent = nameComponent;
        this.father = father;
        running =  false;
    }

    public Component(JSONObject jsonObj){

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
    public abstract boolean getRunning();
    public abstract Object getIList(int i);
    public abstract int getSizeList();


    public void add(Component c) {
        //void
    }
    public abstract Duration calculateTotalTime();
    public abstract void changeTime(LocalDateTime initialDate, LocalDateTime finalDate);
    public abstract void acceptVisitor(Visitor visitor);

    public abstract JSONObject toJSON();
}
