package window.panels.datarecordcreate;

import javax.swing.*;

import application.SqlManager;
import datarecord.DataRecord;
import generic.Vector3;

import java.awt.*;
import java.awt.event.*;

import window.WindowUtil;
import window.elements.JButtonRounded;

public class StepUpload extends StepPanel
{
    JButton buttonUpload;
    JButton buttonFinish;
    JLabel statusLabel;

    public StepUpload(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        buttonUpload = WindowUtil.CreateButton("Upload", getWidth()/2, 400, getWidth()/2, 100, Color.black, Color.white);

        buttonUpload.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tryUpload();
            }
        });
        add(buttonUpload, JLayeredPane.MODAL_LAYER);


        buttonFinish = WindowUtil.CreateButton("Finish", getWidth()/2, 800, (int)(getWidth()/1.5), 100, Color.black, Color.white);

        buttonFinish.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tryFinish();
            }
        });
        add(buttonFinish, JLayeredPane.MODAL_LAYER);

        statusLabel = WindowUtil.CreateLabel("", 0, 250, getWidth(), 50, Color.white);
        statusLabel.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 20));
        add(statusLabel, JLayeredPane.MODAL_LAYER);

        setButton(buttonFinish, false);
    }

    void setButton(JButton button, boolean c)
    {
        Color color = c ? Color.white : Color.gray;
        button.setEnabled(c);
        button.setForeground(color);
    }

    void tryUpload()
    {
        setButton(buttonUpload, false);

        setStatus("Uploading", new Color(20,255,40));

        boolean state = SqlManager.AddDataRecord(context.record);
        setUploadState(state);
    }

    void tryFinish()
    {
        SetState(new StepState(StepState.Result.Done));
    }

    void setUploadState(boolean uploadState)
    {
        Color color = uploadState ? Color.green : Color.red;
        String text = uploadState ? "Success" : "Failure";

        setStatus(text, color);

        if (!uploadState)
        {
            setButton(buttonUpload, true);
        }
        else
        {
            setButton(buttonFinish, true);
        }

        repaint();
    }

    void setStatus(String str, Color color)
    {
        statusLabel.setText(str);
        statusLabel.setForeground(color);
    }

    @Override
    public String GetStepName()
    {
        return "Upload";
    }
}
