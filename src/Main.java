import application.SqlManager;
import datarecord.DataRecord;
import window.MenuManager;
import window.MenuState;

import java.util.Random;

import org.json.JSONObject;

public class Main
{
    public static void main(String[] args)
    {
        MenuManager.Innit(MenuState.Splash);
    }

    static void TestDataCheck()
    {
        DataRecord dr = new DataRecord(new Random());

        SqlManager.AddDataRecord(dr);
        int drid = SqlManager.LastDataRecordAddId;

        DataRecord dr2 = SqlManager.GetDataRecord(drid);

        String str1 = dr.toJson().toString();
        String str2 = dr2.toJson().toString();

        int lenmin = Math.min(str1.length(), str2.length());
        boolean datacheck = str1.length() != str2.length();
        int i = 0;
        for (i = 0; i < lenmin; i++)
        {
            if (str1.charAt(i) != str2.charAt(i))
            {
                datacheck = false;
                break;
            }
        }

        if (datacheck)
        {
            System.out.println("DATA CHECK TROOO YEAHHHHHHH YEAHHHH WOIOOOOO YEAHHH");
        }
        else
        {
            System.out.println("Data check false :(. i=)" + i);
            System.out.println();
            System.out.println(str1);
            System.out.println();
            System.out.println(str2);
        }
    }

    static void TestConnect()
    {
        SqlManager.Connect();
    }

    static void TestUpload()
    {
        DataRecord dr = new DataRecord(new Random());

        SqlManager.AddDataRecord(dr);
    }

    static void TestDownload()
    {
        int id = 5;

        DataRecord dr = SqlManager.GetDataRecord(id);

        if (dr == null)
        {
            System.out.println("Datarecord download failed id: " + id);
            return;
        }

        JSONObject jobj = dr.toJson();
        System.out.println("Downloaded " + id);
        System.out.println(jobj);
    }
}
