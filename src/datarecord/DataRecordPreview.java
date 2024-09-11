package datarecord;

import java.time.LocalDateTime;

public class DataRecordPreview
{
    public int id;
    public LocalDateTime dateTime;

    public DataRecordPreview(int id, LocalDateTime dateTime)
    {
        this.id=id;
        this.dateTime=dateTime;
    }

    public DataRecordPreview()
    {
        
    }
}
