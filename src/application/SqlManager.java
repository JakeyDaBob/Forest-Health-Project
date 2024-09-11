package application;

import generic.Callback;
import datarecord.DataRecord;
import datarecord.DataRecordPreview;
import datarecord.FaunaRecord;

import java.util.concurrent.CompletableFuture;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.Array;

public class SqlManager
{
    public static Callback OnUpload = new Callback();

    static final String Hostname = "58.161.74.181";
    static final String Port = "5432";
    static final String DatabaseName = "test";
    static final String Username = "postgres";
    static final String Password = "SKIBIDI42";
    static final String Url = "jdbc:postgresql://"+Hostname+":"+Port+"/"+DatabaseName;

    static final String TableDataRecords = "public.\"DataRecords\"";
    static final String TableFaunaRecords = "public.\"FaunaRecords\"";
    static final String TableUsers = "public.\"Users\"";

    public static int LastDataRecordAddId = -1;
    public static Callback OnDataRecordAdd = new Callback();
    public static boolean OnDataRecordAddState = false;

    public static Callback OnDataRecordPreviewsGet = new Callback();
    public static boolean OnDataRecordPreviewsGetState = false;
    public static DataRecordPreview[] OnDataRecordPreviewsGetResult;

    public static Callback OnDataRecordGet = new Callback();
    public static boolean OnDataRecordGetState = false;
    public static DataRecord OnDataRecordGetResult;

    public static void DeleteAllDataRecords()
    {
        System.out.println("URL: " + Url);
        System.out.println("Username: " + Username + ", Password: " + Password);

        try (Connection connection = DriverManager.getConnection(Url, Username, Password))
        {
            System.out.println("Connected to postgreserver !!!");

            Statement statement = connection.createStatement();

            String query = "DELETE FROM " + TableDataRecords;
            ResultSet resultSet = statement.executeQuery(query);

            PrintResultSet(resultSet);
        }
        catch (Exception ex)
        {
            System.out.println("Connection issue:");
            ex.printStackTrace();
        }
    }

    public static void Connect()
    {
        System.out.println("URL: " + Url);
        System.out.println("Username: " + Username + ", Password: " + Password);

        try (Connection connection = DriverManager.getConnection(Url, Username, Password))
        {
            System.out.println("Connected to postgreserver !!!");

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + TableDataRecords;
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Results: ");

            PrintResultSet(resultSet);
        }
        catch (Exception ex)
        {
            System.out.println("Connection issue:");
            ex.printStackTrace();
        }
    }

    public static void GetDataRecordPreviewsAsync()
    {
        CompletableFuture<DataRecordPreview[]> future = CompletableFuture.supplyAsync(
        () ->
        {
            return GetDataRecordPreviews();
        });

        future.thenAccept(previews -> { GetDataRecordPreviewsEnd(previews); });
    }

    static void GetDataRecordPreviewsEnd(DataRecordPreview[] previews)
    {
        OnDataRecordPreviewsGetState = previews != null;
        OnDataRecordPreviewsGetResult = previews;
        OnDataRecordPreviewsGet.Invoke();
    }

