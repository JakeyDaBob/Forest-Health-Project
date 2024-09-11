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

public class ViewRecordsExternalPanel extends JLayeredPane
{
    public ViewRecordsExternalPanel(JFrame window)
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

        JLabel labelTitle = WindowUtil.CreateLabel("Public Data Records", 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 100;
        JButton buttonInfo = WindowUtil.CreateButton("Back", getWidth()/2, (int)(getHeight()/4+((buttonHeight*3.5*1.5))), (int)(getWidth()*0.6f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonInfo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.ViewDataRecords);
            }
        });
        add(buttonInfo, JLayeredPane.MODAL_LAYER);
    }
}
