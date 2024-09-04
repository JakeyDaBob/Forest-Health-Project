package generic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RectangleCool
{
    public int x, y, width, height;
    
    public Rectangle toRectangle()
    {
        return new Rectangle(x,y,width,height);
    }
}
