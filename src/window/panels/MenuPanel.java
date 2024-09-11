package window.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

import application.AppInfo;
import application.FileSystem;
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

        Random random = new Random();
        String[] imagePaths = FileSystem.Resources.GetAllFilesInDirectory("photos");
        String imageFileName = imagePaths[random.nextInt(imagePaths.length)];
        String imagePath = "photos/"+imageFileName;

        DrawImage image = new DrawImage(FileSystem.Resources.GetImage(imagePath));
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

        int buttonHeight = 100;
        JButton buttonRecordData = WindowUtil.CreateButton("New Data Record", getWidth()/2, getHeight()/4, (int)(getWidth()*0.6f), buttonHeight, buttonBackColor, Color.white);
        buttonRecordData.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.DataRecordCreate);
            }
        });
        add(buttonRecordData, JLayeredPane.MODAL_LAYER);

        JButton buttonViewDataRecords = WindowUtil.CreateButton("View Records", getWidth()/2, (int)(getHeight()/4+((buttonHeight*1*1.5))), (int)(getWidth()*0.6f), buttonHeight, buttonBackColor, Color.white);
        buttonViewDataRecords.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.ViewDataRecords);
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
