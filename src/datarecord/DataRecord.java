package datarecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JObjectable;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

import generic.Vector3;

import java.util.Random;
import java.lang.reflect.Field;

public class DataRecord implements Serializable, JObjectable
{
    public Image image;
    public double geolocationLatitude, geolocationLongitude;

    public LocalDateTime dateTime;

    public LandscapePosition landscapePosition;

    public VegetationType vegetationType;
    public VegetationDevelopmentStage vegetationDevelopmentStage;

    public BurnSeverity burnSeverity;
    public RecoveryGroundLayer recoveryGround;
    public RecoveryShrubLayer recoveryShrub;
    public RecoverySubCanopyLayer recoverySubCanopy;
    public RecoveryEmergantLayer recoveryTallestTree;

    public FaunaRecord[] faunaRecords;

    public FloweringState floweringStatesPerLayer;

    public double altitudeMeters, barometricPressureAtm;
    public double compassDirectionDegree;
    public Vector3 accelerometerData;

    public DataRecord()
    {

    }

    public DataRecord(Random random)
    {
        image = new Image();
        image.name = "IMG_" + random.nextInt(0,10000);
        image.data = new byte[500];
        random.nextBytes(image.data);

        dateTime = LocalDateTime.now();

        landscapePosition = LandscapePosition.values()[random.nextInt(LandscapePosition.values().length)];

        vegetationType = VegetationType.values()[random.nextInt(VegetationType.values().length)];
        vegetationDevelopmentStage = VegetationDevelopmentStage.values()[random.nextInt(VegetationDevelopmentStage.values().length)];

        
        burnSeverity = BurnSeverity.values()[random.nextInt(BurnSeverity.values().length)];
        recoveryGround = RecoveryGroundLayer.values()[random.nextInt(RecoveryGroundLayer.values().length)];
        recoveryShrub = RecoveryShrubLayer.values()[random.nextInt(RecoveryShrubLayer.values().length)];
        recoverySubCanopy = RecoverySubCanopyLayer.values()[random.nextInt(RecoverySubCanopyLayer.values().length)];
        recoveryTallestTree = RecoveryEmergantLayer.values()[random.nextInt(RecoveryEmergantLayer.values().length)];

        List<FaunaRecord> faunaRecordsList = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            if ((random.nextInt(100)+1) > 100)
            {
                break;
            }

            FaunaRecord fauna = new FaunaRecord();
            fauna.data = "Animal Name " + random.nextInt(100);
            fauna.interactionType = FaunaRecord.InteractionType.values()[random.nextInt(FaunaRecord.InteractionType.values().length)];
            fauna.type = FaunaRecord.Type.values()[random.nextInt(FaunaRecord.Type.values().length)];
        }
        faunaRecords = new FaunaRecord[faunaRecordsList.size()];
        faunaRecordsList.toArray(faunaRecords);

        accelerometerData = new Vector3(1,2,3);
    }

    @Override
    public void fromJson(JSONObject obj)
    {
        /*
        image = new Image();
        image.fromJson(obj.getJSONObject("image"));

        geolocationLatitude = obj.optDouble("geoLatitude");
        geolocationLongitude = obj.optDouble("geoLongitude");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTime = LocalDateTime.parse(obj.optString("dateTime"), formatter);

        landscapePosition = LandscapePosition.valueOf(obj.optString("landscapePosition"));
        */
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject obj = new JSONObject();
        
        /*
        obj.put("image", image.toJson());
        obj.put("geoLatitude", geolocationLatitude);
        obj.put("geoLongitude", geolocationLongitude);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = dateTime.format(formatter);
        obj.put("dateTime", dateTimeString);

        obj.put("landscapePosition", landscapePosition);
        
        return obj;
        */

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                Class<?> type = field.getType();

                field.setAccessible(true);
                Object value = field.get(this);

                boolean isJObjectable = false;
                boolean isJObjectableArray = false;

                //Use field.isArray()

                Class<?>[] interfaces = type.getInterfaces();
                for (Class<?> i : interfaces)
                {
                    if (i == JObjectable.class)
                    {
                        isJObjectable = true;
                    }
                    else if (i == JObjectable[].class)
                    {
                        isJObjectableArray = true;
                    }
                }

                //JObjectable?
                if (isJObjectable)
                {
                    System.out.println(field.getName() + " Jobjectable");
                    JObjectable jobjectable = (JObjectable)value;
                    obj.put(field.getName(), jobjectable.toJson());
                }
                else if (isJObjectableArray)
                {
                    System.out.println(field.getName() + " Jobjectable Array");
                    JObjectable[] jobjs = (JObjectable[])value;
                    
                    for (JObjectable jobj : jobjs)
                    {
                        obj.put(field.getName(), jobj.toString());
                    }
                }
                //Anything else lol
                else
                {
                    System.out.println(field.getName() + "Something idk lol");
                    obj.put(field.getName(), value);
                }
            }
            catch (IllegalAccessException ex)
            {
                System.err.println("Access error on field: " + field.getName());
                ex.printStackTrace();
            }
        }

        return obj;
    }

    public enum LandscapePosition
    {
        Flat,
        Hill,
        Slope,
        Valley
    }

    public class Image implements JObjectable
    {
        public String name;
        public byte[] data;

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

    public enum VegetationType
    {
        EucalyptForestFernOrHerb,
        EucalyptForestGrassy,
        EucalyptForestShrubby,
        Rainforest,
        Riparian
    }

    public enum VegetationDevelopmentStage
    {
        Old,
        Mature,
        Regrowth,
        Mixed,
        FewTreesPresent
    }

    public enum BurnSeverity
    {
        Unburnt,
        Low,
        Moderate,
        High,
        Exetreme
    }

    public enum RecoveryGroundLayer
    {
        Unburnt,
        NewGrowthVisible,
        NoNewGrowth,
        NoGroundCoverPresent
    }

    public enum RecoveryShrubLayer
    {
        Unburnt,
        ShootsPresent,
        SeedlingsPresent,
        BothShootsAndSeedlingsPresent,
        NoShootsOrSeedlingsPresent,
        NoShrubLayerPresent
    }

    public enum RecoverySubCanopyLayer
    {
        Unburnt,
        ShootsPresent,
        NoShootsPresent,
        NoSubCanopyPresent
    }

    public enum RecoveryEmergantLayer
    {
        Unburnt,
        BasalShootsPresent,
        EpicormicShootsPresent,
        EpicormicAndBasalShootsPresent,
        NoEpicormicOrBasalShootsPresent
    }

    public class FaunaRecord implements Serializable
    {
        public Type type;
        public InteractionType interactionType;
        public String data;

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
    }

    public enum Layers
    {
        Ground,
        Shrub,
        SubCanopy,
        UpperCanopy,
        Emergent,
    }

    public enum FloweringState
    {
        Seed,
        Germination,
        Growth,
        Reproduction,
        Pollination,
        SeedSpreading
    }
}
