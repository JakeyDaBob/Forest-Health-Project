package window.panels;

import java.awt.*;
import javax.swing.*;

public class DataRecordCreate extends JLayeredPane
{
    Timer timer;
    int progress;

    JPanel holder;

    public DataRecordCreate(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0, 0, 0));
        backgroundPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        holder = new JPanel();
        holder.setBounds(0, 0, window.getWidth(), window.getHeight());
    }
}
