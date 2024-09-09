package window.panels.datarecordcreate;

import javax.swing.*;

import datarecord.DataRecord;

import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import window.WindowUtil;

public class StepReview extends StepPanel
{
    JButton buttonConfirm;

    public StepReview(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        DataRecord dr = context.record;

        String str = "<html>";

        DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMMM - yyyy");

        String time = dr.dateTime.format(dateTimeFormatterTime);
        String date = dr.dateTime.format(dateTimeFormatterDate);

        str += "Date: " + date;
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

        JLabel label = WindowUtil.CreateLabel(str, 0, 100, getWidth(), 700, Color.white);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.NORTH);
        label.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 16));
        add(label, JLayeredPane.MODAL_LAYER);

        buttonConfirm = WindowUtil.CreateButton("CONFIRM", getWidth()/2, getHeight()-(getHeight()/8), (int)(getWidth()/1.3), 50, new Color(0,0,0,128), Color.white);
        buttonConfirm.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 40));
        buttonConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SetState(new StepState(StepState.Result.Done));
            }
        });
        add(buttonConfirm, JLayeredPane.MODAL_LAYER);
    }

    @Override
    public String GetStepName()
    {
        return "Review";
    }
}
