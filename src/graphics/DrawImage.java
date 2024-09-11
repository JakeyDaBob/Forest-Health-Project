package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawImage extends JPanel
{
    private BufferedImage image;

    public DrawImage(BufferedImage image)
    {
        this.image = image;

        setOpaque(false);
    }

    public void setImage(BufferedImage bufferedImage)
    {
        this.image = bufferedImage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (image != null)
        {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
        else
        {
            final int gridSize = 8;
            
            int width = getWidth()/gridSize;
            int height = getHeight()/gridSize;

            for (int x = 0; x < gridSize; x++)
            {
                for (int y = 0; y < gridSize; y++)
                {
                    g.setColor((x+y)%2==0 ? Color.black : new Color(255,0,255));
                    g.fillRect(x*width, y*height, width, height);
                }
            }
        }
    }
}