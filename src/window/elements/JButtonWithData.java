package window.elements;

import javax.swing.*;

public class JButtonWithData extends JButton
{
    private int data;
    public int getData()
    {
        return data;
    }
    public void setData(int data)
    {
        this.data=data;
    }

    public JButtonWithData(int data)
    {
        super();
        this.data = data;
    }

    public JButtonWithData()
    {
        super();
        this.data=0;
    }
}
