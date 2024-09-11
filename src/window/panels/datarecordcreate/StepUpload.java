package window.panels.datarecordcreate;

import javax.swing.*;

import org.json.JSONObject;

import application.FileSystem;
import application.SqlManager;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import window.WindowUtil;

public class StepUpload extends StepPanel
{
    JButton buttonUpload;
    JButton buttonFinish;
    JLabel statusLabel;

    boolean uploading = false;

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

        subCalls();
    }

    @Override
    public void CleanUp()
    {
        unCalls();
    }

    void subCalls()
    {
        SqlManager.OnDataRecordAdd.Add(taskUploadEnd);
    }

    void unCalls()
    {
        SqlManager.OnDataRecordAdd.Remove(taskUploadEnd);
    }

    void setButton(JButton button, boolean c)
    {
        Color color = c ? Color.white : Color.gray;
        button.setEnabled(c);
        button.setForeground(color);
    }

    void tryUpload()
    {
        if (uploading)
        {
            return;
        }
        uploading = true;

        setButton(buttonUpload, false);

        setStatus("Uploading", new Color(20,255,40));

        SqlManager.AddDataRecordAsync(context.record);
    }

    Runnable taskUploadEnd = () ->
    {
        uploadEnd();
    };

    void uploadEnd()
    {
        uploading = false;

        boolean state = SqlManager.OnDataRecordAddState;
        setUploadState(state);
    }

    void tryFinish()
    {
        SetState(new StepState(StepState.Result.Done));
    }

    void setUploadState(boolean uploadState)
    {
        Color color = uploadState ? Color.green : Color.red;
        String text = uploadState ? "Success" : "Failed, saving record locally";

        setStatus(text, color);

        if (!uploadState)
        {
            setButton(buttonUpload, true);

            FileSystem.Base.DirectoryCheck("outdata");
            FileSystem.Base.DirectoryCheck("outdata/datarecords");

            JSONObject jobj = context.record.toJson();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
            String fileName = dateTime.format(formatter) + ".json";
            String filePath = "outdata/datarecords/" + fileName;
            
            FileSystem.Base.WriteAllLines(filePath, jobj.toString());
        }
        
        setButton(buttonFinish, true);

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
