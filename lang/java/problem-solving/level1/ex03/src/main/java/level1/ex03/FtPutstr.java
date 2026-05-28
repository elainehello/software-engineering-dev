package level1.ex03;

public class FtPutstr {

    public static void ftPutstr(String str) {
        System.out.print(str);
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            ftPutstr(args[0]);
        }
        System.out.println();
    }
}
