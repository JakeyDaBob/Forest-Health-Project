package graphics;

import javax.swing.*;
import java.awt.*;

public class PieChartPanel extends JPanel
{
    private double[] values;
    private Color[] colors;

    public PieChartPanel(double[] values, Color[] colors) 
    {
        this.values = values;
        this.colors = colors;

        setOpaque(false);
        setBackground(new Color(0,0,0,0));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double total = 0.0;
        for (double v : values)
        {
            total += v;
        }

        double startAngle = 0.0;

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 20;

        int x = (width - diameter) / 2;
        int y = (height - diameter) / 2;

        for (int i = 0; i < values.length; i++)
         {
            double slicePercentage = values[i] / total;
            double angle = slicePercentage * 360.0;

            g2d.setColor(colors[i]);

            g2d.fillArc(x, y, diameter, diameter, (int) Math.round(startAngle), (int) Math.round(angle));

            startAngle += angle;
        }
    }
}
