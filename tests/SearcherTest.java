import com.xvr.ISearcher;
import com.xvr.Searcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Valentin solution: average time = 17.765575276859998 (100_000 test)
// TreeSet and parallel stream: average time = 1.37513018474 (100_000 test)
// ClassFile[] and parallel stream: average time = 0.8471331787499999 (100_000 test)
// ClassFile[] and binary search:            average time = 0.007657183339999999 (100_000 test)
// ClassFile[] and binary search and stream: average time = 0.0076231347900000005 //влияния нет либо незначительное
//                                           average time = 0.007732026380000001
//                                           average time = 0.00673369889
//                                           average time = 0.057732810149999994 // исправлена ошибка сортировки
//                                           average time = 0.05784831602
//                                           average time = 0.06187080789 // ClassFile[] заменен на ArrayList<ClassFile>

// TreeSet and red-black search:
// average time = 0.06 ms                Time to refresh = 145ms       first start = 0.1153634 ms
// TreeSet and max sort:
// average time = 0.014 ms (0.02, r > 0) Time to refresh = 120ms       first start =  ms
//
// ArrayList<ClassFile> and binary search:
// average time = 0.05 ms                Time to refresh = 198ms       first start = 0.7824196 ms
// ArrayList<ClassFile> and max search:
// average time = 0.02 ms                Time to refresh = same ↑      first start = 0.0449962 - 1.2872274 ms (unstable)
// ArrayList<ClassFile> and max sort:
// average time = 0.017 ms               Time to refresh = same ↑      first start = 0.0449962 - 1.2872274 ms (unstable)


class SearcherTest {

    static final boolean ALL_TEST = false;
    static final int NUM_OF_LAUNCH = ALL_TEST ? 1000 :100_000;
    static ISearcher searcher = new Searcher();
    static ISearcher searcherToSortTest = new Searcher();
    static List<Class> classes;
//    static List<String> classNames;

    /**
     * Тест на нагрузку
     */
    @BeforeAll
    static void refresh() {
        final ArrayList<String> classNames = new ArrayList<>(96864);
//        classNames = new ArrayList<>(96864);

        Random random = new Random();

        try (BufferedReader reader = new BufferedReader(new FileReader("file_names"))) {
            while (reader.ready()) {
                classNames.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(classNames.size() == 96864, "num of file's name = " + classNames.size());

        long[] classDates = new long[classNames.size()];
        for (int i = 0; i < classNames.size(); i++) {
            classDates[i] = random.nextLong();
        }

        final long start = System.currentTimeMillis();
        searcher.refresh(classNames.toArray(new String[0]), classDates);
        final long end = System.currentTimeMillis() - start;
        System.out.println("Time to refresh = " + end + "ms");
        assertTrue(end < 1000);

        classes = new ArrayList<>(classNames.size());
        for (int i = 0; i < classNames.size(); i++) {
            classes.add(new Class(classNames.get(i), classDates[i]));
        }
    }


//    Array list
//    binary search =    494709 nanos
//    search offset =     18474 nanos
//    fill result   =     42697 nanos
//    sort result   =    814114 nanos
//    map to string =      8622 nanos

    /**
     * Тест на верную сортировку
     */
    @Test
    void sort() {
        Class[] actualClasses = {
                new Class("aab", 2L), // 0
                new Class("aac", 2L), // 1
                new Class("aaw", 2L), // 2

                new Class("aaa", 1L), // 3
                new Class("aabb", 1L), // 4
                new Class("aad", 1L), // 5
                new Class("aae", 1L), // 6
                new Class("aan", 1L), // 7
                new Class("aar", 1L), // 8
                new Class("aas", 1L), // 9
                new Class("aat", 1L), // 10
                new Class("aav", 1L), // 11
                new Class("aavv", 1L), // 12

                new Class("abd", 2L), // 13
                new Class("baa", 2L), // 14
                new Class("Aaa", 2L), // 15
                new Class("AAa", 1L), // 16

        };

        final Class[] shuffleClasses = actualClasses.clone();
        Collections.shuffle(Arrays.asList(shuffleClasses));
        final String[] classNames = Arrays.stream(shuffleClasses).map(Class::getName).toArray(String[]::new);
        final long[] classDates = Arrays.stream(shuffleClasses).mapToLong(Class::getDate).toArray();
        searcherToSortTest.refresh(classNames, classDates);

        String[] result = searcherToSortTest.guess("aa");
//        Arrays.stream(result).forEach(System.out::println);
        assertEquals(actualClasses[0].getName(), result[0]);
        assertEquals(actualClasses[1].getName(), result[1]);
        assertEquals(actualClasses[2].getName(), result[2]);
        assertEquals(actualClasses[3].getName(), result[3]);
        assertEquals(actualClasses[4].getName(), result[4]);
        assertEquals(actualClasses[5].getName(), result[5]);
        assertEquals(actualClasses[6].getName(), result[6]);
        assertEquals(actualClasses[7].getName(), result[7]);
        assertEquals(actualClasses[8].getName(), result[8]);
        assertEquals(actualClasses[9].getName(), result[9]);
        assertEquals(actualClasses[10].getName(), result[10]);
        assertEquals(actualClasses[11].getName(), result[11]);
//        assertEquals(classes[1].getName(), result[3]);

//        assertEquals(actualClasses.length, searcher.size());
    }

    //    ClassFile[]
//    binary search =    597756 nanos
//    search offset =      8211 nanos
//    fill result   =      8211 nanos
//    sort result   =    888012 nanos
//    map to string =     11085 nanos
//    @Disabled

    @RepeatedTest(5)
    void guessLoop() {
        List<Long> times = new ArrayList<>(NUM_OF_LAUNCH);
        Random random = new Random();

        int count = 0;
        while (count < NUM_OF_LAUNCH) {
            final char[] rndChars = new char[3];
            final StringBuilder query = new StringBuilder();
            for (int i = 0; i < random.nextInt(5) + 1; i++) {
                rndChars[0] = (char) (random.nextInt('Z' - 'A') + 'A');
                rndChars[1] = (char) (random.nextInt('z' - 'a') + 'a');
                rndChars[2] = (char) (random.nextInt('9' - '0') + '0');
                query.append((char) rndChars[random.nextInt(3)]);
            }

            final long start = System.nanoTime();
            final String[] result = searcher.guess(query.toString());
            final long end = System.nanoTime();
            assertTrue(result.length <= 12);
            if (result.length > 0) {
                times.add(end - start);
            }

//            System.out.println("query = " + query.toString());
            assertTrue(Arrays.stream(result).allMatch(s -> s.startsWith(query.toString())), () ->
                    Arrays.stream(result).reduce("query = " + query.toString(), (s, s2) -> s + "\n" + s2 + "\n")
            );

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


//            assertEquals(96864, searcher.size());
            System.out.printf("i = %6d; count of result = %2d; times = %8d nanos\n", count, result.length, (end - start));
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