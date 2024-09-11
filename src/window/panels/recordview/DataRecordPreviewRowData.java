package window.panels.recordview;

public class DataRecordPreviewRowData
{
    public int id;
    public String title;
    public String desc;    

    public DataRecordPreviewRowData(int id, String title, String desc)
    {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    public DataRecordPreviewRowData(DataRecordPreviewRowData other)
    {
        this.id = other.id;
        this.title = other.title;
        this.desc =other.desc;
    }

    @Override
    public String toString()
    {
        return "(" + id + ", " + title + ", " + desc + ")";
    }
}
