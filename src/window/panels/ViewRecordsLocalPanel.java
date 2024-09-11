package window.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

import org.json.JSONObject;

import java.awt.event.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


import java.time.format.DateTimeFormatter;

import application.FileSystem;
import application.SqlManager;
import datarecord.DataRecord;
import graphics.ColoredPanel;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

import window.panels.recordview.*;

public class ViewRecordsLocalPanel extends JLayeredPane
{
    DataRecordPreviewPanel previewPanel;
    DataRecordPreviewInformationPanel infomationPanel;
    DataRecord[] dataRecords;

    JButton buttonSync;
    JLayeredPane syncPanel;
    JLabel syncStatusLabel, syncTitleLabel;
    int syncIndex;
    String[] syncPaths;
    JButton syncExitButton;
    DrawImage syncImage;

    JLabel labelWarning;

    public ViewRecordsLocalPanel(JFrame window)
    {
        setSize(window.getWidth(), window.getHeight());

        Random random = new Random();
        String[] imagePaths = FileSystem.Resources.GetAllFilesInDirectory("photos");
        String imageFileName = imagePaths[random.nextInt(imagePaths.length)];
        String imagePath = "photos/"+imageFileName;

        DrawImage image = new DrawImage(FileSystem.Resources.GetImage(imagePath));
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        ColoredPanel box = new ColoredPanel(new Color(0,0,0,200));
        box.setBounds(0,0,getWidth(),getHeight());
        box.setVisible(true);
        add(box, JLayeredPane.PALETTE_LAYER);

        JLabel labelTitle = WindowUtil.CreateLabel("Local Data Records", 0, 20, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        labelWarning = WindowUtil.CreateLabel("No Local Data Records!", 0, (getHeight()/2)-50, getWidth(), 50, Color.white);
        labelWarning.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(30*WindowUtil.GetScaleFactor())));
        add(labelWarning, JLayeredPane.MODAL_LAYER);

