package generic;

public class GroupMember
{
    public String firstName, lastName;
    public String studentId;

    public GroupMember(String studentId, String firstName, String lastName)
    {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
