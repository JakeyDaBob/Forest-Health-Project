package window.panels.datarecordcreate;

import javax.swing.*;
import datarecord.DataRecord;
import datarecord.DataRecord.*;


public class StepFloweringState extends StepChooseOption
{
    public StepFloweringState(JLayeredPane parentPanel, DataContext context)
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
        return DataRecord.FloweringState.values();
    }

    @Override
    public String GetOptionName()
    {
        return "Flowering State";
    }

    @Override
    public void OnComplete()
    {
        context.record.floweringState = FloweringState.values()[optionSelectPanel.getId()];
    }
}
