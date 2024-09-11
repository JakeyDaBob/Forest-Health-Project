package application;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import javax.imageio.ImageIO;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.awt.image.BufferedImage;


public class FileSystem
{
    static final String PathBaseMain = System.getProperty("user.dir") + "\\";
    static final String PathResources = "/resources/";

    public static FileSystem Resources = new FileSystem(Type.Resource, PathResources);
    public static FileSystem Base = new FileSystem(Type.Normal, PathBaseMain);

    public Type type;
    public String pathBase;

    public FileSystem(Type type, String pathBase)
    {
        this.type = type;
        this.pathBase = pathBase;
    }

    public String GetPath(String pathRelative)
    {
        return pathBase + pathRelative;
    }

    public boolean DirectoryCheck(String pathRelative)
    {
        if (!CanDoWrite(type))
        {
            return false;
        }

        String path = GetPath(pathRelative);
        System.out.println("Directory: " + path);

        Path directory = Paths.get(path);

        if (Files.exists(directory) && Files.isDirectory(directory))
        {
            System.out.println("directory already exists");
            return true;
        }

        try
        {
            Files.createDirectory(directory);
        }
        catch (IOException ex)
        {
            System.out.println("error creating dir");
            return false;
        }

        return true;
    }

    public BufferedImage GetImage(String pathRelative)
    {
        InputStream inputStream = GetInputStream(pathRelative);

        BufferedImage image = null;
        try
        {
            image = ImageIO.read(inputStream);
        }
        catch (IOException ex)
        {
            LogError(ex, pathRelative);
        }

        return image;
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
                LogError(ex, path);
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
                LogError(ex, path);
            }
        }

        return null;
    }

    public URL GetFileURL(String pathRelative)
    {
        String path = GetPath(pathRelative);

        if (type == Type.Resource)
        {
            return getClass().getResource(path);
        }
        else if (type == Type.Normal)
        {
                URL url = null;

            try
            {
                url = new File(path).toURI().toURL();
            }
            catch (MalformedURLException ex)
            {
                LogError(ex, path);
            }

            return url;
        }
        
        System.out.println("Unhandled type: " + type);
        return null;
    }

    void LogError(Exception ex, String path)
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

    public String[] GetAllFilesInDirectory(String pathRelative)
    {
        return type == Type.Normal ? GetAllFilesInDirectoryFile(pathRelative) : GetAllFilesInDirectoryResource(pathRelative);
    }

    String[] GetAllFilesInDirectoryFile(String pathRelative)
    {
        String path = GetPath(pathRelative);
        URL resourceDir = GetFileURL(pathRelative);

        List<String> fileList = new ArrayList<>();

        if (resourceDir != null)
        {
            File dir = new File(resourceDir.getPath());

            if (dir.isDirectory())
            {
                String[] files = Objects.requireNonNull(dir.list());
                for (String fileName : files)
                {
                    fileList.add(fileName);
                }
            }
            else
            {
                System.out.println("Path is not a directory: '"+path+"'");
            }
        }
        else
        {
            System.out.println("Directory not found: '"+path+"'");
        }

        String[] fileStrings = new String[fileList.size()];
        fileList.toArray(fileStrings);
        return fileStrings;
    }

    String[] GetAllFilesInDirectoryResource(String pathRelative)
    {
        List<String> fileList = new ArrayList<>();

        try 
        {
            String resourcePath = GetPath(pathRelative);
            URL resourceDirURL = getClass().getResource(resourcePath);

            if (resourceDirURL != null) 
            {
                if (resourceDirURL.getProtocol().equals("jar")) 
                {
                    try (java.nio.file.FileSystem fs = FileSystems.newFileSystem(URI.create("jar:" + resourceDirURL.getPath().substring(0, resourceDirURL.getPath().indexOf("!"))), new HashMap<>())) {
                        Path pathInJar = fs.getPath(resourcePath);
                        try (Stream<Path> paths = Files.walk(pathInJar, 1)) 
                        {
                            paths.filter(Files::isRegularFile)
                                 .forEach(p -> fileList.add(p.getFileName().toString()));
                        }
                    }
                }
                 else 
                 {
                    Path path = Paths.get(resourceDirURL.toURI());
                    try (Stream<Path> paths = Files.walk(path, 1)) 
                    {
                        paths.filter(Files::isRegularFile)
                             .forEach(p -> fileList.add(p.getFileName().toString()));
                    }
                }
            } 
            else 
            {
                System.out.println("Resource directory not found: " + resourcePath);
            }
        } 
        catch (URISyntaxException | IOException e)
        {
            LogError(e, GetPath(pathRelative));
            e.printStackTrace();
        }   

        String[] fileStrings = new String[fileList.size()];
        fileList.toArray(fileStrings);
        return fileStrings;
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
