package datarecord;

import java.time.LocalDateTime;
import java.io.Serializable;

import generic.Vector3;
import jdon.Jdonable;

public class DataRecord implements Serializable
{
    public String imageName;
    public byte[] imageData;
    public double geolocationLatitude, geolocationLongitude;

    public LocalDateTime dateTime;

    public LandscapePosition landscapePosition;

    public VegetationType vegetationType;
    public VegetationDevelopmentStage vegetationDevelopmentStage;

    public BurnSeverity burnSeverity;
    public RecoveryGroundLayer recoveryGround;
    public RecoveryShrubLayer recoveryShrubLayer;
    public RecoverySubCanopyLayer recoverySubCanopy;
    public RecoveryEmergantLayer recoveryTallestTree;

    public FaunaRecord[] faunaRecords;

    public FloweringState floweringStatesPerLayer;

    public double altitudeMeters, barometricPressureAtm;
    public double compassDirectionDegree;
    public Vector3 accelerometerData;

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

    public class FaunaRecord implements Serializable, Jdonable
    {
        public Type type;
        public InteractionType interactionType;
        public String data;

        @Override
        public void JdonWrite(String str)
        {

        }

        @Override
        public String JdonRead()
        {
            return "";
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
