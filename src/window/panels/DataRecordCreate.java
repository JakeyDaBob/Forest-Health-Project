package window.panels;

import window.WindowUtil;
import window.panels.datarecordcreate.*;
import window.elements.*;
import java.awt.*;
import javax.swing.*;

public class DataRecordCreate extends JLayeredPane
{
    Timer timer;
    int progress;
    JLabel titleLabel;

    JLayeredPane holder;
    JLayeredPane stepPanel;
    PopupPanel popupPanel;

    DataContext context;

    int stepId;

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
        add(titleLabel, JLayeredPane.MODAL_LAYER);

        holder = new JLayeredPane();
        holder.setBounds(0, 0, window.getWidth(), window.getHeight());
        add(holder, JLayeredPane.PALETTE_LAYER);

        popupPanel = new PopupPanel();
        popupPanel.setBounds(0,0,getWidth(), 60);
        add(popupPanel, JLayeredPane.POPUP_LAYER);
        
        context = new DataContext();
        
        stepPanel = new JLayeredPane();
        stepPanel.setBounds(0,0,getWidth(),getHeight());

        holder.add(stepPanel, JLayeredPane.PALETTE_LAYER);

        stepId = 0;
        SetStepFromId(stepId);
    }

    void OnStepState(StepState stepState)
    {
        System.out.println("State = " + stepState);
        
        int stepIdNew = stepId;
        if (stepState.result == StepState.Result.Done)
        {
            stepIdNew++;
        }
        else if (stepState.result == StepState.Result.Back)
        {
            stepIdNew--;
        }
        else
        {
            popupPanel.Do(stepState.comment);
        }

        if (stepIdNew != stepId)
        {
            stepId = stepIdNew;
            SetStepFromId(stepId);
        }
    }

    void SetStepFromId(int stepId)
    {
        SetStep(CreateStep(stepId));
    }

    void SetStep(StepPanel step)
    {
        popupPanel.setVisible(false);

        holder.remove(stepPanel);
        stepPanel = null;
        stepPanel = step;

        holder.add(stepPanel, JLayeredPane.PALETTE_LAYER);
        
        titleLabel.setText(step.GetStepName());

        step.SetOnState(() -> { OnStepState(step.getState()); });

        holder.revalidate();
        repaint();
    }

    StepPanel CreateStep(int stepId)
    {
        switch (stepId)
        {
            case 0: return new StepTakePhoto(holder, context);
            case 1: return new StepPreviewPhoto(holder, context);
            case 2: return new StepDescribeLandscape(holder, context);
            case 3: return new StepVegetation(holder, context);
        }

        return null;
    }
}
