package generic;

//This class exists because java.awt.color is really annoying to work with
//Used floats that range from 0 to 1 instead of integers from 0 to 255
public class FColor
{
    public static final int ChannelCount = 3;

    public float r;
    public float g;
    public float b;

    public FColor(float r, float g, float b)
    {
        this.r=r;
        this.g=g;
        this.b=b;

        Validate();
    }
    public FColor(FColor other)
    {
        r = other.r;
        g = other.g;
        b = other.b;
    }

    //Ensures all channels are between 0 and 1
    public void Validate()
    {
        r = Math.clamp(r, 0f, 1f);
        g = Math.clamp(g, 0f, 1f);
        b = Math.clamp(b, 0f, 1f);
    }

    //Returns the nth channel
    public float get(int index)
    {
        switch (index)
        {
            case 0: return r;
            case 1: return g;
            case 2: return b;
            default: throw new IllegalArgumentException();
        }
    }

    //Sets the nth channel
    public void set(int index, float f)
    {
        f = Math.clamp(f, 0f, 1f);

        switch (index)
        {
            case 0:
                r = f;
                break;

            case 1:
                g = f;
                break;
            
            case 2:
                b = f;
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    //Converts to AWT Color
    public java.awt.Color ToAwtColor()
    {
        return new java.awt.Color((int)(r*255), (int)(g*255), (int)(b*255));
    }

    public String ToHex() 
    {
        return String.format("#%02X%02X%02X", (int)(r*255), (int)(g*255), (int)(b*255));
    }

    //Adds two colors
    public static FColor Add(FColor colorA, FColor colorB)
    {
        FColor result = new FColor(0f, 0f, 0f);
        for (int i = 0; i < ChannelCount; i++)
        {
            result.set(i, colorA.get(i) + colorB.get(i));
        }

        result.Validate();

        return result;
    }

    //Multiplies two colors
    public static FColor Multiply(FColor colorA, FColor colorB)
    {
        FColor result = new FColor(0f,0f,0f);
        for (int i = 0; i < ChannelCount; i++)
        {
            result.set(i, colorA.get(i) * colorB.get(i));
        }

        result.Validate();

        return result;
    }

    //Multiplies a color by a scalar
    public static FColor Multiply(FColor color, float f)
    {
        FColor result = new FColor(0f,0f,0f);
        for (int i = 0; i < ChannelCount; i++)
        {
            result.set(i, color.get(i) * f);
        }

        result.Validate();

        return result;
    }

    //Linear interpolates two colors
    public static FColor Lerp(FColor colorA, FColor colorB, float t)
    {
        FColor result = new FColor(0f, 0f, 0f);
        for (int i = 0; i < ChannelCount; i++)
        {
            result.set(i, Mathb.Lerp(colorA.get(i), colorB.get(i), t));
        }

        result.Validate();

        return result;
    }
}
