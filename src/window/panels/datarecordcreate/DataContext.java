package window.panels.datarecordcreate;

import java.awt.image.BufferedImage;
import datarecord.DataRecord;

public class DataContext
{
    public BufferedImage image;
    public DataRecord record;

    public DataContext()
    {
        image = null;
        record = new DataRecord();
    }
}
