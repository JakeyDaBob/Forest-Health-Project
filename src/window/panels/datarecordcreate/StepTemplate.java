package window.panels.datarecordcreate;

import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;

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
