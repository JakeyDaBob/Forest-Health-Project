package window.panels.datarecordcreate;

import javax.swing.*;


public abstract class StepPanel extends JLayeredPane
{
    public Runnable onComplete;
    protected DataContext context;
    protected JLayeredPane parentPanel;

    public StepPanel(JLayeredPane parentPanel, DataContext context)
    {
        this.parentPanel = parentPanel;
        this.context = context;

        setBounds(0, 0, parentPanel.getWidth(), parentPanel.getHeight());
    }

    public abstract StepState GetState();

    public abstract String GetName();
}
