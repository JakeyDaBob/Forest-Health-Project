package window.panels.datarecordcreate;

import graphics.DrawImage;
import application.FileSystem;
import javax.swing.JLayeredPane;

public class StepTakePhoto extends StepPanel
{
    DrawImage image;

    public StepTakePhoto(JLayeredPane parentPanel, DataContext context)
    {
        super(parentPanel, context);

        String imagePath = "photos/ComfyUI_00737_.png";
        image = new DrawImage(FileSystem.Resources.GetInputStream(imagePath));
        image.setBounds(0, 0, getWidth(), getHeight());
        add(image, JLayeredPane.DEFAULT_LAYER);
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
