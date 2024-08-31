package application;

public class FileInfo
{
    static final String PathBase = System.getProperty("user.dir") + "\\";
    static final String PathResources = "src/resources/";

    public static String GetPath(String pathRelative)
    {
        return PathBase + pathRelative;
    }
    
    public static String GetResource(String pathRelative)
    {
        return GetPath(PathResources + pathRelative);
    }
}
