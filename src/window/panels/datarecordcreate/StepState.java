package window.panels.datarecordcreate;

public class StepState
{
    public Result result;
    public String comment;
    
    public StepState(Result result, String comment)
    {
        this.result = result;
        this.comment = comment;
    }

    public StepState(Result result)
    {
        this.result = result;
        this.comment = "";
    }

    @Override
    public String toString()
    {
        return "StepState(" + result + ", '" + comment + "'')";
    }

    public enum Result
    {
        Back,
        Incomplete,
        Done,
    }
}
