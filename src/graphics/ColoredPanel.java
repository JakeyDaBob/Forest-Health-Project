package graphics;

import javax.swing.*;
import java.awt.*;

public class ColoredPanel extends JPanel
{
    Color color = Color.black;

    public ColoredPanel(Color color)
    {
        this.color = color;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);;
        
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());        
    }
}
