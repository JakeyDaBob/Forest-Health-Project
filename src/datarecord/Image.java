package datarecord;

import java.time.format.DateTimeFormatter;

import org.json.JObjectableAuto;
import org.json.JSONObject;
import org.json.JObjectable;

import java.io.Serializable;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

import generic.Vector3;

import java.util.Random;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Image implements JObjectable
{
    public String name;
    public byte[] data;

    public Image()
    {

    }

    @Override
    public void fromJson(JSONObject obj)
    {
        name = obj.optString("name");
        data = Base64.getDecoder().decode(obj.optString("data"));
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject obj = new JSONObject();

        obj.put("name", name);
        obj.put("data", Base64.getEncoder().encodeToString(data));

        return obj;
    }
}
