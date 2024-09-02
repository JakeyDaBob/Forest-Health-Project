package application;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;

public class FileSystem
{
    static final String PathBaseMain = System.getProperty("user.dir") + "\\";
    static final String PathResources = PathBaseMain + "src/resources/";
    static final String PathBaseAbove = PathBaseMain + "../";

    public static FileSystem Base = new FileSystem(PathBaseMain);
    public static FileSystem Resources = new FileSystem(PathResources);
    public static FileSystem Above = new FileSystem(PathBaseAbove);

    public final String pathBase;

    public static String CheckSys(FileSystem fs)
    {
        return "Check: '" + fs.pathBase + "'\n";
    }

    public FileSystem(String pathBase)
    {
        this.pathBase = pathBase;
    }

    public String GetPath(String pathRelative)
    {
        return pathBase + pathRelative;
    }

    public void WriteAllLines(String pathRelative, String text)
    {
        String path = GetPath(pathRelative);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path)))
        {
            writer.write(text);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String ReadAllLines(String pathRelative)
    {
        String path = GetPath(pathRelative);

        String strOut = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(path)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                strOut += line + "\n";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }

        return strOut;
    }
}
