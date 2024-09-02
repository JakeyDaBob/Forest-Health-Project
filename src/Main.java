import java.io.InputStream;

import application.FileSystem;

import window.MenuManager;
import window.MenuState;

import test.*;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println(FileSystem.CheckSys(FileSystem.Resources));
        System.out.println(FileSystem.CheckSys(FileSystem.Base));
        System.out.println(FileSystem.CheckSys(FileSystem.Above));

        TestRes res = new test.TestRes();

        return;
    }
}
