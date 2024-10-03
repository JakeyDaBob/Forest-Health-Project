package application;

import java.util.Random;
import generic.GroupMember;
import java.awt.image.BufferedImage;

public class AppInfo
{
    public static AppMode Mode = AppMode.Citizen;
    public static final String Name = "Forest Health Project"; 

    public static final String CourseCode = "2813ICT";
    public static final String CourseName = "Software Engineering Fundamentals";

    public static final String ContactMobileSupport = "07 5555 1234";
    
    public static final int GroupNumber = 1;
    public static final GroupMember[] GroupMembers = new GroupMember[]
    {
        new GroupMember("s5294409", "Jacob", "Matchett"),
        new GroupMember("s5289789", "Thomas", "Marion"),
        new GroupMember("s5244863", "William", "Wilson"),
        new GroupMember("s5372216", "Alexander", "Koningham"),
        new GroupMember("s5270077", "Tom", "McMillan"),
    };

    public static final String GroupMembersToString()
    {
        String str = "";

        for (int i = 0; i < GroupMembers.length; i++)
        {
            String suffix = i < GroupMembers.length-2 ? ", " : " and ";
            str += GroupMembers[i].firstName + " " + GroupMembers[i].lastName;

            if (i != GroupMembers.length-1)
            {
                str += suffix;
            }
        }

        return str;
    }

    public static BufferedImage GetBackgroundImage()
    {
        Random random = new Random();

        String path = AppInfo.Mode == AppMode.Citizen ? "photos" : "photoswide";

        String[] imagePaths = FileSystem.Resources.GetAllFilesInDirectory(path);
        String imageFileName = imagePaths[random.nextInt(imagePaths.length)];
        String imagePath = path+"/"+imageFileName;

        return FileSystem.Resources.GetImage(imagePath);
    }

    public static BufferedImage GetSplashImage()
    {
        String imageName = Mode == AppMode.Citizen ? "splash1.png" : "splash2.png";
        return FileSystem.Resources.GetImage("splash/" + imageName);
    }
}
