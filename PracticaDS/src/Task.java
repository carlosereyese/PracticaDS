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
    public boolean getRunning()
    {
        return running;
    }
    public void start() {
        System.out.println(nameComponent + " starts");
        Interval newInterval = new Interval();
        newInterval.setFather(this);
        intervalList.add(newInterval);
        running = true;
    }
    public void stop() {
        intervalList.get(intervalList.size() - 1).stop();
        System.out.println(nameComponent + " stops");
        running = false;
    }
    public Duration calculateTotalTime()
    {
        Duration duration = Duration.ofSeconds(0);

        for (int i = 0; i < intervalList.size(); i++)
        {
            duration = duration.plus(intervalList.get(i).getDuration());
        }

        return duration;
    }
    public void changeTime(LocalDateTime initialDate, LocalDateTime finalDate)
    {
        if (this.initialDate == null)
        {
            this.initialDate = initialDate;
        }
        this.finalDate = finalDate;
        father.changeTime(initialDate, finalDate);
    }
    @Override
    public void acceptVisitor(Visitor visitor)
    {
        visitor.printTask(this);
    }

}
