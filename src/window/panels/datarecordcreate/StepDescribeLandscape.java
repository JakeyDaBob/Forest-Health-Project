package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import datarecord.*;
import datarecord.DataRecord.LandscapePosition;
import graphics.ColoredPanel;
import graphics.DrawImage;
import window.WindowUtil;
import window.elements.*;
import java.util.Arrays;

public class StepDescribeLandscape extends StepPanel
{
    final Color colorDeselected = new Color(0,0,0,64);
    final Color colorSelected = new Color(66, 173, 38, 128);

    final Color colorConfirmValid = Color.white;
    final Color colorConfirmInvalid = new Color(128,128,128);

    JButtonWithData[] buttons;
    JButtonWithData buttonConfirm;

    OptionSelectPanel optionSelectPanel;

    public StepDescribeLandscape(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        DrawImage image = new DrawImage(context.image);
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        ColoredPanel box = new ColoredPanel(new Color(0,0,0,200));
        box.setBounds(0,0,getWidth(),getHeight());
        box.setVisible(true);
        add(box, JLayeredPane.PALETTE_LAYER);

        optionSelectPanel = new OptionSelectPanel(DataRecord.LandscapePosition.values(), new Rectangle(0, 100, getWidth(), 600));
        optionSelectPanel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                OnOptionSelected();
            }
        });
        add(optionSelectPanel, JLayeredPane.MODAL_LAYER);
        

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
    }

    void OnOptionSelected()
    {
        int id = optionSelectPanel.getIdSelected();
        buttonConfirm.setForeground(id != -1 ? colorConfirmValid : colorConfirmInvalid);
    }

    void TryConfirm()
    {
        int id = optionSelectPanel.getIdSelected();
        boolean valid = id != -1;
        if (!valid)
        {
            SetState(new StepState(StepState.Result.Incomplete, "Select an option"));
            return;
        }

        context.record.landscapePosition = LandscapePosition.values()[id];
        System.out.println("Landscape = " + context.record.landscapePosition);

        SetState(new StepState(StepState.Result.Done));
    }

    @Override
    public String GetStepName()
    {
        return "Describe Landscape";
    }
}
