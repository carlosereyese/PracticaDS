import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
public class Task extends Component{
    private List<Interval> intervalList = new ArrayList<Interval>();

    public Task()
    {
        //void
    }
    public List<Interval> getIntervalList()
    {
        return intervalList;
    }
    public Duration getActiveTime()
    {
        Duration activeTimeTotal = Duration.ofSeconds(0);
        for (int i = 0; i < intervalList.size(); i++)
        {
            Duration activeIimeInterval = Duration.between(intervalList.get(i).getInitialDate(), intervalList.get(i).getFinalDate());
            activeTimeTotal = activeTimeTotal.plus(activeIimeInterval);
        }

        return activeTimeTotal;
    }
}
