package window.panels;

import java.awt.*;
import javax.swing.*;

import org.json.JSONObject;

import datarecord.*;
import application.*;

import java.awt.event.*;
import java.util.*;
import generic.*;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;
import window.elements.*;

public class SciAnalysis extends JLayeredPane
{
    public SciAnalysis(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        DrawImage image = new DrawImage(AppInfo.GetBackgroundImage());
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel("Analysis", 0, 20, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 50;
        JButton buttonBack = WindowUtil.CreateButton("Back", getWidth()/2, getHeight()-150, (int)(getWidth()*0.2f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MenuManager.SetState(MenuState.Menu);
            }
        });
        add(buttonBack, JLayeredPane.MODAL_LAYER);

        //Load all datarecords into memory
        String[] fileNames = FileSystem.Base.GetAllFilesInDirectory("outdata/scidatarecords/");

        DataRecord[] records = new DataRecord[fileNames.length];
        for (int i = 0; i < records.length; i++)
        {
            String path = "outdata/scidatarecords/" + fileNames[i];

            String str = FileSystem.Base.ReadAllLines(path);
            JSONObject jObj = new JSONObject(str);

            DataRecord record = new DataRecord();
            record.fromJson(jObj);

            records[i] = record;
        }

        //Perform Stats
        int[] countsLandscapePosition = new int[DataRecord.LandscapePosition.values().length];
        int[] countsVegetationType = new int[DataRecord.VegetationType.values().length];
        int[] countsVegetationStage = new int[DataRecord.VegetationDevelopmentStage.values().length];

        int[] countsBurnSev = new int[DataRecord.BurnSeverity.values().length];
        int[] countsRecoveryGround = new int[DataRecord.RecoveryGroundLayer.values().length];
        int[] countsRecoveryShrub = new int[DataRecord.RecoveryShrubLayer.values().length];
        int[] countsRecoveryLowerCanopy = new int[DataRecord.RecoveryLowerCanopyLayer.values().length];
        int[] countsRecoveryUpperCanopy = new int[DataRecord.RecoveryUpperCanopyLayer.values().length];
        int[] countsRecoveryEmergant = new int[DataRecord.RecoveryEmergantLayer.values().length];

        int[] countsFloweringState = new int[DataRecord.FloweringState.values().length];

        int[] countsFaunaType = new int[FaunaRecord.InteractionType.values().length];
        Map<String, Integer> faunaMap = new HashMap<>();

        for (DataRecord dr : records)
        {
            countsLandscapePosition[dr.landscapePosition.ordinal()]++;
            countsVegetationType[dr.vegetationType.ordinal()]++;
            countsVegetationStage[dr.vegetationDevelopmentStage.ordinal()]++;

            countsBurnSev[dr.burnSeverity.ordinal()]++;
            countsRecoveryGround[dr.recoveryGround.ordinal()]++;
            countsRecoveryShrub[dr.recoveryShrub.ordinal()]++;
            countsRecoveryLowerCanopy[dr.recoveryLowerCanopy.ordinal()]++;
            countsRecoveryUpperCanopy[dr.recoveryUpperCanopy.ordinal()]++;
            countsRecoveryEmergant[dr.recoveryEmergantLayer.ordinal()]++;

            countsFloweringState[dr.floweringState.ordinal()]++;

            for (FaunaRecord fr : dr.faunaRecords)
            {
                countsFaunaType[fr.interactionType.ordinal()]++;

                if (faunaMap.containsKey(fr.data))
                {
                    faunaMap.put(fr.data, faunaMap.get(fr.data)+1);
                }
                else
                {
                    faunaMap.put(fr.data, 1);
                }
            }
        }

        AddFonger("Landscape Position", DataRecord.LandscapePosition.values(), countsLandscapePosition,
        new Gradient(new FColor(0f,0.8f,0f), new FColor(0f,0.2f,1f)),
        GetRect(0, 0));

        AddFonger("Vegetation Type", DataRecord.VegetationType.values(), countsVegetationType,
        new Gradient(new FColor(0.2f, 0.2f, 0), new FColor(1f,1f,1f), new FColor(0f,0.3f,0f)),
        GetRect(0, 1));

        AddFonger("Vegetation Stage", DataRecord.VegetationDevelopmentStage.values(), countsVegetationStage,
        new Gradient(new FColor(0.2f, 0.2f, 0), new FColor(1f,1f,1f), new FColor(0f,0.3f,0f)),
        GetRect(0, 2));

        AddFonger("Burn Severity", DataRecord.BurnSeverity.values(), countsBurnSev,
        new Gradient(new FColor(0f,1f,0f), new FColor(0f,0f,1f), new FColor(1f,1f,0f), new FColor(1f,0.5f,0f), new FColor(1f,0f,0f)),
        GetRect(1,0));

        AddFonger("Recovery Ground Layer", DataRecord.RecoveryGroundLayer.values(), countsRecoveryGround,
        new Gradient(new FColor(0f,1f,0f), new FColor(0.9f,0f,0f)),
        GetRect(1,1));

        AddFonger("Recovery Shrub Layer", DataRecord.RecoveryShrubLayer.values(), countsRecoveryShrub,
        new Gradient(new FColor(0f,1f,0f), new FColor(0.9f,0f,0f)),
        GetRect(1,2));

        AddFonger("Recovery Lower Canopy Layer", DataRecord.RecoveryLowerCanopyLayer.values(), countsRecoveryLowerCanopy,
        new Gradient(new FColor(0f,1f,0f), new FColor(0.9f,0f,0f)),
        GetRect(2,0));

        AddFonger("Recovery Upper Canopy Layer", DataRecord.RecoveryUpperCanopyLayer.values(), countsRecoveryUpperCanopy,
        new Gradient(new FColor(0f,1f,0f), new FColor(0.9f,0f,0f)),
        GetRect(2,1));

        AddFonger("Recovery Emergant Layer", DataRecord.RecoveryEmergantLayer.values(), countsRecoveryEmergant,
        new Gradient(new FColor(0f,1f,0f), new FColor(0.9f,0f,0f)),
        GetRect(2,2));

        AddFonger("Flowering State", DataRecord.FloweringState.values(), countsFloweringState,
        new Gradient(new FColor(0.8f,0.8f,0.8f), new FColor(0f,1f,0f), new FColor(0.5f,0f,0f)),
        GetRect(3,0));

        AddFonger("Fauna Type", FaunaRecord.Type.values(), countsFaunaType,
        new Gradient(new FColor(1f,0.5f,0f), new FColor(0f,0.2f,1f)),
        GetRect(3,1));
    }

