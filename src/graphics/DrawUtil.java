package graphics;

import generic.FColor;
import java.awt.*;

public class DrawUtil
{
    public static void Square(Graphics g, int x, int y, int width, FColor color)
    {
        g.setColor(color.ToAwtColor());
        g.fillRect(x, y, width, width);

        FColor colorB = FColor.Lerp(color, new FColor(0,0,0), 0.5f);
        g.setColor(colorB.ToAwtColor());

        int width2 = (int)(width*0.8f);
        int x2 = x + (width/2) - (width2/2);
        int y2 = y + (width/2) - (width2/2);

        g.fillRect(x2, y2, width2, width2);
    }

    public static void Text(Graphics g, int x, int y, String str, Font font, FColor color)
    {
        g.setColor(color.ToAwtColor());
        g.setFont(font);

        int textWidth = ((Graphics2D)g).getFontMetrics().charsWidth(str.toCharArray(), 0, str.length());
        x -= textWidth/2;

        g.drawChars(str.toCharArray(), 0, str.length(), x, y);
    }
}
