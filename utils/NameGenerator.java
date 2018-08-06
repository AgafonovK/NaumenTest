import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class NameGenerator {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("eq_file_names"))) {

            ArrayList<String> classNames = new ArrayList<>(10_000);

            Random random = new Random();

            int failedNames = 0;
            for (int i = 0; i < 100_000; i++) {
                final char[] fileName = new char[random.nextInt(32) + 1];
                //del
                fileName[0] = 'a';
                //del
                String fileNameString;

                boolean exist;
                int attempts = 1000;
                do {
                    exist = true;
                    //del
                    int len = 1; //start index, return to 0
                    //del
                    while (len < fileName.length) {
                        fileName[len] = (char) (random.nextInt('z' - 'A') + 'A');
                        if (fileName[len] > 'Z' && fileName[len] < 'a') {
                            fileName[len] = (char) (random.nextInt('9' - '0') + '0');
                        }

                        len++;
                    }

                    fileNameString = new String(fileName);
                    if (!classNames.contains(fileNameString)) {
                        classNames.add(fileNameString);
                        writer.println(fileNameString);
                        System.out.println(i);
                        exist = false;
                    } else {
                        attempts--;
                        if (attempts <= 0) {
                            failedNames++;
                            exist = false;
                        }
                    }
                } while (exist);
            }

            System.out.println("passed files = " + failedNames);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
