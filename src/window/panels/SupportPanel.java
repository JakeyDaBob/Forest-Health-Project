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

public class SupportPanel extends JLayeredPane
{
    public SupportPanel(JFrame window)
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

        JLabel labelTitle = WindowUtil.CreateLabel("Support", 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        String info = "<html>";
        info += "<br>If you have any enquires or require assistance creating a Data Record please don't hesitate to contact us!";
        
        info += "<br><br>Support Mobile Number (Australia):";

        info += "<br><h1>" + AppInfo.ContactMobileSupport + "</h1>";

        info += "</html>";
        JLabel labelInfo = WindowUtil.CreateLabel(info, 20, 150, getWidth()-40, 500, Color.white);
        labelInfo.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 16));
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        labelInfo.setVerticalAlignment(SwingConstants.NORTH);
        add(labelInfo, JLayeredPane.MODAL_LAYER);

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
