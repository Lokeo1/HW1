public class CreateIntArrayWithIntAndIntLiteralInitializers {
    public static void main(String[] args) {
    	int[] I;
    	int i;
    	
    	i = 0;
    	I = new int[]{ i, 1 };
        StaticJavaLib.println(I[0]);
        StaticJavaLib.println(I[1]);
    }
}
