package window.panels.datarecordcreate;

import javax.swing.*;

import datarecord.FaunaRecord;
import generic.Vector2Int;

import java.awt.*;
import java.awt.event.*;
import window.elements.*;

import java.util.List;
import java.util.ArrayList;

public class StepFauna extends StepPanel
{
    Table table;

    List<FaunaRecord> faunaRecords;

    StepFaunaCreate createFaunaRecordPanel;

    public StepFauna(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        faunaRecords = new ArrayList<>();

        createFaunaRecordPanel = new StepFaunaCreate(parentPanel);
        createFaunaRecordPanel.setVisible(false);
        createFaunaRecordPanel.onEnd = () -> { FaunaCreateEnd(); };
        add(createFaunaRecordPanel, JLayeredPane.MODAL_LAYER);

        table = new Table(getWidth()/8, (getWidth()/8)*3, getWidth()/4, getWidth()/4);
        table.setLocation(0, 100);
        add(table, JLayeredPane.PALETTE_LAYER);

        table.addRow("Add", "Data", "Data Type", "Interaction Type");

        table.onCellClicked = () -> { OnCellClicked(); };
    }

    void OnCellClicked()
    {
        Vector2Int pos = table.onCellClickedPosition;
        System.out.println("Cell click: " + pos);

        if (pos.x == 0)
        {
            if (pos.y == 0)
            {
                CreateNew();
            }
        }
    }

    void CreateNew()
    {
        System.out.println("Creating new fauna record");

        createFaunaRecordPanel.begin("Create Fauna Record");
    }

    void FaunaCreateEnd()
    {
        FaunaRecord fr = createFaunaRecordPanel.getFaunaRecord();

        System.out.println("fauna = " + fr);
    }

    @Override
    public String GetStepName()
    {
        return "Fauna Interactions";
    }
}