        int buttonHeight = 50;
        JButton buttonBack = WindowUtil.CreateButton("Back", getWidth()/2, (int)(getHeight()/4+((buttonHeight*8*1.5))), (int)(getWidth()*0.6f), buttonHeight, new Color(0,0,0,128), Color.white);
        buttonBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                unCalls();
                MenuManager.SetState(MenuState.ViewDataRecords);
            }
        });
        add(buttonBack, JLayeredPane.MODAL_LAYER);

        buttonSync = WindowUtil.CreateButton("Sync", getWidth()/2, 125, 100, buttonHeight, new Color(0,0,0,128), Color.white);
        buttonSync.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                syncRecords();
            }
        });
        add(buttonSync, JLayeredPane.MODAL_LAYER);

        infomationPanel = new DataRecordPreviewInformationPanel(new Rectangle(0,0,getWidth(),getHeight()));
        infomationPanel.setVisible(false);
        add(infomationPanel, JLayeredPane.POPUP_LAYER);

        previewPanel = new DataRecordPreviewPanel(new Rectangle(20,150,getWidth()-40,800), 10);

        previewPanel.onRowClicked = () ->
        {
            onDataIndex(previewPanel.getData(previewPanel.onRowClickedDataIndex));
        };

        setDataRecords();

        previewPanel.setPage(0);

        add(previewPanel, JLayeredPane.MODAL_LAYER);

        createSyncPanel();
        syncPanel.setVisible(false);

        subCalls();
    }

    void subCalls()
    {
        SqlManager.OnDataRecordAdd.Add(taskDataRecordAddEnd);
    }

    void unCalls()
    {
        SqlManager.OnDataRecordAdd.Remove(taskDataRecordAddEnd);
    }

    void createSyncPanel()
    {
        syncPanel = new JLayeredPane();
        syncPanel.setBounds(0,0,getWidth(),getHeight());

        ColoredPanel bgPanel = new ColoredPanel(Color.black);
        bgPanel.setBounds(0,0,getWidth(),getHeight());
        syncPanel.add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        syncImage = new DrawImage(null);
        syncImage.setBounds(0,0,getWidth(),getHeight());
        syncPanel.add(syncImage, JLayeredPane.PALETTE_LAYER);

        syncTitleLabel = WindowUtil.CreateLabel("Sync Data Records", 0, 50, getWidth(), 50, Color.white);
        syncTitleLabel.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, 36));
        syncTitleLabel.setOpaque(true);
        syncTitleLabel.setBackground(Color.black);
        syncPanel.add(syncTitleLabel, JLayeredPane.MODAL_LAYER);

        syncStatusLabel = WindowUtil.CreateLabel("Status", 0, 150, getWidth(), 100, Color.white);
        syncStatusLabel.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, 30));
        syncStatusLabel.setOpaque(true);
        syncStatusLabel.setBackground(Color.black);
        syncPanel.add(syncStatusLabel, JLayeredPane.MODAL_LAYER);

        syncExitButton = WindowUtil.CreateButton("Back", getWidth()/2, getHeight()-100, getWidth()/2, 100, new Color(0,0,0,128), Color.white);
        syncExitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setDataRecords();
                syncPanel.setVisible(false);
            }
        });
        syncPanel.add(syncExitButton, JLayeredPane.MODAL_LAYER);

        add(syncPanel, JLayeredPane.POPUP_LAYER);
    }

    void syncRecords()
    {
        syncPanel.setVisible(true);

        syncTitleLabel.setText("Syncing " + dataRecords.length + " records");

        syncExitButton.setEnabled(false);
        syncExitButton.setForeground(Color.gray);

        syncIndex = 0;
        syncPaths = FileSystem.Base.GetAllFilesInDirectory("outdata/datarecords/");

        syncRecord();
    }

    void syncRecord()
    {
        SqlManager.AddDataRecordAsync(dataRecords[syncIndex]);

        String imagePath = "photos/"+dataRecords[syncIndex].image.name;
        BufferedImage imageData = null;
        if (FileSystem.Resources.Exists(imagePath))
        {
            imageData = FileSystem.Resources.GetImage(imagePath);
        }
        syncImage.setImage(imageData);

        syncStatusSet("[" + syncIndex + "/" + dataRecords.length + "] Uploading", Color.white);
    }

    Runnable taskDataRecordAddEnd = () ->
    {
        onDataRecordAddEnd();
    };

    void onDataRecordAddEnd()
    {
        boolean state = SqlManager.OnDataRecordAddState;

        FileSystem.Base.Delete("outdata\\datarecords\\" + syncPaths[syncIndex]);

        if (!state)
        {
            syncStatusSet("Sync " + syncIndex + " failed!", Color.red);
            syncEnd();
            return;
        }

        syncIndex++;

        if (syncIndex >= syncPaths.length)
        {
            syncComplete();
            syncEnd();
        }
        else
        {
            syncRecord();
        }
    }

    void syncComplete()
    {
        syncStatusSet("!! Sync Complete !!", Color.green);
    }

    void syncEnd()
    {
        syncExitButton.setEnabled(true);
        syncExitButton.setForeground(Color.white);
    }

    void syncStatusSet(String str, Color color)
    {
        syncStatusLabel.setText(str);
        syncStatusLabel.setForeground(color);
    }

    String getTitle(DataRecord dr)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (dd/mm/yyyy)");
        String dtString = dr.dateTime.format(formatter);

        return "Data Record " + dtString;
    }

    void onDataIndex(DataRecordPreviewRowData data)
    {
        infomationPanel.set(dataRecords[data.id]);
        infomationPanel.setVisible(true);
    }

    void setDataRecords()
    {
        dataRecords = getDataRecords();
        DataRecordPreviewRowData[] datas = new DataRecordPreviewRowData[dataRecords.length];
        for (int i = 0; i < dataRecords.length; i++)
        {
            datas[i] = new DataRecordPreviewRowData(i, getTitle(dataRecords[i]), "Locally Stored");
        }

        previewPanel.setDataRecordPreviews(datas);

        boolean valid = dataRecords.length > 0;

        labelWarning.setVisible(!valid);
        previewPanel.setVisible(valid);
        buttonSync.setVisible(valid);
    }

    DataRecord[] getDataRecords()
    {
        List<DataRecord> drList = new ArrayList<>();

        String[] paths = FileSystem.Base.GetAllFilesInDirectory("outdata/datarecords/");
        for (String path : paths)
        {
            String fileString = FileSystem.Base.ReadAllLines("outdata\\datarecords\\"+path);
            DataRecord dr = new DataRecord();
            dr.fromJson(new JSONObject(fileString));
            drList.add(dr);
        }

        DataRecord[] drArray = new DataRecord[drList.size()];
        drList.toArray(drArray);

        return drArray;
    }
}
