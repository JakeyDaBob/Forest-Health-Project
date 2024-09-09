package datarecord;

import org.json.JSONObject;
import org.json.JObjectable;

public class FaunaRecord implements JObjectable
{
    public Type type;
    public InteractionType interactionType;
    public String data;

    public FaunaRecord()
    {

    }

    public FaunaRecord(String data, Type type, InteractionType interactionType)
    {
        this.data = data;
        this.type = type;
        this.interactionType = interactionType;
    }

    public enum Type
    {
        SpeciesName,
        AnimalType
    }

    public enum InteractionType
    {
        Sighting,
        CallHeard
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject obj = new JSONObject();
        obj.put("type", type);
        obj.put("interactionType", interactionType);
        obj.put("data", data);
        return obj;
    }

    @Override
    public void fromJson(JSONObject obj)
    {
        type = Type.valueOf(obj.optString("type"));
        interactionType = InteractionType.valueOf(obj.optString("interactionType"));
        data = obj.optString("data");
    }

    @Override
    public String toString()
    {
        return "[" + data + ", " + type + ", " + interactionType + "]";
    }
}