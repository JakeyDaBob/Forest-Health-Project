package window.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

import application.FileSystem;
import graphics.ColoredPanel;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

public class ViewRecordsPanel extends JLayeredPane
{
    public ViewRecordsPanel(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        Random random = new Random();
        String[] imagePaths = FileSystem.Resources.GetAllFilesInDirectory("photos");
        String imageFileName = imagePaths[random.nextInt(imagePaths.length)];
        String imagePath = "photos/"+imageFileName;

        DrawImage image = new DrawImage(FileSystem.Resources.GetImage(imagePath));
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        ColoredPanel box = new ColoredPanel(new Color(0,0,0,200));
        box.setBounds(0,0,getWidth(),getHeight());
        box.setVisible(true);
        add(box, JLayeredPane.PALETTE_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel("View Data Records", 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        Color buttonColor = new Color(0,0,0,128);

        DrawImage img1 = new DrawImage(FileSystem.Resources.GetImage("icons/folder.png"));
        img1.setBounds(100,200,100,100);
        add(img1, JLayeredPane.MODAL_LAYER);

        JButton button1 = WindowUtil.CreateButton("Local", 150, 400, 200, 80, buttonColor, Color.white);
        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.ViewDataRecordsLocal);
            }
        });
        add(button1, JLayeredPane.MODAL_LAYER);

        DrawImage img2 = new DrawImage(FileSystem.Resources.GetImage("icons/wifi.png"));
        img2.setBounds(getWidth()-200,200,100,100);
        add(img2, JLayeredPane.MODAL_LAYER);

        JButton button2 = WindowUtil.CreateButton("Public", getWidth()-150, 400, 200, 80, buttonColor, Color.white);
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.ViewDataRecordsExternal);
            }
        });
        add(button2, JLayeredPane.MODAL_LAYER);
        

        int buttonHeight = 100;
        JButton buttonInfo = WindowUtil.CreateButton("Back", getWidth()/2, (int)(getHeight()/4+((buttonHeight*3.5*1.5))), (int)(getWidth()*0.6f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonInfo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.Menu);
            }
        });
        add(buttonInfo, JLayeredPane.MODAL_LAYER);
    }
}
