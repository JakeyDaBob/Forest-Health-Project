package window.panels;

import window.panels.datarecordcreate.*;
import java.awt.*;
import javax.swing.*;

public class DataRecordCreate extends JLayeredPane
{
    Timer timer;
    int progress;

    JLayeredPane holder;
    JLayeredPane stepPanel;

    public DataRecordCreate(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0, 0, 0));
        backgroundPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        holder = new JLayeredPane();
        holder.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(holder, JLayeredPane.PALETTE_LAYER);
        
        DataContext context = new DataContext();
        stepPanel = new StepTakePhoto(holder, context);
        holder.add(stepPanel, JLayeredPane.DEFAULT_LAYER);
    }
}
