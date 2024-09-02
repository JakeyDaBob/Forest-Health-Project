package window.panels.datarecordcreate;

import javax.swing.JLayeredPane;

public class StepTemplate extends StepPanel
{
    public StepTemplate(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);
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
