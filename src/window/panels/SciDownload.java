package window.panels;

import java.awt.*;
import javax.swing.*;

import org.json.JSONObject;

import application.*;
import datarecord.*;
import graphics.DrawImage;

import java.awt.event.*;

import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

public class SciDownload extends JLayeredPane
{
    JLabel labelStatus;
    JButton buttonDownload;
    DataRecordPreview[] dataRecordPreviews;
    int dataRecordDownloadIndex;

    public SciDownload(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        DrawImage image = new DrawImage(AppInfo.GetBackgroundImage());
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        JPanel backgroundPanel = new JPanel(null);
        backgroundPanel.setBackground(Color.black);
        backgroundPanel.setBounds(0,0,getWidth(),getHeight());
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel("Download Records", 0, getHeight()/16, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        labelStatus = WindowUtil.CreateLabel("", 0, 200, getWidth(), 50, Color.white);
        add(labelStatus, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 100;
        buttonDownload = WindowUtil.CreateButton("Download Records", getWidth()/2, (int)(getHeight()/4+((buttonHeight*1*1.5))), (int)(getWidth()*0.3f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonDownload.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tryGetPreviews();
                buttonDownload.setEnabled(false);
            }
        });
        add(buttonDownload, JLayeredPane.MODAL_LAYER);

        JButton buttonBack = WindowUtil.CreateButton("Back", getWidth()/2, (int)(getHeight()/4+((buttonHeight*3.5*1.5))), (int)(getWidth()*0.3f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                unCalls();
                MenuManager.SetState(MenuState.Menu);
            }
        });
        add(buttonBack, JLayeredPane.MODAL_LAYER);

        JButton buttonPostgre = WindowUtil.CreateButton("PostgreSQL Interface", getWidth()/2, (int)(getHeight()/4+((buttonHeight*2.0*1.5))), (int)(getWidth()*0.3f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonPostgre.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                unCalls();
                MenuManager.SetState(MenuState.SciPostgreInterface);
            }
        });
        add(buttonPostgre, JLayeredPane.MODAL_LAYER);

        subCalls();
    }
    
    void subCalls()
    {
        SqlManager.OnDataRecordPreviewsGet.Add(taskOnGetPreviews);
        SqlManager.OnDataRecordGet.Add(taskOnGetRecord);
    }

    void unCalls()
    {
        SqlManager.OnDataRecordPreviewsGet.Remove(taskOnGetPreviews);
        SqlManager.OnDataRecordGet.Remove(taskOnGetRecord);
    }

    Runnable taskOnGetPreviews = () ->
    {
        onGetPreviews();
    };

    void onGetPreviews()
    {
        boolean state = SqlManager.OnDataRecordPreviewsGetState;
        dataRecordPreviews = SqlManager.OnDataRecordPreviewsGetResult;

        labelStatus.setText(state ? ("Found " + dataRecordPreviews.length + " records!") : "Failed to get records");
        labelStatus.setForeground(state ? Color.green : Color.red);

        if (!state)
        {
            buttonDownload.setEnabled(true);
            return;
        }

        tryDownloadRecord();
    }

    void tryGetPreviews()
    {
        String[] fileNames = FileSystem.Base.GetAllFilesInDirectory("outdata/scidatarecords/");
        for (String fileName : fileNames)
        {
            FileSystem.Base.Delete("outdata/scidatarecords/" + fileName);
        }

        dataRecordDownloadIndex = 0;
        dataRecordPreviews = null;

        System.out.println("Try get previews");
        SqlManager.GetDataRecordPreviewsAsync();
    }

    void tryDownloadRecord()
    {
        if (dataRecordDownloadIndex >= dataRecordPreviews.length)
        {
            labelStatus.setText("Downloaded All Records!");
            labelStatus.setForeground(Color.green);
            return;
        }

        var preview = dataRecordPreviews[dataRecordDownloadIndex];
        SqlManager.GetDataRecordAsync(preview.id);
    }

    Runnable taskOnGetRecord = () ->
    {
        onGetRecord();
    };

    void onGetRecord()
    {
        System.out.println("on get record " + dataRecordDownloadIndex);

        boolean state = SqlManager.OnDataRecordGetState;
        DataRecord record = SqlManager.OnDataRecordGetResult;

        labelStatus.setText(state ? ("Downloaded Record " + record.id) : ("Failed to download " + dataRecordDownloadIndex + "/" + dataRecordPreviews.length));
        labelStatus.setForeground(state ? Color.green : Color.red);

        if (!state)
        {
            return;
        }

        JSONObject jObj = record.toJson();
        String jStr = jObj.toString();
        FileSystem.Base.DirectoryCheck("outdata");
        FileSystem.Base.DirectoryCheck("outdata/scidatarecords");
        
        FileSystem.Base.WriteAllLines("outdata/scidatarecords/dr"+record.id+".json", jStr);

        dataRecordDownloadIndex++;
        tryDownloadRecord();
    }
}
