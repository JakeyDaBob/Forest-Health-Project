package window.panels;

import window.WindowUtil;
import window.panels.datarecordcreate.*;
import java.awt.*;
import javax.swing.*;

public class DataRecordCreate extends JLayeredPane
{
    Timer timer;
    int progress;
    JLabel titleLabel;

    JLayeredPane holder;
    JLayeredPane stepPanel;

    DataContext context;

    public DataRecordCreate(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0, 0, 0));
        backgroundPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        titleLabel = WindowUtil.CreateLabel("Title", 0, 0, getWidth(), 50, Color.white);
        titleLabel.setBackground(new Color(0,0,0,100));
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, 42));
        add(titleLabel, JLayeredPane.POPUP_LAYER);

        holder = new JLayeredPane();
        holder.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(holder, JLayeredPane.PALETTE_LAYER);
        
        DataContext context = new DataContext();
        
        stepPanel = new JLayeredPane();
        stepPanel.setBounds(0,0,getWidth(),getHeight());

        holder.add(stepPanel, JLayeredPane.PALETTE_LAYER);

        SetStep(new StepTakePhoto(holder, context));

    }

    void OnStepState(StepState stepState)
    {
        System.out.println("State = " + stepState);
    }

    void SetStep(StepPanel step)
    {
        holder.remove(stepPanel);
        stepPanel = step;

        add(stepPanel, JLayeredPane.PALETTE_LAYER);
        
        titleLabel.setText(step.GetStepName());

        step.SetOnState(() -> { OnStepState(step.getState()); });

        repaint();
    }
}
