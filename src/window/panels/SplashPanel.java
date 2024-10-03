package window.panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import application.*;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

public class SplashPanel extends JLayeredPane
{
    Timer timer;
    int progress;

    public SplashPanel(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0, 0, 0));
        backgroundPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        DrawImage image = new DrawImage(AppInfo.GetSplashImage());
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.PALETTE_LAYER);

        Color textBackgroundColor = new Color(0, 0, 0, 128);

        JLabel labelTitle = WindowUtil.CreateLabel(AppInfo.Name, 0, getHeight()/8, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(50*WindowUtil.GetScaleFactor())));
        labelTitle.setOpaque(true);
        labelTitle.setBackground(textBackgroundColor);
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        JLabel labelSubtitle = WindowUtil.CreateLabel(AppInfo.CourseCode + " " + AppInfo.CourseName, 0, (getHeight()/8)+55, getWidth(), 25, Color.white);
        labelSubtitle.setFont(new Font(WindowUtil.FontMainName, Font.ITALIC, (int)(20*WindowUtil.GetScaleFactor())));
        labelSubtitle.setOpaque(true);
        labelSubtitle.setBackground(textBackgroundColor);
        add(labelSubtitle, JLayeredPane.MODAL_LAYER);

        String groupLabelText = "Group: " + AppInfo.GroupNumber + ": " + AppInfo.GroupMembersToString();
        JLabel labelCredits = WindowUtil.CreateLabel(groupLabelText, 0, getHeight()-(getHeight()/10), getWidth(), 25, Color.white);
        labelCredits.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, (int)(12*WindowUtil.GetScaleFactor())));
        labelCredits.setOpaque(true);
        labelCredits.setBackground(textBackgroundColor);
        add(labelCredits, JLayeredPane.MODAL_LAYER);

        //move to menu after 3 seconds
        timer = new Timer(1000, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                progress++;

                if (progress >= 3)
                {
                    timer.stop();
                    MenuManager.SetState(MenuState.Menu);
                }
            }
        });
        timer.start();
    }
}
