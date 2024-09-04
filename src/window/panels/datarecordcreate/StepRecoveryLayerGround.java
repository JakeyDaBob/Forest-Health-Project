package window.panels.datarecordcreate;

import javax.swing.*;
import datarecord.DataRecord;
import datarecord.DataRecord.*;
import generic.UtilMethods;


public class StepRecoveryLayerGround extends StepChooseOption
{
    public static final int LayerIndex = 0;

    public StepRecoveryLayerGround(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);
    }

    @Override
    public String GetStepName()
    {
        return "Describe " + UtilMethods.FormatCodeString(DataRecord.Layers.values()[LayerIndex].toString());
    }

    @Override
    public Object[] GetOptionObjects()
    {
        Class<?> enumClass = DataRecord.GetLayerRecovery(LayerIndex);
        var values = UtilMethods.getEnumValues(enumClass);
        return values;
    }

    @Override
    public String GetOptionName()
    {
        return UtilMethods.FormatCodeString(DataRecord.Layers.values()[LayerIndex].toString()) + " Recovery";
    }

    @Override
    public void OnComplete()
    {
        context.record.recoveryGround = RecoveryGroundLayer.values()[optionSelectPanel.getId()];
    }
}
