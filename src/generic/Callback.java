package generic;

import java.util.List;
import java.util.ArrayList;

public class Callback
{
    private List<Runnable> runnables = new ArrayList<>();

    public void Add(Runnable runnable)
    {
        runnables.add(runnable);
    }

    public void Remove(Runnable runnable)
    {
        int indexToRemove = -1;
        for (int i = 0; i < runnables.size(); i++)
        {
            if (runnable.equals(runnables.get(i)))
            {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1)
        {
            runnables.remove(indexToRemove);
        }
    }

    public void Invoke()
    {
        for (Runnable runnable : runnables)
        {
            runnable.run();
        }
    }
}
