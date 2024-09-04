package window.panels.datarecordcreate;

import javax.swing.*;
import datarecord.DataRecord;


public class StepBurnSeverity extends StepChooseOption
{
    public StepBurnSeverity(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);
    }

    @Override
    public String GetStepName()
    {
        return "Describe " + GetOptionName();
    }

    @Override
    public Object[] GetOptionObjects()
    {
        return DataRecord.BurnSeverity.values();
    }

    @Override
    public String GetOptionName()
    {
        return "Burn Severity";
    }

    @Override
    public void OnComplete()
    {
        return;
    }
}
