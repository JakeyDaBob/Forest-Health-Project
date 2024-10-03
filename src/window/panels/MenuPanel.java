package window.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import application.AppInfo;
import application.AppMode;
import graphics.ColoredPanel;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

public class MenuPanel extends JLayeredPane
{
    Timer timer;
    int progress;

    public MenuPanel(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        DrawImage image = new DrawImage(AppInfo.GetBackgroundImage());
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        ColoredPanel box = new ColoredPanel(new Color(0,0,0,100));
        box.setBounds(0,0,getWidth(),getHeight());
        box.setVisible(true);
        add(box, JLayeredPane.PALETTE_LAYER);

        Color buttonBackColor = new Color(0,0,0,128);

        JLabel labelTitle = WindowUtil.CreateLabel(AppInfo.Name, 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        JLabel labelMode = WindowUtil.CreateLabel(AppInfo.Mode + " Mode", 0, getHeight()/16+40, getWidth(), 50, Color.white);
        labelMode.setFont(new Font(WindowUtil.FontMainName, Font.ITALIC, (int)(30*WindowUtil.GetScaleFactor())));
        add(labelMode, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 100;
        JButton buttonRecordData = WindowUtil.CreateButton(AppInfo.Mode == AppMode.Citizen ? "New Data Record" : "Analysis", getWidth()/2, getHeight()/4, (int)(getWidth()*0.6f), buttonHeight, buttonBackColor, Color.white);
        buttonRecordData.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuState state = AppInfo.Mode == AppMode.Citizen ? MenuState.DataRecordCreate : MenuState.SciAnalysis;
                MenuManager.SetState(state);
            }
        });
        add(buttonRecordData, JLayeredPane.MODAL_LAYER);

        JButton buttonViewDataRecords = WindowUtil.CreateButton(AppInfo.Mode == AppMode.Citizen ? "View Records" : "Download Data Records", getWidth()/2, (int)(getHeight()/4+((buttonHeight*1*1.5))), (int)(getWidth()*0.6f), buttonHeight, buttonBackColor, Color.white);
        buttonViewDataRecords.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuState state = AppInfo.Mode == AppMode.Citizen ? MenuState.ViewDataRecords : MenuState.SciDownload;
                MenuManager.SetState(state);
            }
        });
        add(buttonViewDataRecords, JLayeredPane.MODAL_LAYER);

        JButton buttonSupport = WindowUtil.CreateButton("Support", getWidth()/2, (int)(getHeight()/4+((buttonHeight*2*1.5))), (int)(getWidth()*0.6f), buttonHeight, buttonBackColor, Color.white);
        buttonSupport.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.Support);
            }
        });
        add(buttonSupport, JLayeredPane.MODAL_LAYER);

        JButton buttonInfo = WindowUtil.CreateButton("Info", getWidth()/2, (int)(getHeight()/4+((buttonHeight*3*1.5))), (int)(getWidth()*0.6f), buttonHeight, buttonBackColor, Color.white);
        buttonInfo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.Information);
            }
        });
        add(buttonInfo, JLayeredPane.MODAL_LAYER);
    }
}
