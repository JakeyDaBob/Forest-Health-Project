package window.panels.datarecordcreate;

import javax.swing.JPanel;

public class StepTemplate extends Step
{
    public StepTemplate(JPanel panel, DataContext context)
    {
        super(panel, context);
    }

    @Override
    public String GetName()
    {
        return "Template Step";
    } 

    @Override
    public StepState GetState()
    {
        return new StepState(false, "Developer is a sigma");
    }
}
