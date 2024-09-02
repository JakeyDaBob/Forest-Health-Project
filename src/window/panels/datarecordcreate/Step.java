package window.panels.datarecordcreate;

import javax.swing.JPanel;


public abstract class Step
{
    public Runnable onComplete;
    protected JPanel holder;
    protected DataContext context;

    public Step(JPanel holder, DataContext context)
    {
        this.holder = holder;
        this.context = context;
    }

    public abstract StepState GetState();

    public abstract String GetName();
}
