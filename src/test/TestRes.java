package test;

import java.io.InputStream;

public class TestRes
{
    public TestRes()
    {
        System.out.println("Checking resources: ");

        String[] pathsToTest = new String[] {"/resources/forest0.png", "/existno.real"};
        
        for (String path : pathsToTest)
        {
            InputStream inputStream = getClass().getResourceAsStream(path);

            boolean check = inputStream != null;
            System.out.println(path + ": " + check);
        }
        
    }    
}
