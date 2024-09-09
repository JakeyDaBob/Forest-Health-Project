package window.elements;

import javax.swing.*;

import window.WindowUtil;

import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;

public class Table extends JLayeredPane
{
    static final int RowHeight = 70;
    static final int RowGap = 20;
    static final double Dooner = 0.1;

    int[] widths;
    int getRowCount()
    {
        return widths.length;
    }

    Rectangle[] columnRects;
    Rectangle rowRect;
    List<Row> rows = new ArrayList<>();
    

    public Table(int... widths)
    {
        int x = 0;

        columnRects = new Rectangle[widths.length];
        for (int i = 0; i < widths.length; i++)
        {
            int width = widths[i];
            int donger = (int)(width*Dooner);

            Rectangle r = new Rectangle(x+(donger/2), 0, width-(donger/2), RowHeight);
            columnRects[i] = r;

            x += width;
        }

        rowRect = new Rectangle(0,0,x,RowHeight);

        Rectangle tableRect = new Rectangle(rowRect);
        tableRect.y = (RowHeight+RowGap)*6;
        setBounds(tableRect);

        JLabel testLabel = WindowUtil.CreateLabel("test", 0, 100, getWidth(), 50, Color.white);
        add(testLabel, JLayeredPane.MODAL_LAYER);
    }

    public void addRow(String... names)
    {
        if (names.length != columnRects.length)
        {
            throw new IllegalArgumentException("There are " + columnRects.length + " columns not " + names.length);
        }

        Row row = new Row(rowRect, columnRects);
        for (int i = 0; i < columnRects.length; i++)
        {
            Cell cell = row.getCell(i);
            cell.setText(names[i]);
        }

        add(row, JLayeredPane.PALETTE_LAYER);
        rows.add(row);

        updateRows();
    }

    void updateRows()
    {
        for (int i = 0; i < rows.size(); i++)
        {
            Rectangle rect = new Rectangle(rowRect);
            rect.y = (RowGap + RowHeight) * i;

            rows.get(i).setBounds(rect);
        }
    }

    public class Row extends JLayeredPane
    {
        Cell[] cells;

        public Row(Rectangle rect, Rectangle[] rects)
        {
            setBounds(rect);

            cells = new Cell[rects.length];
            for (int i = 0; i < rects.length; i++)
            {
                Cell cell;

                if (i == 0)
                {
                    cell = new CellButton(rects[i]);
                }
                else
                {
                    cell = new CellText(rects[i]);
                }

                cells[i] = cell;
                add(cells[i], JLayeredPane.PALETTE_LAYER);
            }
        }

        public Cell getCell(int i)
        {
            return cells[i];
        }
    }

    public abstract class Cell extends JLayeredPane
    {
        public Cell(Rectangle rect)
        {
            setBounds(rect);

            JPanel panel = new JPanel();
            panel.setBackground(Color.gray);
            panel.setBounds(0,0,getWidth(),getHeight());
            add(panel, JLayeredPane.DEFAULT_LAYER);
        }

        abstract void setText(String text);
        abstract String getText();
    }

    public class CellText extends Cell
    {
        JLabel label;

        public CellText(Rectangle rect)
        {
            super(rect);

            label = WindowUtil.CreateLabel("text", 0, 0, getWidth(), getHeight(), Color.white);
            add(label, JLayeredPane.MODAL_LAYER);
        }

        @Override
        public void setText(String text)
        {
            label.setText(text);
        }

        @Override
        public String getText()
        {
            return label.getText();
        }
    }

    public class CellButton extends Cell
    {
        JButton button;

        public CellButton(Rectangle rect)
        {
            super(rect);

            button = WindowUtil.CreateButton("button", getWidth()/2, getHeight()/2, getWidth()/2, getHeight()/2, Color.black, Color.white);
            add(button, JLayeredPane.MODAL_LAYER);
        }

        @Override
        public void setText(String text)
        {
            button.setText(text);
        }

        @Override
        public String getText()
        {
            return button.getText();
        }
    }
}
