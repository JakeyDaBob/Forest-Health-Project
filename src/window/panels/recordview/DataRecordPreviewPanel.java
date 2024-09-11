package window.panels.recordview;

import java.awt.*;
import javax.swing.*;

import window.WindowUtil;

import java.awt.event.*;

public class DataRecordPreviewPanel extends JLayeredPane
{
    static final int HeightFromTop = 100;
    static final int HeightPerRow = 50;
    static final int HeightGap = 5;

    DataRecordPreviewRowData[] datas;
    DataRecordPreviewRow[] rows;
    int rowsPerPage;
    int pageIndex;

    JButton buttonNext, buttonPrevious;
    JLabel labelPageDesc;

    public int onRowClickedDataIndex;
    public Runnable onRowClicked;

    public DataRecordPreviewPanel(Rectangle rect, int rowsPerPage)
    {
        setBounds(rect);

        this.rowsPerPage = rowsPerPage;

        rows = new DataRecordPreviewRow[rowsPerPage];
        for (int i = 0; i < rows.length; i++)
        {
            int y = HeightFromTop+((HeightPerRow+HeightGap))*i;
            Rectangle r = new Rectangle(0,y,getWidth(),HeightPerRow);
            DataRecordPreviewRow row = new DataRecordPreviewRow(r);
            
            row.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) 
                {
                    if (row.contains(e.getPoint()))
                    {
                        onRowClickedMethod(row);
                    }
                }
            });
            row.setVisible(false);
            rows[i] = row;
            add(row, JLayeredPane.MODAL_LAYER);
        }

        Color colorButton = new Color(0,0,0,128);

        buttonPrevious = WindowUtil.CreateButton("Previous", 80, 50, getWidth()/5, 50, colorButton, Color.white);
        buttonPrevious.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pagePrev();
            }
        });
        add(buttonPrevious, JLayeredPane.MODAL_LAYER);

        buttonNext = WindowUtil.CreateButton("Next", getWidth()-80, 50, getWidth()/5, 50, colorButton, Color.white);
        buttonNext.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pageNext();
            }
        });
        add(buttonNext, JLayeredPane.MODAL_LAYER);

        labelPageDesc = WindowUtil.CreateLabel("Page X of Y", (getWidth()/2)-(250/2), 25, 250, 50, Color.white);
        labelPageDesc.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 24));
        add(labelPageDesc, JLayeredPane.MODAL_LAYER);
    }

    void pagePrev()
    {
        pageIndex--;
        if (pageIndex < 0)
        {
            pageIndex = getTotalPages()-1;
        }

        setPage(pageIndex);
    }

    void pageNext()
    {
        pageIndex++;
        if (pageIndex >= getTotalPages())
        {
            pageIndex = 0;
        }

        setPage(pageIndex);
    }

    int getTotalPages()
    {
        if (datas == null)
        {
            return 0;
        }
        return (datas.length/rowsPerPage)+1;
    }

    public void setDataRecordPreviews(DataRecordPreviewRowData[] datas)
    {
        this.datas = datas;
    }

    public void setPage(int pageIndex)
    {
        this.pageIndex = pageIndex;

        if (datas == null)
        {
            return;
        }

        int startIndex = rowsPerPage*pageIndex;
        int endIndex = startIndex+rowsPerPage;

        for (int i = startIndex; i < endIndex; i++)
        {
            boolean valid = i >= 0 && i < datas.length;

            if (valid)
            {
                DataRecordPreviewRow row = rows[i-startIndex];
                DataRecordPreviewRowData data = datas[i];

                row.setTitle(data.title, Color.white);
                row.setDesc(data.desc, Color.white);
            }

            rows[i-startIndex].setVisible(valid);
        }

        labelPageDesc.setText("Page " + (pageIndex+1) + "/" + getTotalPages() + " (" + (startIndex+1) + "-" + (endIndex) + ")");
    }

    int getIndexOfRow(DataRecordPreviewRow row)
    {
        for (int i = 0; i < rows.length; i++)
        {
            if (rows[i].equals(row))
            {
                return i;
            }
        }

        return -1;
    }

    int getDataIndexOfRow(int rowIndex)
    {
        int dataIndex = (pageIndex*rowsPerPage)+rowIndex;
        return dataIndex;
    }

    void onRowClickedMethod(DataRecordPreviewRow row)
    {
        int rowIndex = getIndexOfRow(row);

        if (rowIndex == -1)
        {
            System.out.println("Failed to get index of row");
            return;
        }

        int dataIndex = getDataIndexOfRow(rowIndex);

        onRowClickedDataIndex = dataIndex;
        onRowClicked.run();
    }

    public DataRecordPreviewRow getRow(int rowIndex)
    {
        return rows[rowIndex];
    }

    public DataRecordPreviewRowData getData(int dataIndex)
    {
        return datas[dataIndex];
    }
}
