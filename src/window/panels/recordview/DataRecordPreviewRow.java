package window.panels.recordview;

import java.awt.*;
import javax.swing.*;

import graphics.ColoredPanel;
import window.WindowUtil;

import java.awt.event.*;

public class DataRecordPreviewRow extends JLayeredPane
{
    JLabel title, desc;
    ColoredPanel bgPanel;

    public DataRecordPreviewRow(Rectangle rect)
    {
        setBounds(rect);

        bgPanel = new ColoredPanel(new Color(0,0,0,128));
        bgPanel.setBounds(0,0,getWidth(),getHeight());
        add(bgPanel, JLayeredPane.DEFAULT_LAYER);
        
        title = WindowUtil.CreateLabel("", 30, 0, getWidth()/2, getHeight(), Color.white);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        add(title, JLayeredPane.MODAL_LAYER);

        desc =  WindowUtil.CreateLabel("", (getWidth()/2)-30, 0, getWidth()/2, getHeight(), Color.white);
        desc.setHorizontalAlignment(SwingConstants.RIGHT);
        add(desc, JLayeredPane.MODAL_LAYER);
    }

    public void setTitle(String str, Color color)
    {
        title.setText(str);
        title.setForeground(color);
    }

    public void setDesc(String str, Color color)
    {
        desc.setText(str);
        desc.setForeground(color);
    }
}
