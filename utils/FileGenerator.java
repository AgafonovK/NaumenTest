import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class FileGenerator {

    private static Set<String> existNames = new HashSet<>();

    public static void main(String[] args) throws IOException {

        int counFiles = calcCountOfFiles().orElse(0);

        Random random = new Random();

        for (int i = 0; i <= random.nextInt(10_000 - counFiles) + 1; i++) {
            final char[] fileName = new char[random.nextInt(32) + 1];
            String fileNameString;

            boolean exist;
            do {
                exist = true;
                int len = 0;
                while (len < fileName.length) {
//                for (int j = 0; j < fileName.length; j++) {
                    fileName[len] = (char) (random.nextInt('z' - 'A') + 'A');
                    if (fileName[len] <= 'Z' || fileName[len] >= 'a') {
                        len++;
                    }
                }

                fileNameString = new String(fileName);
                if (!existNames.contains(fileNameString)) {
                    existNames.add(fileNameString);
                    exist = false;
                    System.out.println(fileNameString);
                }
            } while (exist);

            System.out.printf("File name = %s\n", fileNameString);
            File file = new File("./files/" + fileNameString);
            file.createNewFile();
        }


    }

    private static Optional<Integer> calcCountOfFiles() {
        return Optional.ofNullable(new File("./files/").list().length);
    }
}
