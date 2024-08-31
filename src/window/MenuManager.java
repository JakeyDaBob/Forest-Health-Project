package window;

import java.awt.*;
import javax.swing.*;

import application.AppInfo;
import generic.Vector2Int;
import window.panels.MenuPanel;
import window.panels.SplashPanel;

public class MenuManager
{
    

    int progress;
    Timer timer;
    static JFrame window;

    public static void Innit()
    {
        window = new JFrame(AppInfo.Name);
        window.setVisible(true);

        Vector2Int windowDimensions = WindowUtil.CalculateWindowDimensions();
        window.setSize(windowDimensions.x, windowDimensions.y);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        WindowUtil.CenterWindow(window);
    }

    public static void SetState(MenuState state)
    {
        Container pane = GetPaneForState(state);
        if (pane == null)
        {
            System.out.println("Menu State Invalid: " + state);
            return;
        }
        window.setContentPane(pane);
        window.invalidate();
        window.validate();
    }

    static Container GetPaneForState(MenuState state)
    {
        switch (state)
        {
            case Splash: return new SplashPanel(window);
            case Menu: return new MenuPanel(window);
            default: return null;
        }
    }
}
