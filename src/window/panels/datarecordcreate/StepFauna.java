package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StepFauna extends StepPanel
{
    Rectangle[] columnRects;
    Rectangle rowRect;

    static final int RowHeight = 70;
    static final int RowGap = 20;
    static final double Dooner = 0.9;

    public StepFauna(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        SetColumnRects(getWidth()/4, ((getWidth()/4)*3), getWidth()/2, getWidth()/2);
    }

    void SetColumnRects(int... widths)
    {
        int x = 0;

        for (int i = 0; i < widths.length; i++)
        {
            int width = widths[i];
            int donger = (int)(width*Dooner);

            Rectangle r = new Rectangle(x+(donger/2), 0, width-(donger/2), RowHeight);
            columnRects[i] = r;

            x += width;
        }

        rowRect = new Rectangle(0,0,x,RowHeight);
    }

    public class Row extends JLayeredPane
    {
        public Row(Rectangle rect, Rectangle[] rs)
        {
            setBounds(rect);
        }
    }

    @Override
    public String GetStepName()
    {
        return "Fauna Interactions";
    }
}