    Rectangle GetRect(int gridX, int gridY)
    {
        int width = 350;
        int height = 200;
        return new Rectangle(50+((width+25)*gridX), 100+((height+25)*gridY), width, height);
    }

    void AddFonger(String name, Object[] nameObjects, int[] counts, Gradient gradient, Rectangle rect)
    {
        var fonger = CreateFonger(name, nameObjects, counts, gradient, rect);
        add(fonger, JLayeredPane.MODAL_LAYER);
    }

    FongerPanel CreateFonger(String name, Object[] nameObjects, int[] counts, Gradient gradient, Rectangle rect)
    {
        String[] names = java.util.Arrays.stream(nameObjects).map(Object::toString).toArray(String[]::new);
        double[] values = new double[counts.length];

        for (int i = 0; i < names.length; i++)
        {
            names[i] = counts[i] + " " + names[i];
        }

        int total = 0;
        for (int i : counts)
        {
            total += i;
        }

        for (int i = 0; i < counts.length; i++)
        {
            values[i] = (double)counts[i]/total;
        }

        Color[] colors = new Color[counts.length];
        for (int i = 0; i < colors.length; i++)
        {
            double d = ((double)i)/(colors.length-1);
            colors[i] = gradient.Evaluate(d).ToAwtColor();
        }

        return new FongerPanel(name, names, values, colors, rect, new Color(100,100,100,255));
    }
}
