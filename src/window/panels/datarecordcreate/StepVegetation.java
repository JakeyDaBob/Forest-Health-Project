package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import datarecord.*;
import graphics.ColoredPanel;
import graphics.DrawImage;
import window.WindowUtil;
import window.elements.*;

public class StepVegetation extends StepPanel
{
    final Color colorConfirmValid = Color.white;
    final Color colorConfirmInvalid = new Color(128,128,128);

    JButtonWithData[] buttons;
    JButtonWithData buttonConfirm;

    OptionSelectPanel optionsType;
    OptionSelectPanel optionsStage;

    public StepVegetation(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        DrawImage image = new DrawImage(context.image);
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        ColoredPanel box = new ColoredPanel(new Color(0,0,0,200));
        box.setBounds(0,0,getWidth(),getHeight());
        box.setVisible(true);
        add(box, JLayeredPane.PALETTE_LAYER);

        double scalar = 2.4;

        JLabel labelTypeTitle = WindowUtil.CreateLabel("Type", 20, 80, (int)(getWidth()/scalar), 50, Color.white);
        labelTypeTitle.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 25));
        add(labelTypeTitle, JLayeredPane.MODAL_LAYER);

        optionsType = new OptionSelectPanel(DataRecord.VegetationType.values(), new Rectangle(20, 150, (int)(getWidth()/scalar), 600), (int)(getWidth()/scalar), 80, 16);
        optionsType.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                OnOptionSelected();
            }
        });
        add(optionsType, JLayeredPane.MODAL_LAYER);

        
        JLabel labelStageTitle = WindowUtil.CreateLabel("Stage", getWidth()-(int)(getWidth()/scalar)-40, 80, (int)(getWidth()/scalar), 50, Color.white);
        labelStageTitle.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 25));
        add(labelStageTitle, JLayeredPane.MODAL_LAYER);

        optionsStage = new OptionSelectPanel(DataRecord.VegetationDevelopmentStage.values(), new Rectangle(getWidth()-(int)(getWidth()/scalar)-40, 150, (int)(getWidth()/scalar), 600), (int)(getWidth()/scalar), 80, 16);
        optionsStage.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                OnOptionSelected();
            }
        });
        add(optionsStage, JLayeredPane.MODAL_LAYER);
        

        buttonConfirm = WindowUtil.CreateButton("CONFIRM", getWidth()/2, getHeight()-(getHeight()/5), (int)(getWidth()/1.3), 150, new Color(0,0,0,128), Color.white);
        buttonConfirm.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 40));
        buttonConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TryConfirm();
            }
        });
        add(buttonConfirm, JLayeredPane.MODAL_LAYER);

        optionsStage.SetSelectedId(-1);
        optionsType.SetSelectedId(-1);
    }

    boolean GetValid()
    {
        return optionsType.getId() != -1 && optionsStage.getId() != -1;
    }

    void OnOptionSelected()
    {
        buttonConfirm.setForeground(GetValid() ? colorConfirmValid : colorConfirmInvalid);
    }

    void TryConfirm()
    {
        boolean valid = GetValid();
        if (!valid)
        {
            SetState(new StepState(StepState.Result.Incomplete, "Select an option"));
            return;
        }

        context.record.vegetationType = DataRecord.VegetationType.values()[optionsType.getId()];
        context.record.vegetationDevelopmentStage = DataRecord.VegetationDevelopmentStage.values()[optionsStage.getId()];

        SetState(new StepState(StepState.Result.Done));
    }

    @Override
    public String GetStepName()
    {
        return "Vegetation";
    }
}
