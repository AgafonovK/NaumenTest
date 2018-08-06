import com.xvr.ISearcher;
import com.xvr.Searcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TreeSet and red-black search: (10_000)
// average time = 50 ms                Time to refresh = 150-170 ms       first start = 100 ms
// TreeSet and max sort:
// average time = 2 ms                 Time to refresh = same ↑           first start =  30 ms
//
// ArrayList<ClassFile> and binary search: (10_000)
// average time = 30 ms                Time to refresh = 150-200 ms       first start = 240 ms
// ArrayList<ClassFile> and max search:
// average time = 11.444712327 ms      Time to refresh = same ↑           first start = 55 ms
// ArrayList<ClassFile> and max sort:
// average time = 4.7496814157         Time to refresh = same ↑           first start = 45 ms


public class SearcherTest2 {
    static final boolean ALL_TEST = true;
    static final int NUM_OF_LAUNCH = ALL_TEST ? 100 : 10_000;
    static ISearcher searcher = new Searcher();
    static List<Class> classes;

    @BeforeAll
    static void refresh() {
//        final Set<String> classNames = new HashSet<>();
        final ArrayList<String> classNames = new ArrayList<>(96864);

        Random random = new Random();

        try (BufferedReader reader = new BufferedReader(new FileReader("eq_file_names"))) {
            while (reader.ready()) {
                classNames.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(classNames.size() == 93674, "num of file's name = " + classNames.size());

        long[] classDates = new long[classNames.size()];
        for (int i = 0; i < classNames.size(); i++) {
            classDates[i] = random.nextLong();
        }

        final long start = System.currentTimeMillis();
        searcher.refresh(classNames.toArray(new String[0]), classDates);
        final long end = System.currentTimeMillis() - start;
        System.out.println("Time to refresh = " + end + "ms");
        assertTrue(System.currentTimeMillis() - start < 300);

        classes = new ArrayList<>(classNames.size());
        for (int i = 0; i < classNames.size(); i++) {
            classes.add(new Class(classNames.get(i), classDates[i]));
        }
    }

    @Test
    void guessLoop() {
        List<Long> times = new ArrayList<>(NUM_OF_LAUNCH);

        int count = 0;
        while (count < NUM_OF_LAUNCH) {
            final char[] rndChars = new char[3];
            final StringBuilder query = new StringBuilder("a");

            final long start = System.nanoTime();
            final String[] result = searcher.guess(query.toString());
            times.add(System.nanoTime() - start);

            // Слишком долго проверяет
            if (ALL_TEST) {
                assertArrayEquals(
                        classes
                                .parallelStream()
                                .filter(s -> s.getName().startsWith(query.toString()))
                                .sorted(Comparator.comparing(Class::getDate, Comparator.reverseOrder()).thenComparing(Class::getName))
                                .limit(12)
                                .map(Class::getName)
                                .toArray(String[]::new),
                        result
                );
            }

            System.out.printf("i = %6d; count of result = %2d; times = %8d nanos\n", count, result.length, times.get(times.size() - 1));
            count++;
//            System.out.println(count + ";  " + times.get(times.size() - 1));
        }

        times.stream()
                .mapToLong(Long::longValue)
                .average()
                .ifPresent(value -> {
                    System.out.println("average time = " + (value / 1_000_000));
//                    assertTrue(value <= 30_000_000);
                });
    }
}
