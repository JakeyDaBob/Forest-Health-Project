package test;
import org.json.*;

public class Toilet implements JObjectable
    {
        public int colorId;
        public boolean flushable;
        public float waterLevel;

        public Toilet()
        {
           
        }

        public Toilet(int colorId, boolean flushable, float waterLevel)
        {
            this.colorId=colorId;
            this.flushable=flushable;
            this.waterLevel=waterLevel;
        }

        @Override
        public void fromJson(JSONObject obj)
        {
            colorId = obj.optInt("colorId");
            flushable = obj.optBoolean("flushable");
            waterLevel = obj.optFloat("waterLevel");
        }

        @Override
        public JSONObject toJson()
        {
            JSONObject obj = new JSONObject();

            obj.put("colorId", colorId);
            obj.put("flushable", flushable);
            obj.put("waterLevel", waterLevel);

            return obj;
        }

    }