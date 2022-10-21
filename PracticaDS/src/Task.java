import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
public class Task extends Component{
    private List<Interval> intervalList = new ArrayList<Interval>();
    public Task() {
        //void
    }
    public Task(String nameTask, Component father)
    {
        super(nameTask, father);
    }

    public Object getIList(int i)
    {
        return intervalList.get(i);
    }
    public int getSizeList() { return intervalList.size(); }

    public void start() {

        Interval newInterval = new Interval();
        intervalList.add(newInterval);
    }
    public void stop() {
        intervalList.get(intervalList.size() - 1).stop();
    }
    public Duration getActiveTime() {
        Duration activeTimeTotal = Duration.ofSeconds(0);

        for (int i = 0; i < intervalList.size(); i++)
        {
            if ((intervalList.get(i).getInitialDate() != null) && (intervalList.get(i).getFinalDate() != null))
            {
                Duration activeIimeInterval = Duration.between(intervalList.get(i).getInitialDate(), intervalList.get(i).getFinalDate());
                activeTimeTotal = activeTimeTotal.plus(activeIimeInterval);
            }

            if (finalDate == null)
            {
                finalDate = intervalList.get(i).getFinalDate();
            }
            else
            {
                if (intervalList.get(i).getFinalDate().isAfter(finalDate))
                {
                    finalDate = intervalList.get(i).getFinalDate();
                }
            }
        }

        if (intervalList.isEmpty() == false)
        {
            initialDate = intervalList.get(0).getInitialDate();
        }

        return activeTimeTotal;
    }
}
