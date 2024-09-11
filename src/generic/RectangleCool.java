package generic;

import java.awt.*;

public class RectangleCool
{
    public int x, y, width, height;
    
    public Rectangle toRectangle()
    {
        return new Rectangle(x,y,width,height);
    }
}
