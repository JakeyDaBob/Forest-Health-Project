package window.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class JButtonRounded extends JButton
{
    private int radius;

    JButtonRounded(int radius)
    {
        super();
        this.radius = radius;
        setFocusPainted(false);  // Disable focus rectangle
        setContentAreaFilled(false);  // Prevent default background filling
        setOpaque(false);  // Make sure the button does not paint a solid background
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the area and set up the background color
        Color color = getBackground();

        if (getModel().isPressed())
        {
            color = getBackground().darker();
        }
        else if (getModel().isRollover()) 
        {
            color = getBackground().brighter();
        }

        g2d.setColor(color);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));

        // Paint button text
        g2d.setColor(getForeground());
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(getText());
        int stringHeight = fontMetrics.getAscent();
        g2d.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 3);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Clear the area and set up the border color
        g2d.setColor(getForeground());
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}
