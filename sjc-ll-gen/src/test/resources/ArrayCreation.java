public class ArrayCreation
{
    public static void main(String[] args)
    {
        AC[] acs;
        int length;
        
        acs = new AC[] { null, new AC() };
        length = 2;
        StaticJavaLib.println(length);
        StaticJavaLib.println(acs[0] == null);
        StaticJavaLib.println(acs[1] != null);
    }
}

class AC
{
    
}
