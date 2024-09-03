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

public class StepDescribeLandscape extends StepPanel
{
    final Color colorDeselected = new Color(0,0,0,64);
    final Color colorSelected = new Color(66, 173, 38, 128);

    final Color colorConfirmValid = Color.white;
    final Color colorConfirmInvalid = new Color(128,128,128);

    JButtonWithData[] buttons;
    JButtonWithData buttonConfirm;
    int idSelected = -1;

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

        var landscapePositions = DataRecord.LandscapePosition.values();

        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(new Color(0,0,0,0));
        buttonPanel.setBounds(0,150,getWidth(), 600);
        add(buttonPanel, JLayeredPane.MODAL_LAYER);

        buttons = new JButtonWithData[landscapePositions.length];

        for (int i = 0; i < landscapePositions.length; i++)
        {
            String text = WindowUtil.FormatCodeString(landscapePositions[i].toString());

            var button = WindowUtil.CreateButton(text, getWidth()/2, 50+(120*i), getWidth()/2, 100, Color.black, Color.white);
            button.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 36));
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    OnButtonPress(button);   
                }
            });

            button.setData(i);

            buttons[i] = button;
            buttonPanel.add(button);
        }

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

        SetSelectedId(-1);
    }

    void OnButtonPress(JButtonWithData button)
    {
        int id = button.getData();
        if (idSelected == id)
        {
            idSelected = -1;
        }
        else
        {
            idSelected = id;
        }


        SetSelectedId(idSelected);
    }

    void SetSelectedId(int id)
    {
        for (int i = 0; i < buttons.length; i++)
        {
            boolean selected = i == id;
            Color color = selected ? colorSelected : colorDeselected;
            buttons[i].setBackground(color);
        }

        buttonConfirm.setForeground(id != -1 ? colorConfirmValid : colorConfirmInvalid);
    }

    void TryConfirm()
    {
        boolean valid = idSelected != -1;
        if (!valid)
        {
            SetState(new StepState(StepState.Result.Incomplete, "Select an option"));
            return;
        }

        context.record.landscapePosition = LandscapePosition.values()[idSelected];
        System.out.println("Landscape = " + context.record.landscapePosition);

        SetState(new StepState(StepState.Result.Done));
    }

    @Override
    public String GetStepName()
    {
        return "Describe Landscape";
    }
}
