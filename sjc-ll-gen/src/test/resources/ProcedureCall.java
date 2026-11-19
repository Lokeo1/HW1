public class ProcedureCall {
    public static void main(String[] args) {
        int x;
        x = 0;
        x = inc(x);
        StaticJavaLib.assertTrue(x == 1);
        x = dec(x);
        StaticJavaLib.assertTrue(x == 0);
    }

    static int inc(int x) {
        return x + 1;
    }

    static int dec(int x) {
        return x - 1;
    }
}
