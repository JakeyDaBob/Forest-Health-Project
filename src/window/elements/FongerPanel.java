package window.elements;

import graphics.*;
import window.WindowUtil;

import java.awt.*;
import javax.swing.*;

public class FongerPanel extends JLayeredPane
{
    public FongerPanel(String name, String[] names, double[] values, Color[] colors, Rectangle rect, Color backgroundColor)
    {
        setBounds(rect);

        JPanel bg = new JPanel(null);
        bg.setBounds(0,0,rect.width,rect.height);
        bg.setBackground(backgroundColor);
        add(bg, JLayeredPane.DEFAULT_LAYER);

        JLabel label = WindowUtil.CreateLabel(name, 0, 0, getWidth(), 30, Color.white);
        label.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 18));
        add(label, JLayeredPane.PALETTE_LAYER);

        PieChartPanel chart = new PieChartPanel(values, colors);
        chart.setBounds(10,(getHeight()/2)-(((int)(getWidth()/2.5)-20)/2),(int)(getWidth()/2.5)-20,(int)(getWidth()/2.5)-20);
        add(chart, JLayeredPane.PALETTE_LAYER);

        LegendPanel legend = new LegendPanel(names, colors, new Rectangle(getWidth()-((getWidth()/2)+40),40,(getWidth()),getHeight()-40));
        add(legend, JLayeredPane.PALETTE_LAYER);
    }
}