    public static DataRecordPreview[] GetDataRecordPreviews()
    {
        List<DataRecordPreview> previewList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(Url, Username, Password))
        {
            Statement statement = connection.createStatement();

            String queryDataRecord = "SELECT \"id\", \"dateTime\" FROM " + TableDataRecords;

            ResultSet resultSet = statement.executeQuery(queryDataRecord);

            while (resultSet.next())
            {
                DataRecordPreview preview = GetDataRecordPreviewFromResultSet(resultSet);

                if (preview != null)
                {
                    previewList.add(preview);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Connection issue:");
            ex.printStackTrace();
            return null;
        }

        DataRecordPreview[] previewArray = new DataRecordPreview[previewList.size()];
        previewList.toArray(previewArray);

        return previewArray;
    }

    static DataRecordPreview GetDataRecordPreviewFromResultSet(ResultSet set)
    {
        DataRecordPreview dr = new DataRecordPreview();

        try
        {
            dr.id = set.getInt("id");

            Timestamp timestamp = set.getTimestamp("dateTime");
            dr.dateTime = timestamp.toLocalDateTime();
        }
        catch (Exception ex)
        {
            System.err.println("Error reading DataRecordPreview");
            ex.printStackTrace();

            dr = null;
        }

        return dr;
    }

    public static void GetDataRecordAsync(int id)
    {
        CompletableFuture<DataRecord> future = CompletableFuture.supplyAsync(
        () ->
        {
            return GetDataRecord(id);
        });

        future.thenAccept(dataRecord -> { GetDataRecordEnd(dataRecord); });
    }

    public static void GetDataRecordEnd(DataRecord dataRecord)
    {
        OnDataRecordGetState = dataRecord != null;
        OnDataRecordGetResult = dataRecord;
        OnDataRecordGet.Invoke();
    }

    public static DataRecord GetDataRecord(int id)
    {
        DataRecord dr = null;

        try (Connection connection = DriverManager.getConnection(Url, Username, Password))
        {
            Statement statement = connection.createStatement();

            String queryDataRecord = "SELECT * FROM " + TableDataRecords + " WHERE id = " + id;

            ResultSet resultSetDataRecord = statement.executeQuery(queryDataRecord);

            if (!resultSetDataRecord.next())
            {
                System.err.println("No datarecords found of id " + id);
                return null;
            }

            dr = CreateDataRecordFromResultSets(resultSetDataRecord);

            if (dr == null)
            {
                System.err.println("Failed to create data record");
                return null;
            }

            Statement statement2 = connection.createStatement();

            String queryFaunaRecords = "SELECT * FROM " + TableFaunaRecords + " WHERE \"idDataRecord\" = " + id;

            ResultSet resultSetFaunaRecord = statement2.executeQuery(queryFaunaRecords);

            List<FaunaRecord> faunaRecords = new ArrayList<>();

            while (resultSetFaunaRecord.next())
            {
                FaunaRecord faunaRecord = CreateFaunaRecordFromResultSet(resultSetFaunaRecord);
                faunaRecords.add(faunaRecord);
                System.out.println("Fauna record read: " + faunaRecord.data);
            }

            dr.faunaRecords = new FaunaRecord[faunaRecords.size()];
            faunaRecords.toArray(dr.faunaRecords);
        }
        catch (Exception ex)
        {
            System.out.println("Connection issue:");
            ex.printStackTrace();
            dr = null;
        }

        return dr;
    }

    public static void AddFaunaRecord(int dataRecordId, FaunaRecord faunaRecord, Connection connection)
    {
        String query = "INSERT INTO " + TableFaunaRecords + " (\"idDataRecord\", \"type\", \"interactionType\", \"data\") VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1, dataRecordId);
            preparedStatement.setString(2, faunaRecord.type.toString());
            preparedStatement.setString(3, faunaRecord.interactionType.toString());
            preparedStatement.setString(4, faunaRecord.data);

            int affectedRows = preparedStatement.executeUpdate();

            // Retrieve generated keys (auto-increment values)
            if (affectedRows > 0)
            {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys())
                {
                    if (generatedKeys.next())
                    {
                        long generatedId = generatedKeys.getLong(1);
                        System.out.println("Added Fauna Record with ID: " + generatedId);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Issue:");
            ex.printStackTrace();
        }
    }

    public static void AddDataRecordAsync(DataRecord dr)
    {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(
        () ->
        {
            return AddDataRecord(dr);
        });

        future.thenAccept(state -> { AddDataRecordEnd(state); });
    }

    static void AddDataRecordEnd(boolean state)
    {
        OnDataRecordAddState = state;
        OnDataRecordAdd.Invoke();
    }

    public static boolean AddDataRecord(DataRecord dr)
    {
        try (Connection connection = DriverManager.getConnection(Url, Username, Password))
        {
            System.out.println("Connected to postgreserver !!!");

            String query = "INSERT INTO " + TableDataRecords + " (\"userid\", \"geolocationData\", \"dateTime\", \"landscapePosition\", \"vegetationType\", \"vegetationDevelopmentStage\", \"burnSeverity\", \"recoveryStages\", \"floweringState\", \"altitudeMeters\", \"barometricPressureAtm\", \"compassDirectionDegree\", \"accelerometerData\", \"imageName\", \"imageData\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
            {
                Object[] geolocationData = new Object[] { dr.geolocationLatitude, dr.geolocationLongitude };
                Array arrayGeolocation = connection.createArrayOf("float8", geolocationData);

                preparedStatement.setInt(1, 0);
                preparedStatement.setArray(2, arrayGeolocation);

                Timestamp timestamp = Timestamp.valueOf(dr.dateTime.withNano(0));
                preparedStatement.setTimestamp(3, timestamp);
                preparedStatement.setString(4, dr.landscapePosition.toString());
                preparedStatement.setString(5, dr.vegetationType.toString());
                preparedStatement.setString(6, dr.vegetationDevelopmentStage.toString());
                preparedStatement.setString(7, dr.burnSeverity.toString());

                Object[] recoveryStages = new Object[5];
                for (int i = 0; i < recoveryStages.length; i++)
                {
                    recoveryStages[i] = dr.GetLayerRecoveryString(i);
                }
                Array arrayRecoveryStages = connection.createArrayOf("text", recoveryStages);

                preparedStatement.setArray(8, arrayRecoveryStages);

                //floweringState, altitudeMeters, barometricPressureAtm, compassDirectionDegree, accelerometerData, imageName, imageData

                preparedStatement.setString(9, dr.floweringState.toString());
                preparedStatement.setDouble(10, dr.altitudeMeters);
                preparedStatement.setDouble(11, dr.barometricPressureAtm);
                preparedStatement.setDouble(12, dr.compassDirectionDegree);
                
                Object[] accelerometerData = new Object[3];
                for (int i = 0; i < accelerometerData.length; i++)
                {
                    accelerometerData[i] = dr.accelerometerData.get(i);
                }
                Array arrayAccelerometerData = connection.createArrayOf("float8", accelerometerData);

                preparedStatement.setArray(13, arrayAccelerometerData);
                
                preparedStatement.setString(14, dr.image.name);
                preparedStatement.setBytes(15, dr.image.data);

                int affectedRows = preparedStatement.executeUpdate();

                // Retrieve generated keys (auto-increment values)
                int generatedId = -1;
                if (affectedRows > 0)
                {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys())
                    {
                        if (generatedKeys.next())
                        {
                            generatedId = generatedKeys.getInt(1);
                            System.out.println("Added Data Record with ID: " + generatedId);
                        }
                    }
                }

                LastDataRecordAddId = generatedId;

                if (generatedId == -1)
                {
                    System.err.println("Failed to add data record");
                    return false;
                }

                //Fauna records:
                for (int i = 0; i < dr.faunaRecords.length; i++)
                {
                    AddFaunaRecord(generatedId, dr.faunaRecords[i], connection);
                }
            }

        }
        catch (Exception ex)
        {
            System.out.println("Issue:");
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public static FaunaRecord CreateFaunaRecordFromResultSet(ResultSet set)
    {
        FaunaRecord fr = new FaunaRecord();

        try
        {
            fr.data = set.getString("data");
            fr.type = FaunaRecord.Type.valueOf(set.getString("type"));
            fr.interactionType = FaunaRecord.InteractionType.valueOf(set.getString("interactionType"));
        }
        catch (Exception ex)
        {
            System.err.println("Error reading DataRecord");
            ex.printStackTrace();

            fr = null;
        }

        return fr;
    }

    public static DataRecord CreateDataRecordFromResultSets(ResultSet set)
    {
        DataRecord dr = new DataRecord();

        try
        {
            dr.id = set.getInt("id");
            dr.userId = set.getInt("userId");
            
            dr.image.data = set.getBytes("imageData");
            dr.image.name = set.getString("imageName");
            //dr.image.data = new byte[0];
            //dr.image.name = "NotDone.png";

            Object[] geolocationDataObject = (Object[])set.getArray("geolocationData").getArray();
            double[] geolocationData = Arrays.stream(geolocationDataObject).mapToDouble(o -> ((Number) o).doubleValue()).toArray();
            dr.geolocationLatitude = geolocationData[0];
            dr.geolocationLongitude = geolocationData[1];

            Timestamp timestamp = set.getTimestamp("dateTime");
            dr.dateTime = timestamp.toLocalDateTime();

            dr.landscapePosition = DataRecord.LandscapePosition.valueOf(set.getString("landscapePosition"));
            dr.vegetationType = DataRecord.VegetationType.valueOf(set.getString("vegetationType"));
            dr.vegetationDevelopmentStage = DataRecord.VegetationDevelopmentStage.valueOf(set.getString("vegetationDevelopmentStage"));

            dr.burnSeverity = DataRecord.BurnSeverity.valueOf(set.getString("burnSeverity"));

            Object[] layerRecoveryStagesObject = (Object[])set.getArray("recoveryStages").getArray();
            String[] layerRecoveryStages = Arrays.stream(layerRecoveryStagesObject).map(Object::toString).toArray(String[]::new);

            for (int i = 0; i < layerRecoveryStages.length; i++)
            {
                dr.SetLayerRecoveryString(i, layerRecoveryStages[i]);
            }

            dr.floweringState = DataRecord.FloweringState.valueOf(set.getString("floweringState"));
            dr.altitudeMeters = set.getDouble("altitudeMeters");
            dr.barometricPressureAtm = set.getDouble("barometricPressureAtm");
            dr.compassDirectionDegree = set.getDouble("compassDirectionDegree");

            Object[] accelerometerDataObject = (Object[])set.getArray("accelerometerData").getArray();
            double[] accelerometerData = Arrays.stream(accelerometerDataObject).mapToDouble(o -> ((Number) o).doubleValue()).toArray();

            for (int i = 0; i < 3; i++)
            {
                dr.accelerometerData.set(i, (float)accelerometerData[i]);
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error reading DataRecord");
            ex.printStackTrace();

            dr = null;
        }

        return dr;
    }

    public static void PrintResultSet(ResultSet resultSet) throws Exception
    {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Print column names
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + ", ");
        }
        System.out.println();

        // Print rows
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + ", ");
            }
            System.out.println();
        }
    }

}
