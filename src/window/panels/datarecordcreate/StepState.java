package window.panels.datarecordcreate;

public class StepState
{
    public boolean done;
    public String comment;
    
    public StepState(boolean complete, String comment)
    {
        this.done = complete;
        this.comment = comment;
    }
}
