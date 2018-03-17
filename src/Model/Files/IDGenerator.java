package Model.Files;

public class IDGenerator
{
    public static int counter = 0;
    public static int generate_ID()
    {
        return counter++;
    }
}
