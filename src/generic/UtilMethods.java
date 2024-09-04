package generic;

public class UtilMethods
{
    public static Enum<?>[] getEnumValues(Class<?> enumClass) 
    {
        if (enumClass.isEnum())
        {
            @SuppressWarnings("unchecked")
            var values = ((Class<? extends Enum<?>>) enumClass).getEnumConstants();
            return values;
        } 
        else 
        {
            throw new IllegalArgumentException("Class provided is not an enum type.");
        }
    }
    
    public static String FormatCodeString(String strInput)
    {
        String strOut = "";
        int len = strInput.length();

        boolean lastCharCase = true;

        for (int i = 0; i < len; i++)
        {
            char c = strInput.charAt(i);

            boolean charCase = Character.isUpperCase(c);

            if (charCase && !lastCharCase)
            {
                strOut += " ";
            }
            strOut += c;

            lastCharCase = charCase;
        }

        return strOut;
    }
}
