package window.elements;

import graphics.*;

import java.awt.*;
import javax.swing.*;

public class FongerPanel extends JLayeredPane
{
    public FongerPanel(String[] names, double[] values, Color[] colors, Rectangle rect, Color backgroundColor)
    {
        setBounds(rect);

        JPanel bg = new JPanel(null);
        bg.setBounds(rect);
        bg.setBackground(backgroundColor);
        add(bg, JLayeredPane.DEFAULT_LAYER);

        PieChartPanel chart = new PieChartPanel(values, colors);
        chart.setBounds(30,15,(getWidth()/2)-20,getHeight()-20);
        add(chart, JLayeredPane.PALETTE_LAYER);

        LegendPanel legend = new LegendPanel(names, colors);
        legend.setBounds(getWidth()-((getWidth()/2)-40),40,(getWidth()/2)-40,getHeight()-40);
        add(legend, JLayeredPane.PALETTE_LAYER);
    }
}
