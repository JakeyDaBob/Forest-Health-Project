package test;
import java.lang.reflect.Field;

import generic.Vector3;

import java.lang.IllegalAccessException;
import java.util.Random;
import java.time.LocalDateTime;

import org.json.JObjectableAuto;
import org.json.JSONObject;
import org.json.JObjectable;
public class ToiletEnjoyer extends JObjectableAuto
{
    public String firstName;
    public String lastName;
    public int age;
    public float chodeSize;
    public double height;
    public boolean gender;//Based??
    public char favouriteCharacter;
    public Vector3 position;
    public LocalDateTime birthDateTime;

    public int[] coolNumbersTheyLike;

    public Toilet mainToilet;
    public Toilet[] otherToilets;

    public ToiletEnjoyer()
    {

    }

    public ToiletEnjoyer(Random random)
    {
        firstName = "Tom";
        lastName = "Toilet Enjoyer";
        age = 32;
        chodeSize = 4.4f;
        height = 120.95;
        gender = false;
        favouriteCharacter = 'f';
        position = new Vector3(69,420,369);

        coolNumbersTheyLike = new int[] {1,2,5,23,6,7,3,56,23,5,7};
        mainToilet = new Toilet(1,true,0.5f);
        otherToilets = new Toilet[] { new Toilet(69,true,0), new Toilet(70,true,0.1f) };

        birthDateTime = LocalDateTime.now();
    }
}
