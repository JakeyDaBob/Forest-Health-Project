package datarecord;

import java.time.LocalDateTime;

import org.json.JObjectableAuto;

import java.util.ArrayList;
import java.util.List;

import generic.Vector3;

import java.util.Random;

public class DataRecord extends JObjectableAuto
{
    public int id;
    public int userId;
    
    public Image image;
    public double geolocationLatitude, geolocationLongitude;

    public LocalDateTime dateTime;

    public LandscapePosition landscapePosition;

    public VegetationType vegetationType;
    public VegetationDevelopmentStage vegetationDevelopmentStage;

    public BurnSeverity burnSeverity;
    public RecoveryGroundLayer recoveryGround;
    public RecoveryShrubLayer recoveryShrub;
    public RecoveryLowerCanopyLayer recoveryLowerCanopy;
    public RecoveryUpperCanopyLayer recoveryUpperCanopy;
    public RecoveryEmergantLayer recoveryEmergantLayer;

    public FaunaRecord[] faunaRecords;

    public FloweringState floweringState;

    public double altitudeMeters, barometricPressureAtm;
    public double compassDirectionDegree;
    public Vector3 accelerometerData;

    public DataRecord()
    {
        image = new Image();
        dateTime = LocalDateTime.now().withNano(0);
        accelerometerData = new Vector3();
    }

    public DataRecord(Random random)
    {
        image = new Image();
        image.name = "IMG_" + random.nextInt(0,10000);
        image.data = new byte[100];
        random.nextBytes(image.data);

        dateTime = LocalDateTime.now().withNano(0);

        landscapePosition = LandscapePosition.values()[random.nextInt(LandscapePosition.values().length)];

        vegetationType = VegetationType.values()[random.nextInt(VegetationType.values().length)];
        vegetationDevelopmentStage = VegetationDevelopmentStage.values()[random.nextInt(VegetationDevelopmentStage.values().length)];

        
        burnSeverity = BurnSeverity.values()[random.nextInt(BurnSeverity.values().length)];
        recoveryGround = RecoveryGroundLayer.values()[random.nextInt(RecoveryGroundLayer.values().length)];
        recoveryShrub = RecoveryShrubLayer.values()[random.nextInt(RecoveryShrubLayer.values().length)];
        recoveryLowerCanopy = RecoveryLowerCanopyLayer.values()[random.nextInt(RecoveryLowerCanopyLayer.values().length)];
        recoveryUpperCanopy = RecoveryUpperCanopyLayer.values()[random.nextInt(RecoveryUpperCanopyLayer.values().length)];
        recoveryEmergantLayer = RecoveryEmergantLayer.values()[random.nextInt(RecoveryEmergantLayer.values().length)];

        List<FaunaRecord> faunaRecordsList = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            FaunaRecord faunaRecord = new FaunaRecord();
            faunaRecord.data = "Animal Name " + random.nextInt(100);
            faunaRecord.interactionType = FaunaRecord.InteractionType.values()[random.nextInt(FaunaRecord.InteractionType.values().length)];
            faunaRecord.type = FaunaRecord.Type.values()[random.nextInt(FaunaRecord.Type.values().length)];

            faunaRecordsList.add(faunaRecord);
        }
        faunaRecords = faunaRecordsList.toArray(new FaunaRecord[faunaRecordsList.size()]);

        floweringState = FloweringState.values()[random.nextInt(FloweringState.values().length)];
        altitudeMeters = random.nextDouble(500);
        barometricPressureAtm = random.nextDouble(850,1013);
        compassDirectionDegree = random.nextDouble(360);
        accelerometerData = new Vector3((float)random.nextDouble(-0.1,0.1), (float)random.nextDouble(-0.2,0.2), (float)random.nextDouble(-0.1,0.1));
    }

    public enum LandscapePosition
    {
        Flat,
        Hill,
        Slope,
        Valley
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
        Extreme
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

    public enum RecoveryLowerCanopyLayer
    {
        Unburnt,
        ShootsPresent,
        NoShootsPresent,
        NoLowerCanopyPresent
    }

    public enum RecoveryUpperCanopyLayer
    {
        Unburnt,
        ShootsPresent,
        NoShootsPresent,
        NoUpperCanopyPresent
    }

    public enum RecoveryEmergantLayer
    {
        Unburnt,
        BasalShootsPresent,
        EpicormicShootsPresent,
        EpicormicAndBasalShootsPresent,
        NoEpicormicOrBasalShootsPresent
    }

    public static Class<?> GetLayerRecovery(int layerIndex)
    {
        switch (layerIndex)
        {
            case 0: return RecoveryGroundLayer.class;
            case 1: return RecoveryShrubLayer.class;
            case 2: return RecoveryLowerCanopyLayer.class;
            case 3: return RecoveryUpperCanopyLayer.class;
            case 4: return RecoveryEmergantLayer.class;
        }

        throw new IllegalArgumentException("Invalid layer index " + layerIndex);
    }

    public String GetLayerRecoveryString(int layerIndex)
    {
        switch (layerIndex)
        {
            case 0: return recoveryGround.toString();
            case 1: return recoveryShrub.toString();
            case 2: return recoveryLowerCanopy.toString();
            case 3: return recoveryUpperCanopy.toString();
            case 4: return recoveryEmergantLayer.toString();
        }

        throw new IllegalArgumentException("Invalid layer index " + layerIndex);
    }

    public void SetLayerRecoveryString(int layerIndex, String str)
    {
        switch (layerIndex)
        {
            case 0:
                recoveryGround = RecoveryGroundLayer.valueOf(str);
                break;
            case 1:
                recoveryShrub = RecoveryShrubLayer.valueOf(str);
                break;
            case 2:
                recoveryLowerCanopy = RecoveryLowerCanopyLayer.valueOf(str);
                break;
            case 3:
                recoveryUpperCanopy = RecoveryUpperCanopyLayer.valueOf(str);
                break;
            case 4:
                recoveryEmergantLayer = RecoveryEmergantLayer.valueOf(str);
                break;
            default:
                throw new IllegalArgumentException("Invalid layer index " + layerIndex);
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
        None,
        Seed,
        Germination,
        Growth,
        Reproduction,
        Pollination,
        SeedSpreading
    }
}
