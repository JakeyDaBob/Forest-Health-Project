package generic;

import java.io.Serializable;

public class Vector3 implements Serializable
{
    public float x, y, z;

    public Vector3(float x, float y, float z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public Vector3()
    {
        x = y = z = 0f;
    }

    public Vector3(Vector3 other)
    {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public void set(int i, float f)
    {
        switch (i)
        {
            case 0: x = f; break;
            case 1: y = f; break;
            case 2: z = f; break;
        }

        throw new IllegalArgumentException("Invalid index " + i);
    }

    public float get(int i)
    {
        switch (i)
        {
            case 0: return x;
            case 1: return y;
            case 2: return z;
        }

        throw new IllegalArgumentException("Invalid index " + i);
    }

    public float Magnitude()
    {
        return Vector3.Distance(new Vector3(), this);
    }

    public static Vector3 Normalised(Vector3 v)
    {
        Vector3 a = new Vector3(v);
        float mag = a.Magnitude();
        for (int i = 0; i < 3; i++)
        {
            a.set(i, a.get(i) / mag);
        }

        return a;
    }

    public static float Distance(Vector3 a, Vector3 b)
    {
        float sum = 0f;
        for (int i = 0; i < 3; i++)
        {
            sum += Math.pow(Math.abs(a.x-b.x), 2);
        }

        return (float)Math.sqrt(sum);
    }

    public static Vector3 Add(Vector3 a, Vector3 b)
    {
        Vector3 v = new Vector3();
        for (int i = 0; i < 3; i++)
        {
            v.set(i, a.get(i)+b.get(i));
        }
        return v;
    }

    public static Vector3 Subtract(Vector3 a, Vector3 b)
    {
        Vector3 v = new Vector3();
        for (int i = 0; i < 3; i++)
        {
            v.set(i, a.get(i)-b.get(i));
        }
        return v;
    }

    public static Vector3 Multiply(Vector3 a, Vector3 b)
    {
        Vector3 v = new Vector3();
        for (int i = 0; i < 3; i++)
        {
            v.set(i, a.get(i)*b.get(i));
        }
        return v;
    }

    public static Vector3 Multiply(Vector3 a, float f)
    {
        Vector3 v = new Vector3();
        for (int i = 0; i < 3; i++)
        {
            v.set(i, a.get(i)*f);
        }
        return v;
    }
}
