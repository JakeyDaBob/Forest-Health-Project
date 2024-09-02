import window.MenuManager;
import window.MenuState;

import org.json.JObjectable;
import org.json.JSONObject;

import application.FileInfo;
import datarecord.DataRecord;
import test.ToiletEnjoyer;

import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        //MenuManager.Innit();
        //MenuManager.SetState(MenuState.Menu);

        JsonTest();
    }

    static void JsonToiletTest()
    {
        Random random = new Random();
        ToiletEnjoyer data = new ToiletEnjoyer(random);

        JSONObject jobj = data.toJson();
        String jString = jobj.toString();
        
        ToiletEnjoyer data2 = new ToiletEnjoyer();
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

        FileInfo.WriteAllLines("data/toiletenjoyer.json", jobj.toString());
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

        int minlen = Math.min(a.length(), a.length());

        for (int i = 0; i < minlen; i++)
        {
            if (a.charAt(i) != b.charAt(i))
            {
                System.out.println("Char diff i="+i + ": " + a.charAt(i) + " != " + b.charAt(i));

                if (a.length() != b.length())
                {
                    System.out.println("Length diff: " + a.length() + "!=" + b.length());
                }

                return false;
            }
        }

        if (a.length() != b.length())
        {
            System.out.println("Length diff: " + a.length() + "!=" + b.length());
            return false;
        }

        return true;
    }
}
