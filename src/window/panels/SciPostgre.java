package window.panels;

import java.awt.*;
import javax.swing.*;

import org.json.JSONObject;

import application.*;
import datarecord.*;
import graphics.DrawImage;

import java.awt.event.*;

import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

public class SciPostgre extends JLayeredPane
{
    JLabel labelStatus;
    JButton buttonDownload;
    DataRecordPreview[] dataRecordPreviews;
    int dataRecordDownloadIndex;

    public SciPostgre(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        DrawImage image = new DrawImage(FileSystem.Resources.GetImage("sample/pgsqlui.jpg"));
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(Color.black);
        backgroundPanel.setBounds(0,0,getWidth(),getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel("PostgreSQL Interface", 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 60;
        JButton buttonBack = WindowUtil.CreateButton("Back", getWidth()/2, getHeight()-150, (int)(getWidth()*0.3f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.SciDownload);
            }
        });
        add(buttonBack, JLayeredPane.MODAL_LAYER);

    }
}
