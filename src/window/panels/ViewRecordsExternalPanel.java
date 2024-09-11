package window.panels;

import java.awt.*;
import javax.swing.*;

import org.json.JSONObject;

import java.awt.event.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.FileSystem;
import application.SqlManager;
import datarecord.*;
import graphics.ColoredPanel;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

import window.panels.recordview.*;

public class ViewRecordsExternalPanel extends JLayeredPane
{
    DataRecordPreviewPanel previewPanel;
    DataRecordPreviewInformationPanel infomationPanel;

    JLabel labelStatus;

    public ViewRecordsExternalPanel(JFrame window)
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

        JLabel labelTitle = WindowUtil.CreateLabel("Public Data Records", 0, 20, getWidth(), 50, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.BOLD, (int)(42*WindowUtil.GetScaleFactor())));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        JButton buttonRefresh = WindowUtil.CreateButton("Refresh", getWidth()/2, 150, 100, 50, new Color(0,0,0,128), Color.white);
        buttonRefresh.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                tryGetDataRecordPreviews();
            }
        });
        add(buttonRefresh, JLayeredPane.MODAL_LAYER);

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

        infomationPanel = new DataRecordPreviewInformationPanel(new Rectangle(0,0,getWidth(),getHeight()));
        infomationPanel.setVisible(false);
        add(infomationPanel, JLayeredPane.POPUP_LAYER);

        previewPanel = new DataRecordPreviewPanel(new Rectangle(20,150,getWidth()-40,800), 10);

        previewPanel.onRowClicked = () ->
        {
            onDataIndex(previewPanel.getData(previewPanel.onRowClickedDataIndex));
        };

        previewPanel.setPage(0);

        add(previewPanel, JLayeredPane.MODAL_LAYER);

        labelStatus = WindowUtil.CreateLabel("Status", 0, 60, getWidth(), 70, Color.gray);
        labelStatus.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 20));
        add(labelStatus, JLayeredPane.MODAL_LAYER);

        subCalls();

        tryGetDataRecordPreviews();
    }

    String getTitle(DataRecordPreview preview)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/mm/yyyy");
        String dtString = preview.dateTime.format(formatter);

        return "Record " + preview.id + " [" + dtString + "]";
    }

    void subCalls()
    {
        SqlManager.OnDataRecordPreviewsGet.Add(taskOnDataRecordPreviewsGet);
        SqlManager.OnDataRecordGet.Add(taskOnDataRecordGet);
    }

    void unCalls()
    {
        SqlManager.OnDataRecordPreviewsGet.Remove(taskOnDataRecordPreviewsGet);
        SqlManager.OnDataRecordGet.Remove(taskOnDataRecordGet);
    }

    void onDataIndex(DataRecordPreviewRowData data)
    {
        infomationPanel.set(null);
        infomationPanel.setVisible(true);

        JLabel infoTitle = infomationPanel.getTitle();
        infoTitle.setText("Getting Record...");
        infoTitle.setForeground(Color.gray);

        tryGetDataRecord(data.id);
    }

    void setStatus(String text, Color color)
    {
        labelStatus.setText(text);
        labelStatus.setForeground(color);
    }

    void tryGetDataRecordPreviews()
    {
        setStatus("Getting records...", new Color(255,50,0));
        SqlManager.GetDataRecordPreviewsAsync();
    }

    Runnable taskOnDataRecordPreviewsGet = () ->
    {
        endGetDataRecordPreviews();
    };

    void endGetDataRecordPreviews()
    {
        boolean state = SqlManager.OnDataRecordPreviewsGetState;
        DataRecordPreview[] previews = SqlManager.OnDataRecordPreviewsGetResult;

        String text = state ? ("Loaded " + previews.length + " records") : "Connection failure";
        Color color = state ? Color.green : Color.red;

        setStatus(text, color);

        DataRecordPreviewRowData[] datas = null;
        if (state)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/mm/yyyy");

            datas = new DataRecordPreviewRowData[previews.length];
            for (int i = 0; i < datas.length; i++)
            {
                String name = "Record " + previews[i].id;
                String desc = previews[i].dateTime.format(formatter);
                datas[i] = new DataRecordPreviewRowData(previews[i].id, name , desc);
            }
        }

        previewPanel.setDataRecordPreviews(datas);
        previewPanel.setPage(0);
    }

    void tryGetDataRecord(int id)
    {
        SqlManager.GetDataRecordAsync(id);
    }

    Runnable taskOnDataRecordGet = () ->
    {
        endGetDataRecord();
    };

    void endGetDataRecord()
    {
        boolean state = SqlManager.OnDataRecordGetState;
        DataRecord dr = SqlManager.OnDataRecordGetResult;

        infomationPanel.setDataRecord(dr);

        if (!state)
        {
            JLabel infoTitle = infomationPanel.getTitle();
            infoTitle.setText("Connection Failure...");
            infoTitle.setForeground(Color.red);

            JLabel infoBody = infomationPanel.getInfo();
            infoBody.setText("Failed to get data record from server!");
            infoBody.setForeground(Color.red);
        }
    }
}
