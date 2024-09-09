package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import window.WindowUtil;

public class StepReview extends StepPanel
{
    JButton buttonConfirm;

    public StepReview(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        buttonConfirm = WindowUtil.CreateButton("CONFIRM", getWidth()/2, getHeight()-(getHeight()/5), (int)(getWidth()/1.3), 150, new Color(0,0,0,128), Color.white);
        buttonConfirm.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 40));
        buttonConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SetState(new StepState(StepState.Result.Done));
            }
        });
        add(buttonConfirm, JLayeredPane.MODAL_LAYER);
    }

    @Override
    public String GetStepName()
    {
        return "Review";
    }
}
