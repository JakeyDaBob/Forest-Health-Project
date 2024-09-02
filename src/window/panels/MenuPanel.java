package window.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import application.AppInfo;
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

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0, 0, 0));
        backgroundPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel(AppInfo.Name, 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 100;
        JButton buttonRecordData = WindowUtil.CreateButton("New Data Record", getWidth()/2, getHeight()/4, (int)(getWidth()*0.6f), buttonHeight, Color.black, Color.white);
        buttonRecordData.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.DataRecordCreate);
            }
        });
        add(buttonRecordData, JLayeredPane.MODAL_LAYER);

        JButton buttonViewDataRecords = WindowUtil.CreateButton("View Records", getWidth()/2, (int)(getHeight()/4+((buttonHeight*1*1.5))), (int)(getWidth()*0.6f), buttonHeight, Color.black, Color.white);
        add(buttonViewDataRecords, JLayeredPane.MODAL_LAYER);

        JButton buttonSupport = WindowUtil.CreateButton("Support", getWidth()/2, (int)(getHeight()/4+((buttonHeight*2*1.5))), (int)(getWidth()*0.6f), buttonHeight, Color.black, Color.white);
        add(buttonSupport, JLayeredPane.MODAL_LAYER);

        JButton buttonInfo = WindowUtil.CreateButton("Info", getWidth()/2, (int)(getHeight()/4+((buttonHeight*3*1.5))), (int)(getWidth()*0.6f), buttonHeight, Color.black, Color.white);
        add(buttonInfo, JLayeredPane.MODAL_LAYER);
    }
}
