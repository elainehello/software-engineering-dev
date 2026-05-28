package level1.ex02;

public class FizzBuzz {

    public static void fizzBuzz(int len) {
        for (int i = 1; i <= len; i++) {
            if (i % 15 == 0) {
                System.out.println("fizzbuzz");
            }
            else if (i % 3 == 0) {
                System.out.println("fizz");
            }
            else if (i % 5  == 0) {
                System.out.println("buzz");
            }
            else {
                System.out.println(i);
            }
        }
    }

    public static void main() {
        fizzBuzz(100);
    }
}
