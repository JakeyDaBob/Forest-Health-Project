package window.panels.datarecordcreate;

public class StepState
{
    public boolean done;
    public String comment;
    
    public StepState(boolean done, String comment)
    {
        this.done = done;
        this.comment = comment;
    }

    public StepState(boolean done)
    {
        this.done = done;
        this.comment = "";
    }

    @Override
    public String toString()
    {
        return "StepState(" + done + ", '" + comment + "'')";
    }
}
