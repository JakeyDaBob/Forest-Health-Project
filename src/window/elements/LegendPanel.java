package window.elements;

import java.awt.*;
import javax.swing.*;

import window.WindowUtil;

public class LegendPanel extends JLayeredPane
{
    public LegendPanel(String[] names, Color[] colors)
    {
        if (names.length != colors.length)
        {
            throw new IllegalArgumentException();
        }

        setOpaque(false);
        setBackground(new Color(0,0,0,0));

        for (int i = 0; i < names.length; i++)
        {
            JPanel panel = new JPanel(null);
            panel.setBackground(colors[i]);
            panel.setBounds(0, i*25, 20, 20);
            add(panel, JLayeredPane.MODAL_LAYER);

            JLabel label = WindowUtil.CreateLabel(names[i], 25, i*25, 100, 20, Color.white);
            label.setHorizontalAlignment(SwingConstants.LEFT);
            add(label, JLayeredPane.MODAL_LAYER);
        }
    }
}
