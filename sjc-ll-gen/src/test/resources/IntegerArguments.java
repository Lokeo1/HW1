public class IntegerArguments {
    public static void main(String[] args) {
        StaticJavaLib.println(StaticJavaLib.getIntArgument(args, 0));
        StaticJavaLib.println(StaticJavaLib.getIntArgument(args, 1));
    }
}
