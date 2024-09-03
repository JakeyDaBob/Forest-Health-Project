package window.panels.datarecordcreate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import graphics.ColoredPanel;
import graphics.DrawImage;
import window.WindowUtil;

public class StepPreviewPhoto extends StepPanel
{
    public StepPreviewPhoto(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        DrawImage image = new DrawImage(context.image);
        image.setBounds(0,0,getWidth(),getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        ColoredPanel box = new ColoredPanel(new Color(0,0,0,200));
        box.setBounds(0,0,getWidth(),getHeight());
        box.setVisible(true);
        add(box, JLayeredPane.PALETTE_LAYER);

        JPanel textPanel = new JPanel(null);
        textPanel.setBounds(0,150,getWidth(),getHeight());
        textPanel.setOpaque(false);
        add(textPanel, JLayeredPane.MODAL_LAYER);

        JLabel labelGeoPosTitle = WindowUtil.CreateLabel("Geodetic Coordinates", 0,100,getWidth(), 50,Color.white);
        labelGeoPosTitle.setFont(new Font(WindowUtil.FontMainName, Font.ITALIC, 30));
        textPanel.add(labelGeoPosTitle, JLayeredPane.MODAL_LAYER);

        String geoPosLat = String.format("%.5f", context.record.geolocationLatitude);
        String geoPosLong = String.format("%.5f", context.record.geolocationLongitude);
        String geoPos = geoPosLat + ", " + geoPosLong;
        JLabel labelGeoPos = WindowUtil.CreateLabel(geoPos, 0,150,getWidth(), 50,Color.white);
        labelGeoPos.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 25));
        textPanel.add(labelGeoPos, JLayeredPane.MODAL_LAYER);

        DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMMM - yyyy");

        String time = context.record.dateTime.format(dateTimeFormatterTime);
        String date = context.record.dateTime.format(dateTimeFormatterDate);

        JLabel labelDateTimeTitle = WindowUtil.CreateLabel("Time & Date", 0,250,getWidth(), 50,Color.white);
        labelDateTimeTitle.setFont(new Font(WindowUtil.FontMainName, Font.ITALIC, 30));
        textPanel.add(labelDateTimeTitle, JLayeredPane.MODAL_LAYER);

        JLabel labelTime = WindowUtil.CreateLabel(time, 0,300,getWidth(), 50,Color.white);
        labelTime.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 25));
        textPanel.add(labelTime, JLayeredPane.MODAL_LAYER);

        JLabel labelDate = WindowUtil.CreateLabel(date, 0,340,getWidth(), 25,Color.white);
        labelDate.setFont(new Font(WindowUtil.FontMainName, Font.PLAIN, 20));
        textPanel.add(labelDate, JLayeredPane.MODAL_LAYER);

        JButton buttonConfirm = WindowUtil.CreateButton("CONFIRM", getWidth()-(getWidth()/4), getHeight()-(getHeight()/5), getWidth()/3, 100, new Color(0,0,0,128), Color.white);
        buttonConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SetState(new StepState(StepState.Result.Done));
            }
        });
        add(buttonConfirm, JLayeredPane.MODAL_LAYER);

        JButton buttonBack = WindowUtil.CreateButton("RETAKE", getWidth()/4, getHeight()-(getHeight()/5), getWidth()/3, 100, new Color(0,0,0,128), Color.white);
        buttonBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SetState(new StepState(StepState.Result.Back));
            }
        });
        add(buttonBack, JLayeredPane.MODAL_LAYER);
    }

    @Override
    public String GetStepName()
    {
        return "Preview Photo";
    }
}
