public class While {
    public static void main(String[] args) {
        int i;
        int j;        
		
        i = 0; j = 0;
        while (i < 10) {
            i++;
        }
        j = i;
        StaticJavaLib.assertTrue(j == 10);
    }
}
