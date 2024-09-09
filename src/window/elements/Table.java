package window.elements;

import javax.swing.*;

import generic.Vector2Int;
import window.WindowUtil;

import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;

public class Table extends JLayeredPane
{
    static final int RowHeight = 50;
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
    
    public Runnable onCellClicked;
    public Vector2Int onCellClickedPosition;

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
        tableRect.height = (RowHeight+RowGap)*10;
        setBounds(tableRect);
    }

    public void addRow(String... names)
    {
        if (names.length != columnRects.length)
        {
            throw new IllegalArgumentException("There are " + columnRects.length + " columns not " + names.length);
        }

        Row row = new Row(rowRect, columnRects, this);
        for (int i = 0; i < columnRects.length; i++)
        {
            Cell cell = row.getCell(i);
            cell.setText(names[i]);
        }

        add(row, JLayeredPane.PALETTE_LAYER);
        rows.add(row);

        updateRows();
    }

    public Row getRow(int i)
    {
        return rows.get(i);
    }

    public void removeRow(int i)
    {
        Row row = rows.get(i);

        rows.remove(i);
        remove(row);
        revalidate();

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

        repaint();
    }

    Cell getCellAtPosition(Vector2Int position)
    {
        Row row = rows.get(position.y);
        Cell cell = row.getCell(position.x);

        return cell;
    }

    Vector2Int getPositionOfCell(Cell cell)
    {
        for (int y = 0; y < rows.size(); y++)
        {
            for (int x = 0; x < columnRects.length; x++)
            {
                Vector2Int position = new Vector2Int(x,y);
                Cell cellOther = getCellAtPosition(position);

                if (cellOther.equals(cell))
                {
                    return position;
                }
            }
        }

        System.err.println("Failed to get position of cell");

        return null;
    }

    void onCellClicked(Cell cell)
    {
        Vector2Int position = getPositionOfCell(cell);

        onCellClickedPosition = position;
        System.out.println("Cell clicked: " + (onCellClickedPosition == null ? "Failed Position" : onCellClickedPosition.toString()));

        if (onCellClicked != null)
        {
            onCellClicked.run();
        }
    }

    public class Row extends JLayeredPane
    {
        Cell[] cells;
        Table tableParent;

        public Row(Rectangle rect, Rectangle[] rects, Table tableParent)
        {
            setBounds(rect);

            this.tableParent = tableParent;

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
                cells[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) 
                    {
                        if (cell.contains(e.getPoint()))
                        {
                            tableParent.onCellClicked(cell);
                        }
                    }
                });
                this.add(cells[i], JLayeredPane.PALETTE_LAYER);
            }
        }

        public Cell getCell(int i)
        {
            return cells[i];
        }

        public void setNames(String... names)
        {
            if (names.length != cells.length)
            {
                return;
            }

            for (int i = 0; i < names.length; i++)
            {
                cells[i].setText(names[i]);
            }
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
        JLabel label;

        public CellButton(Rectangle rect)
        {
            super(rect);

            label = WindowUtil.CreateLabel("text", 0, 0, getWidth(), getHeight(), Color.white);
            label.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, 18));
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
}
