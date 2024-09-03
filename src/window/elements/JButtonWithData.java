package window.elements;

import java.awt.*;

public class JButtonWithData extends JButtonRounded
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

    public JButtonWithData()
    {
        super(25);
        this.data=0;
    }
}
