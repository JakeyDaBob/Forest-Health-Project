package window.panels.datarecordcreate;

import javax.swing.JLayeredPane;

public class StepTemplate extends StepPanel
{
    public StepTemplate(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);
    }

    @Override
    public String GetStepName()
    {
        return "Template Step";
    }
}
