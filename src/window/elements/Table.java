package window.elements;

import javax.swing.*;

import window.WindowUtil;

import java.awt.*;
import java.awt.event.*;

public class Table extends JLayeredPane
{
    static final int RowHeight = 70;
    static final int RowGap = 20;
    static final double Dooner = 0.9;

    int[] widths;
    int getRowCount()
    {
        return widths.length;
    }

    Rectangle[] columnRects;
    Rectangle rowRect;

    public Table(int... widths)
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
        JPanel[] panels;

        public Row(Rectangle rect, Rectangle[] rects)
        {
            setBounds(rect);

            panels = new JPanel[rects.length];
            for (int i = 0; i < rects.length; i++)
            {
                
            }
        }

        public JPanel getPanel(int i)
        {
            return panels[i];
        }
    }

    public class Cell extends JLayeredPane
    {
        JLabel label;

        public Cell(Rectangle rect, String text)
        {
            setBounds(rect);

            label = new JLabel(text);
            label.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 16));
            label.setForeground(Color.white);

            add(label, JLayeredPane.MODAL_LAYER);
        }
    }
}
