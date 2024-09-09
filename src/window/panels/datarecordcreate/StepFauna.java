package window.panels.datarecordcreate;

import javax.swing.*;

import datarecord.FaunaRecord;
import generic.Vector2Int;

import java.awt.*;
import java.awt.event.*;

import window.WindowUtil;
import window.elements.*;

import java.util.List;
import java.util.ArrayList;

public class StepFauna extends StepPanel
{
    Table table;
    List<FaunaRecord> faunaRecords;
    StepFaunaCreate createFaunaRecordPanel;
    FarnuType farnuType;
    int farnuIndex;
    JButton buttonConfirm;

    public StepFauna(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        faunaRecords = new ArrayList<>();

        createFaunaRecordPanel = new StepFaunaCreate(parentPanel);
        createFaunaRecordPanel.setVisible(false);
        createFaunaRecordPanel.onEnd = () -> { FaunaThingEnd(); };
        add(createFaunaRecordPanel, JLayeredPane.MODAL_LAYER);

        table = new Table(getWidth()/8, (getWidth()/8)*3, getWidth()/4, getWidth()/4);
        table.setLocation(0, 100);
        add(table, JLayeredPane.PALETTE_LAYER);

        table.addRow("Add", "Data", "Data Type", "Interaction Type");

        table.onCellClicked = () -> { OnCellClicked(); };

        farnuType = FarnuType.None;
        farnuIndex = -1;

        buttonConfirm = WindowUtil.CreateButton("CONFIRM", getWidth()/2, getHeight()-(getHeight()/5), (int)(getWidth()/1.3), 150, new Color(0,0,0,128), Color.white);
        buttonConfirm.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 40));
        buttonConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Confirm();
            }
        });
        add(buttonConfirm, JLayeredPane.MODAL_LAYER);
    }

    void Confirm()
    {
        FaunaRecord[] faunaRecordsArray = new FaunaRecord[faunaRecords.size()];
        faunaRecords.toArray(faunaRecordsArray);

        context.record.faunaRecords = faunaRecordsArray;
        System.out.println("Set fauna records");
        for (FaunaRecord fr : context.record.faunaRecords)
        {
            System.out.println("- " + fr);
        }

        SetState(new StepState(StepState.Result.Done));
    }

    void OnCellClicked()
    {
        Vector2Int pos = table.onCellClickedPosition;

        if (pos == null)
        {
            return;
        }

        if (pos.x == 0)
        {
            if (pos.y == 0)
            {
                farnuType = FarnuType.Create;
                createFaunaRecordPanel.begin(false, null);
            }
            else
            {
                farnuType = FarnuType.Edit;
                farnuIndex = pos.y;
                createFaunaRecordPanel.begin(true, faunaRecords.get(farnuIndex-1));
            }
        }
    }

    void FaunaThingEnd()
    {
        FaunaRecord fr = createFaunaRecordPanel.getFaunaRecord();

        System.out.println("fauna = " + fr);

        if (farnuType == FarnuType.Create)
        {
            faunaRecords.add(fr);

            table.addRow("EDIT", fr.data, fr.type.toString(), fr.interactionType.toString());
        }
        else if (farnuType == FarnuType.Edit)
        {
            if (farnuIndex == -1)
            {
                return;
            }

            if (fr == null)
            {
                table.removeRow(farnuIndex);
                faunaRecords.remove(farnuIndex-1);
            }
            else
            {
                table.getRow(farnuIndex).setNames("EDIT", fr.data, fr.type.toString(), fr.interactionType.toString());
                faunaRecords.set(farnuIndex-1, fr);
            }
        }
        
        farnuIndex = -1;
        farnuType = FarnuType.None;
    }

    @Override
    public String GetStepName()
    {
        return "Fauna Interactions";
    }

    public enum FarnuType
    {
        None,
        Create,
        Edit
    }
}
