package application;

import generic.GroupMember;

public class AppInfo
{
    public static final String Name = "Forest Health Project"; 

    public static final String CourseCode = "2813ICT";
    public static final String CourseName = "Software Engineering Fundamentals";
    
    public static final int GroupNumber = 1;
    public static final GroupMember[] GroupMembers = new GroupMember[]
    {
        new GroupMember("s5294409", "Jacob", "Matchett"),
        new GroupMember("s5289789", "Thomas", "Marion"),
        new GroupMember("s5244863", "William", "Wilson"),
        new GroupMember("s5372216", "Alexander", "Koningham"),
        new GroupMember("s5270077", "Tom", "McMillan")

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
}
