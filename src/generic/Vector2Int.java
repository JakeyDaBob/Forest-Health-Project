package generic;

//Vector class of two integers
public class Vector2Int
{
    public static final Vector2Int[] AdjacentOffsets;

    static
    {
        AdjacentOffsets = new Vector2Int[]
        {
            new Vector2Int(1,0),
            new Vector2Int(-1,0),
            new Vector2Int(0,1),
            new Vector2Int(0,-1)
        };
    }

    public int x;
    public int y;

    public Vector2Int(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2Int(Vector2Int other)
    {
        set(other);
    }

    public void set(Vector2Int other)
    {
        x = other.x;
        y = other.y;
    }

    //Adds two vectors
    public static Vector2Int Add(Vector2Int a, Vector2Int b)
    {
        return new Vector2Int(a.x + b.x, a.y + b.y);
    }

    //Subtracts two vectors
    public static Vector2Int Subtract(Vector2Int a, Vector2Int b)
    {
        return new Vector2Int(a.x - b.x, a.y - b.y);
    }

    //Calculates pythagorian magnitude
    public double Magnitude()
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || obj.getClass() != this.getClass())
        {
            return false;
        }

        Vector2Int other = (Vector2Int)obj;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}

