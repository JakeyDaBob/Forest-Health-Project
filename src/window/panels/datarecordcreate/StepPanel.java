package window.panels.datarecordcreate;

import javax.swing.*;


public abstract class StepPanel extends JLayeredPane
{
    private Runnable OnState;
    protected DataContext context;
    protected JLayeredPane parentPanel;
    protected StepState state;
    public StepState getState()
    {
        return state;
    }

    public void SetOnState(Runnable runnable)
    {
        OnState = runnable;
    }

    protected void SetState(StepState state)
    {
        this.state = state;
        OnState.run();
    }

    public StepPanel(JLayeredPane parentPanel, DataContext context)
    {
        this.parentPanel = parentPanel;
        this.context = context;

        setBounds(0, 0, parentPanel.getWidth(), parentPanel.getHeight());
    }

    public abstract String GetStepName();
}
