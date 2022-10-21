import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Component{
    private List<Interval> intervalList = new ArrayList<Interval>();

    public Task(String nameTask, Component father)
    {
        super(nameTask, father);
    }

    public int getSizeList() { return intervalList.size(); }
    public Object getIList(int i)
    {
        return intervalList.get(i);
    }

    public void start() {
        System.out.println(nameComponent + " starts");
        Interval newInterval = new Interval();
        newInterval.setFather(this);
        intervalList.add(newInterval);
    }
    public void stop() {
        intervalList.get(intervalList.size() - 1).stop();
        System.out.println(nameComponent + " stops");
    }
    public void changeTime(LocalDateTime initialDate, LocalDateTime finalDate, Duration duration)
    {
        if (this.initialDate == null)
        {
            this.initialDate = initialDate;
        }
        this.finalDate = finalDate;
        this.duration = this.duration.plus(duration);
        father.changeTime(initialDate, finalDate, duration);
    }
    @Override
    public void acceptVisitor(Visitor visitor)
    {
        visitor.printTask(this);
    }

}
