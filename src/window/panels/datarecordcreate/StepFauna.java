package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import window.elements.*;

public class StepFauna extends StepPanel
{
    Table table;

    public StepFauna(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        table = new Table(getWidth()/8, (getWidth()/8)*3, getWidth()/4, getWidth()/4);
        table.setLocation(0, 100);
        add(table, JLayeredPane.PALETTE_LAYER);

        table.addRow("Add", "Data", "Data Type", "Interaction Type");
        table.addRow("sigma", "sigma", "sigma", "sigma");
    }

    @Override
    public String GetStepName()
    {
        return "Fauna Interactions";
    }
}
