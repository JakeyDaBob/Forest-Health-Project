package application;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;

public class FileInfo
{
    static final String PathBase = System.getProperty("user.dir") + "\\" + "src/resources/";

    public static String GetPath(String pathRelative)
    {
        return PathBase + pathRelative;
    }

    public static void WriteAllLines(String pathRelative, String text)
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

    public static String ReadAllLines(String pathRelative)
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
