package window.panels;

import java.awt.*;
import javax.swing.*;

import org.json.JSONObject;

import datarecord.*;
import application.*;

import java.awt.event.*;
import java.util.*;
import generic.*;

import graphics.*;

import window.MenuManager;
import window.MenuState;
import window.WindowUtil;
import window.elements.*;

public class SciAnalysis extends JLayeredPane
{
    public SciAnalysis(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(Color.black);
        backgroundPanel.setBounds(0,0,getWidth(),getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel("Analysis", 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 100;
        JButton buttonBack = WindowUtil.CreateButton("Back", getWidth()/2, (int)(getHeight()/4+((buttonHeight*3.5*1.5))), (int)(getWidth()*0.3f), buttonHeight, new Color(0,0,0,128), Color.white);
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

        var fonger = CreateFonger(DataRecord.LandscapePosition.values(), countsLandscapePosition);
        fonger.setBounds(50,50,fonger.getWidth(),fonger.getHeight());
        add(fonger, JLayeredPane.MODAL_LAYER);
    }

    FongerPanel CreateFonger(Object[] nameObjects, int[] counts)
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
            float f = ((float)i)/colors.length;
            colors[i] = FColor.Lerp(new FColor(1f,1f,1f), new FColor(1f,0f,0f), f).ToAwtColor();
        }

        return new FongerPanel(names, values, colors, new Rectangle(0,0,300,200), new Color(100,100,100,255));
    }
}
