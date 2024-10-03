package generic;

public class Mathb
{
    //Linear interpolation
    public static float Lerp(float a, float b, float t)
    {
        return a + ((b - a) * t);
    }

    public static double Lerp(double a, double b, double t)
    {
        return a + ((b - a) * t);
    }

    //Inverse linear interpolation
    public static float InverseLerp(float a, float b, float v)
    {
        return (v - a) / (b - a);
    }

    public static double InverseLerp(double a, double b, double v)
    {
        return (v - a) / (b - a);
    }

    //Dot Product
    public static double Dot(double[] a, double[] b)
    {
        if (a.length != b.length)
        {
            throw new IllegalArgumentException();
        }

        double dot = 0.0;

        for (int i = 0; i < a.length; i++)
        {
            dot += a[i] * b[i];
        }

        return dot;
    }
}
