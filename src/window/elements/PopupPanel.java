package window.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import window.WindowUtil;

public class PopupPanel extends JLayeredPane
{
    JLabel label;
    JPanel backPanel;
    Timer timer;

    public PopupPanel()
    {
        setOpaque(false);

        backPanel = new JPanel(null);
        backPanel.setBounds(0,0,getWidth(),getHeight());
        backPanel.setBackground(new Color(128,0,0,255));

        label = WindowUtil.CreateLabel("popup", getWidth()/2, getHeight(), getWidth(), getHeight(), Color.white);
        label.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, 30));

        add(backPanel, JLayeredPane.DEFAULT_LAYER);
        add(label, JLayeredPane.MODAL_LAYER);

        setVisible(false);
    }

    public void Do(String text)
    {
        setVisible(true);

        label.setText(text);

        if (timer != null)
        {
            timer.stop();
            timer = null;
        }

        timer = new Timer(3000, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                timer.stop();
                timer = null;

                setVisible(false);
            }
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        backPanel.setBounds(0,0,getWidth(),getHeight());
        backPanel.repaint();

        label.setBounds(0, 0, getWidth(), getHeight());
        label.repaint();
    }
}
