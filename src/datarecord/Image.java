package datarecord;

import org.json.JSONObject;
import org.json.JObjectable;

import java.util.Base64;

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
