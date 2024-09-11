package window.panels.recordview;

import java.awt.*;
import javax.swing.*;

import application.FileSystem;

import java.awt.event.*;

import graphics.ColoredPanel;
import graphics.DrawImage;
import window.MenuManager;
import window.MenuState;
import window.WindowUtil;

import datarecord.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataRecordPreviewInformationPanel extends JLayeredPane
{
    JLabel labelTitle;
    public JLabel getTitle()
    {
        return labelTitle;
    }

    JLabel labelInfo;
    public JLabel getInfo()
    {
        return labelInfo;
    }

    DrawImage image;
    JButton buttonImage;
    boolean showingImage = false;

    public DataRecordPreviewInformationPanel(Rectangle rect)
    {
        setBounds(rect);

        ColoredPanel bgPanel = new ColoredPanel(new Color(0,0,0,220));
        bgPanel.setBounds(0,0,getWidth(),getHeight());
        add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        labelTitle = WindowUtil.CreateLabel("Title Title", 0, 0, getWidth(), 100, Color.white);
        labelTitle.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 25));
        add(labelTitle, JLayeredPane.MODAL_LAYER);

        labelInfo = WindowUtil.CreateLabel("Info mation!", 40, 100, getWidth()-80, getHeight(), Color.white);
        labelInfo.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 16));
        labelInfo.setHorizontalAlignment(SwingConstants.LEFT);
        labelInfo.setVerticalAlignment(SwingConstants.NORTH);
        add(labelInfo, JLayeredPane.MODAL_LAYER);

        image = new DrawImage(null);
        image.setVisible(false);
        add(image, JLayeredPane.POPUP_LAYER);

        buttonImage = WindowUtil.CreateButton("Back", getWidth()/2, getHeight()-100, 200, 100, new Color(0,0,0,128), Color.white);
        buttonImage.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setImage(!showingImage);
            }
        });
        buttonImage.setVisible(false);
        add(buttonImage, JLayeredPane.DRAG_LAYER);

        JButton buttonBack = WindowUtil.CreateButton("Back", getWidth()/2, getHeight()-100, 200, 100, new Color(0,0,0,128), Color.white);
        buttonBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
        add(buttonBack, JLayeredPane.MODAL_LAYER);
    }

    public void set(DataRecord dr)
    {
        labelTitle.setText("");
        labelTitle.setForeground(Color.white);
        labelInfo.setText("");
        labelInfo.setForeground(Color.white);
        image.setImage(null);
        image.setVisible(false);
        buttonImage.setVisible(false);

        if (dr == null)
        {
            return;
        }

        setDataRecord(dr);
    }

    public void setDataRecord(DataRecord dr)
    {
        labelTitle.setText(getTitle(dr));
        labelInfo.setText(getInfo(dr));

        image.setVisible(true);
        buttonImage.setVisible(true);

        String imagePath = "photos/"+dr.image.name;
        if (FileSystem.Resources.Exists(imagePath))
        {
            image.setImage(FileSystem.Resources.GetImage(imagePath));
        }

        setImage(false);
    }

    void setImage(boolean state)
    {
        if (!state)
        {
            image.setBounds((getWidth()/2)+100, 250, (int)(90*1.5), (int)(160*1.5));
            buttonImage.setBounds((getWidth()/2)+100, 500, (int)(90*1.5), 50);
            buttonImage.setText("Preview Image");
        }
        else
        {
            image.setBounds(80,80,getWidth()-160,getHeight()-160);
            buttonImage.setBounds((getWidth()/2)-100, getHeight()-200, 200, 100);
            buttonImage.setText("Close Preview");
        }

        showingImage = state;
    }

    String getTitle(DataRecord dr)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (dd/mm/yyyy)");
        String dtString = dr.dateTime.format(formatter);

        return "Data Record " + dtString;
    }

    String getInfo(DataRecord dr)
    {
        String str = "<html>";

        DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMMM - yyyy");

        String time = dr.dateTime.format(dateTimeFormatterTime);
        String date = dr.dateTime.format(dateTimeFormatterDate);

        str += "Image name: " + dr.image.name;
        str += "<br>Date: " + date;
        str += "<br>Time: " + time;

        String geoPosLat = String.format("%.5f", dr.geolocationLatitude);
        String geoPosLong = String.format("%.5f", dr.geolocationLongitude);
        String geoPos = geoPosLat + ", " + geoPosLong;

        str += "<br>Geodetic Coordinates: " + geoPos;

        str += "<br><br>Landscape Position: " + dr.landscapePosition;

        str += "<br><br>Vegetation Type: " + dr.vegetationType;
        str += "<br>Vegetation Development: " + dr.vegetationDevelopmentStage;

        str += "<br>Burn Severity: " + dr.burnSeverity;

        str += "<br><br>Recovery per layer:";

        for (int i = 0; i < 5; i++)
        {
            String layerName = DataRecord.Layers.values()[i].toString();
            String layerRecovery = dr.GetLayerRecoveryString(i);

            str += "<br>" + layerName + ": " + layerRecovery;
        }

        str += "<br><br>Flowering State: " + dr.floweringState;
        str += "<br><br>Altitude: " + dr.altitudeMeters + "m";
        str += "<br>Barometric Pressure: " + dr.barometricPressureAtm + "atm";
        str += "<br>Compass Direction: " + dr.compassDirectionDegree + " degrees";
        str += "<br>Accelerometer Data: " + dr.accelerometerData;

        str += "<br><br>Fauna Records:";
        for (int i = 0; i < dr.faunaRecords.length; i++)
        {
            str += "<br>" + dr.faunaRecords[i];
        }

        str += "</html>";

        return str;
    }
}
