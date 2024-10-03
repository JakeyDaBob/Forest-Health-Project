package generic;

import java.util.*;

public class Gradient
{
    List<Key> keys;

    public Gradient()
    {
        keys = new ArrayList<>();
    }

    public Gradient(FColor... colors)
    {
        this();

        if (colors.length < 2)
        {
            throw new IllegalArgumentException("Must have 2 or more colors");
        }

        for (int i = 0; i < colors.length; i++)
        {
            double v = (double)i/(colors.length-1);
            Key key = new Key(v,colors[i]);

            keys.add(key);
        }
    }
    
    public FColor Evaluate(double d)
    {
        if (d < 0 || d > 1)
        {
            throw new IllegalArgumentException("Illegal sample value of: " + d);
        }

        //Find key before and after 'd'
        Key keyA = null;
        Key keyB = null;
        for (int i = 0; i < keys.size(); i++)
        {
            if (keys.get(i).v <= d)
            {
                if (i+1 < keys.size())
                {
                    keyA = keys.get(i);
                    keyB = keys.get(i+1);
                }
            }
        }

        if (keyA == null || keyB == null)
        {
            throw new IllegalArgumentException("Couldn't find valid keys for: " + d);
        }

        float t = (float)Mathb.InverseLerp(keyA.v, keyB.v, d);

        return FColor.Lerp(keyA.color, keyB.color, t);
    }

    public class Key
    {
        public double v;
        public FColor color;

        public Key(double v, FColor color)
        {
            this.v = v;
            this.color = color;
        }
    }    
}
