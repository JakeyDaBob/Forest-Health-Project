package window;

import java.awt.*;
import javax.swing.*;

import generic.Vector2Int;

public class WindowUtil
{
    public static final Vector2Int AspectRatio = new Vector2Int(9, 16);
    public static final double WindowHeightPercentage = 0.9f;

    static Toolkit ToolkitMain = Toolkit.getDefaultToolkit();

    public static Vector2Int CalculateWindowDimensions()
    {
        Vector2Int screenDimensions = WindowUtil.GetScreenDimensions();
        int windowHeight = (int)(screenDimensions.y*WindowHeightPercentage);
        int windowWidth = (int)(windowHeight*((double)AspectRatio.x/AspectRatio.y));

        return new Vector2Int(windowWidth, windowHeight);
    }

    public static Vector2Int GetScreenDimensions()
    {
        return new Vector2Int((int)ToolkitMain.getScreenSize().getWidth(), (int)ToolkitMain.getScreenSize().getHeight());
    }

    public static JButton CreateButton(String buttonText, int x, int y, int width, int height, Color backgroundColor, Color textColor)
    {
        JButton button = new JButton(buttonText);

        button.setBackground(backgroundColor);
        button.setForeground(textColor);

        if (backgroundColor.getAlpha() < 255)
        {
            button.setOpaque(false);
            button.setFocusPainted(false);
        }
        

        button.setBounds(x - (width / 2), y - (height / 2), width, height);

        return button;
    }

    public static void SetWindow(JFrame frame, JLayeredPane contentPane)
    {
        frame.setContentPane(contentPane);
        frame.invalidate();
        frame.revalidate();
        frame.setVisible(true);
    }

    public static void CenterWindow(Window window) 
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension windowSize = window.getSize();

        int x = (screenSize.width - windowSize.width) / 2;
        int y = (screenSize.height - windowSize.height) / 2;

        window.setLocation(x, y);
    }

    public static final String FontMainName = "SansSerif";

    public static Font GetFontMain()
    {
        return new Font(FontMainName, Font.PLAIN, 16);
    }

    public static JLabel CreateLabel(String text, int x, int y, int width, int height, Color color)
    {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setBounds(x,y,width,height);

        if (color.getAlpha() < 255)
        {
            label.setOpaque(false);
        }

        return label;
    }

    public static double GetScaleFactor()
    {
        return WindowHeightPercentage;
    }
}
