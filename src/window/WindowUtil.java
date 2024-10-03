package window;

import java.awt.*;
import javax.swing.*;

import application.AppInfo;
import application.AppMode;
import generic.Vector2Int;

import window.elements.*;

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

        if (AppInfo.Mode == AppMode.Scientist)
        {
            windowWidth = screenDimensions.x-100;
            windowHeight = screenDimensions.y-100;
        }

        return new Vector2Int(windowWidth, windowHeight);
    }

    public static Vector2Int GetScreenDimensions()
    {
        return new Vector2Int((int)ToolkitMain.getScreenSize().getWidth(), (int)ToolkitMain.getScreenSize().getHeight());
    }

    public static JButtonWithData CreateButton(String buttonText, int x, int y, int width, int height, Color backgroundColor, Color textColor)
    {
        JButtonWithData button = new JButtonWithData();
        
        button.setText(buttonText);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        

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

    public static void Resize(Window window, int widthNew, int heightNew)
    {
        int widthCurrent = window.getWidth();
        int heightCurrent = window.getHeight();

        int widthDiff = widthNew-widthCurrent;
        int heightDiff = heightNew-heightCurrent;

        int x = window.getX() - (widthDiff/2);
        int y = window.getY() - (heightDiff/2);

        window.setBounds(x, y, widthNew, heightNew);
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

    public static JTextField CreateTextField(int x, int y, int width, int height, Color colorBack, Color colorText)
    {
        JTextField field = new JTextField(SwingConstants.CENTER);
        field.setBounds(x-(width/2),y-(height/2),width,height);
        field.setBackground(colorBack);
        field.setForeground(colorText);
        field.setFont(new Font(FontMainName, Font.PLAIN, 20));

        if (colorBack.getAlpha() < 255)
        {
            field.setOpaque(false);
        }

        return field;
    }

    public static double GetScaleFactor()
    {
        return WindowHeightPercentage;
    }
}
