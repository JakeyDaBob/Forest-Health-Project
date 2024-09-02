package application;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FileSystem
{
    static final String PathBaseMain = System.getProperty("user.dir") + "\\";
    static final String PathResources = "/resources/";

    public static FileSystem Resources = new FileSystem(Type.Resource, PathResources);
    public static FileSystem Base = new FileSystem(Type.Normal, PathBaseMain);

    public Type type;
    public String pathBase;

    public static String CheckSys(FileSystem fs)
    {
        return "Check: '["+ fs.type +"] " + fs.pathBase + "'\n";
    }

    public FileSystem(Type type, String pathBase)
    {
        this.type = type;
        this.pathBase = pathBase;
    }

    public String GetPath(String pathRelative)
    {
        return pathBase + pathRelative;
    }

    public InputStream GetInputStream(String pathRelative)
    {
        String path = GetPath(pathRelative);
        if (type == Type.Normal)
        {
            try (InputStream inputStream = new FileInputStream(GetPath(path)))
            {
                return inputStream;
            }
            catch (IOException ex)
            {
                PerformIOError(ex, path);
            }
        }

        else if (type == Type.Resource)
        {
            try
            {
                InputStream inputStream = getClass().getResourceAsStream(path);

                if (inputStream == null)
                {
                    throw new FileNotFoundException();
                }

                return inputStream;
            }
            catch (IOException ex)
            {
                PerformIOError(ex, path);
            }
        }

        return null;
    }

    void PerformIOError(IOException ex, String path)
    {
        System.err.println(type + " error: '" + path + "' ("+ ex.getClass().getName() +")");
    }

    public void WriteAllLines(String pathRelative, String text)
    {
        if (!CanDoWriteCheck())
        {
            return;
        }

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

        InputStream inputStream = GetInputStream(path);
        if (inputStream == null)
        {
            return "";
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        try (BufferedReader reader = new BufferedReader(inputStreamReader))
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

    boolean CanDoWriteCheck()
    {
        if (CanDoWrite(type))
        {
            return true;
        }

        System.err.println("FileSystem of type: " + type + " cannot write files!");

        return false;
    }

    static boolean CanDoWrite(Type type)
    {
        return type == Type.Normal;
    }

    public enum Type
    {
        Normal,
        Resource
    }
}
