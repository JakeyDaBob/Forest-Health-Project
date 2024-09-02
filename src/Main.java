import window.MenuManager;
import window.MenuState;

import org.json.JSONObject;

import application.FileInfo;
import datarecord.DataRecord;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        //MenuManager.Innit();
        //MenuManager.SetState(MenuState.Menu);

        JsonTest2();
    }

    static void JsonTest2()
    {
        Random random = new Random();
        DataRecord data = new DataRecord(random);

        JSONObject jobj = data.toJson();
        String jString = jobj.toString();

        System.out.println("JSON OUT:\n"+jString);

        FileInfo.WriteAllLines("data/testrecord.json", jobj.toString());
    }

    static void JsonTest()
    {
        Random random = new Random();
        DataRecord data = new DataRecord(random);

        JSONObject jobj = data.toJson();
        String jString = jobj.toString();
        
        DataRecord data2 = new DataRecord();
        data2.fromJson(new JSONObject(jString));
        JSONObject jobj2 = data2.toJson();
        String jString2 = jobj2.toString();

        boolean check = CompareStrings(jString, jString2);

        System.out.println("DATA CHECK: " + check);

        if (!check)
        {
            System.out.println("J1:\n" + jString);
            System.out.println("J2\n"+jString2);
        }

        FileInfo.WriteAllLines("data/testrecord.json", jobj.toString());
    }

    static boolean CompareStrings(String a, String b)
    {
        if (a.length() != b.length())
        {
            System.out.println("Length diff: " + a.length() + " != " + b.length());
            return false;
        }

        for (int i = 0; i < a.length(); i++)
        {
            if (a.charAt(i) != b.charAt(i))
            {
                System.out.println("Char diff i="+i + ": " + a.charAt(i) + " != " + b.charAt(i));
                return false;
            }
        }

        return true;
    }
}
