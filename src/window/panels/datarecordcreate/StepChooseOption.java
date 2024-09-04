package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import window.WindowUtil;
import window.elements.*;

public abstract class StepChooseOption extends StepPanel
{   
    final Color colorConfirmValid = Color.white;
    final Color colorConfirmInvalid = new Color(128,128,128);

    abstract Object[] GetOptionObjects();
    abstract String GetOptionName();
    abstract void OnComplete();

    JButtonWithData[] buttons;
    JButtonWithData buttonConfirm;
    OptionSelectPanel optionSelectPanel;

    public StepChooseOption(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        JLabel labelTitle = WindowUtil.CreateLabel(GetOptionName(), 0, 80, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 35));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        optionSelectPanel = new OptionSelectPanel(GetOptionObjects(), new Rectangle(0, 150, getWidth(), 600), (int)(getWidth()/1.2), 26);
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
        int id = optionSelectPanel.getId();
        buttonConfirm.setForeground(id != -1 ? colorConfirmValid : colorConfirmInvalid);
    }

    void TryConfirm()
    {
        int id = optionSelectPanel.getId();
        boolean valid = id != -1;
        if (!valid)
        {
            SetState(new StepState(StepState.Result.Incomplete, "Select an option"));
            return;
        }

        OnComplete();

        SetState(new StepState(StepState.Result.Done));
    }
}
