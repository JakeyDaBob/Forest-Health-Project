package org.json;

public interface JObjectable
{
    JSONObject toJson();
    void fromJson(JSONObject json);
}
