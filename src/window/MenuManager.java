package window;

import java.awt.*;
import javax.swing.*;


import application.AppInfo;
import generic.Vector2Int;
import window.panels.DataRecordCreate;
import window.panels.InfoPanel;
import window.panels.MenuPanel;
import window.panels.SplashPanel;
import window.panels.SupportPanel;
import window.panels.ViewRecordsExternalPanel;
import window.panels.ViewRecordsLocalPanel;
import window.panels.ViewRecordsPanel;

public class MenuManager
{
    int progress;
    Timer timer;
    static JFrame window;

    public static void Innit(MenuState startState)
    {
        window = new JFrame(AppInfo.Name);
        window.setVisible(true);

        Vector2Int windowDimensions = WindowUtil.CalculateWindowDimensions();
        window.setSize(windowDimensions.x, windowDimensions.y);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        WindowUtil.CenterWindow(window);

        SetState(startState);
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
            case DataRecordCreate: return new DataRecordCreate(window);
            case Information: return new InfoPanel(window);
            case Support: return new SupportPanel(window);
            case ViewDataRecords: return new ViewRecordsPanel(window);
            case ViewDataRecordsLocal: return new ViewRecordsLocalPanel(window);
            case ViewDataRecordsExternal: return new ViewRecordsExternalPanel(window);
            default: return null;
        }
    }
}
