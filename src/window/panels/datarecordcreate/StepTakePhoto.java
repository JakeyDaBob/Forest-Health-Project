package window.panels.datarecordcreate;

import graphics.DrawImage;
import window.WindowUtil;
import application.FileSystem;

import java.awt.Color;
import java.util.Random;
import javax.swing.*;

public class StepTakePhoto extends StepPanel
{
    DrawImage image;

    public StepTakePhoto(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        Random random = new Random();
        String[] imagePaths = FileSystem.Resources.GetAllFilesInDirectory("photos");
        String imagePath = "photos/"+imagePaths[random.nextInt(imagePaths.length)];

        image = new DrawImage(FileSystem.Resources.GetInputStream(imagePath));
        image.setBounds(0, 0, getWidth(), getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);

        JButton button = WindowUtil.CreateButton("CAPTURE", getWidth()/2, getHeight()-(getHeight()/8), getWidth()/3, 100, new Color(0,0,0,128), Color.white);
        add(button, JLayeredPane.MODAL_LAYER);

        JLabel promptLabel = WindowUtil.CreateLabel("TAKE PHOTO", 0, getHeight()/2, getWidth(), 50, Color.white);
        add(promptLabel, JLayeredPane.MODAL_LAYER);
    }

    @Override
    public String GetName()
    {
        return "Take Photo";
    } 

    @Override
    public StepState GetState()
    {
        return new StepState(false, "Developer is a sigma");
    }
}
