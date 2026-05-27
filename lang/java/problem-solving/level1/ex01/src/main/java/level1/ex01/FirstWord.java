package level1.ex01;

public class FirstWord {

    public static void firstWord(String str) {
        int i = 0;

        // Skip leading spaces and tabs
        while (i < str.length() && (str.charAt(i) == ' ' || str.charAt(i) == '\t'))
            i++;

        // Print characters until space or end of string
        while (i < str.length() && str.charAt(i) != ' ' && str.charAt(i) != '\t')
            System.out.print(str.charAt(i++));
    }

    public static void main(String[] args) {
        if (args.length == 1)
            firstWord(args[0]);
        System.out.println();
    }
}