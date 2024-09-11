package window.panels.datarecordcreate;

import graphics.DrawImage;
import window.WindowUtil;
import application.FileSystem;
import datarecord.DataRecord;
import datarecord.Image;
import generic.Vector3;

import java.awt.Color;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

public class StepTakePhoto extends StepPanel
{
    DrawImage image;
    BufferedImage imageData;
    String imageName;
    String imagePath;

    public StepTakePhoto(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        Random random = new Random();
        String[] imagePaths = FileSystem.Resources.GetAllFilesInDirectory("photos");
        String imageFileName = imagePaths[random.nextInt(imagePaths.length)];
        imagePath = "photos/"+imageFileName;

        imageData = FileSystem.Resources.GetImage(imagePath);
        image = new DrawImage(imageData);
        image.setBounds(0, 0, getWidth(), getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        JButton button = WindowUtil.CreateButton("CAPTURE", getWidth()/2, getHeight()-(getHeight()/5), getWidth()/3, 100, new Color(0,0,0,128), Color.white);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                context.image = imageData;

                DataRecord record = context.record;

                record.image = new Image();
                record.image.name = imageFileName;

                Random random = new Random();

                record.image.data = new byte[64];
                random.nextBytes(record.image.data);

                record.geolocationLatitude = -28.2383 + random.nextDouble(-0.2,0.2);
                record.geolocationLongitude = 153.1972 + random.nextDouble(-0.2,0.2);

                record.dateTime = LocalDateTime.now();

                record.altitudeMeters = random.nextInt(1,1000);
                record.compassDirectionDegree = random.nextInt(360);
                record.barometricPressureAtm = 1.00 -random.nextDouble(0.56);

                record.accelerometerData = new Vector3(random.nextFloat(-0.1f,1f),random.nextFloat(-0.2f,2f), random.nextFloat(-0.1f,1f));

                System.out.println("IMAGE NAME: " + context.record.image.name);
                SetState(new StepState(StepState.Result.Done));
            }
        });
        add(button, JLayeredPane.MODAL_LAYER);
    }

    @Override
    public String GetStepName()
    {
        return "Take Photo";
    } 
}
