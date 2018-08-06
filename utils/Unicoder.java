import java.util.Random;

public class Unicoder {
    public static void main(String[] args) {
        for (int i = 'A'; i <= 'z'; i++) {
            System.out.println((char) i);
        }

//        System.out.printf("first index = %d, char = 'A'\n", (int) 'A');
//        System.out.printf("last index = %d, char = 'z'\n", (int) 'z');
//        Random random = new Random();
//        for (int i = 0; i < 10_000; i++) {
//            final int value = random.nextInt('z' - 'A') + 'A';
//            assert value >= 'A' && value <= 'z';
//            System.out.printf("index = %d, char = %c\n", value, (char) value);
//        }
    }
}
