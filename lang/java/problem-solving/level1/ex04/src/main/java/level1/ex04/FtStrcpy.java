package level1.ex04;

public class FtStrcpy {

    public static char[] ftStrcpy(char[] s1, char[] s2) {

        for (int i = 0; i < s2.length; i++) {
            s1[i] = s2[i];
        }
        return s1;
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            char[] s2 = args[0].toCharArray();
            char[] s1 = new char[s2.length];
            System.out.println(new String(ftStrcpy(s1, s2)));
            // System.out.printf("%s%n", new String(ftStrcpy(s1, s2)));
        }
    }
}
