package window.panels;

import window.WindowUtil;
import window.panels.datarecordcreate.*;
import window.elements.*;
import java.awt.*;
import javax.swing.*;

import datarecord.DataRecord.*;
import datarecord.DataRecord;

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

        stepId = 11;
        SetStepFromId(stepId);
    }

    void OnStepState(StepState stepState)
    {
        System.out.println("State = " + stepState);
        
        int stepIdNew = stepId;
        if (stepState.result == StepState.Result.Done)
        {
            stepIdNew = GetNextStep(stepId);
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

    int GetNextStep(int stepCurrent)
    {
        //Skip recovery information if the Burn Severity is Unburnt
        if (stepCurrent == 4 && context.record.burnSeverity == BurnSeverity.Unburnt)
        {
            context.record.recoveryGround = DataRecord.RecoveryGroundLayer.Unburnt;
            context.record.recoveryShrub = DataRecord.RecoveryShrubLayer.Unburnt;
            context.record.recoveryLowerCanopy = DataRecord.RecoveryLowerCanopyLayer.Unburnt;
            context.record.recoveryUpperCanopy = DataRecord.RecoveryUpperCanopyLayer.Unburnt;
            context.record.recoveryEmergantLayer = DataRecord.RecoveryEmergantLayer.Unburnt;
            return 10;
        }

        return stepCurrent+1;
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
            case 4: return new StepBurnSeverity(holder, context);
            case 5: return new StepRecoveryLayerGround(holder, context);
            case 6: return new StepRecoveryLayerShrub(holder, context);
            case 7: return new StepRecoveryLayerLowerCanopy(holder, context);
            case 8: return new StepRecoveryLayerUpperCanopy(holder, context);
            case 9: return new StepRecoveryLayerEmergantLayer(holder, context);
            case 10: return new StepFloweringState(holder, context);
            case 11: return new StepFauna(holder, context);
        }

        return null;
    }
}
