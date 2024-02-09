package edu.cwru.jfd69.matrix;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class IndexesTest {

    @Test
    public void TestORIGIN(){
        Indexes actualOrigin = new Indexes(0, 0);
        assertEquals(Indexes.ORIGIN, actualOrigin);
    }

    @Test
    public void TestWithRow() {
        Indexes index = new Indexes(10, 10);
        Indexes actualWithRow = index.withRow(20);
        Indexes expectedWithRow = new Indexes(20, 10);
        assertEquals(expectedWithRow, actualWithRow);
    }

    @Test
    public void TestWithColumn() {
        Indexes index = new Indexes(10, 10);
        Indexes actualWithColumn = index.withColumn(50);
        Indexes expectedWithColumn = new Indexes(10, 50);
        assertEquals(expectedWithColumn, actualWithColumn);
    }

    @Test
    public void TestStream() {
        Stream<Indexes> stream = Indexes.stream(new Indexes(0,0), new Indexes(2, 3));

        List<Indexes> expectedList = new ArrayList<>(listOfIndexes());
        List<Indexes> listStream = stream.toList();
        int index = 0;
        for (Indexes i : listStream) {
            assertEquals(i, expectedList.get(index++));
        }
    }

    @Test
    public void TestStream1() {
        Stream<Indexes> stream = Indexes.stream(new Indexes(2, 3));

        List<Indexes> expectedList = new ArrayList<>(listOfIndexes());
        List<Indexes> listStream = stream.toList();
        int index = 0;
        for (Indexes i : listStream) {
            assertEquals(i, expectedList.get(index++));
        }
    }

    @Test
    public void TestStream2() {
        Stream<Indexes> stream = Indexes.stream(2, 3);

        List<Indexes> expectedList = new ArrayList<>(listOfIndexes());
        List<Indexes> listStream = stream.toList();
        int index = 0;
        for (Indexes i : listStream) {
            assertEquals(i, expectedList.get(index++));
        }
    }

    @Test
    public void TestByRows() {
        List<Indexes> list = listOfRandomIndexes();
        List<Indexes> expectedList = new ArrayList<>(listOfIndexes());
        list.sort(Indexes.byRows);
        int index = 0;
        for (Indexes i : list)
            assertEquals(i, expectedList.get(index++));
    }

    @Test
    public void TestByColumn() {
        List<Indexes> list = listOfRandomIndexes();
        List<Indexes> expectedList = new ArrayList<>(listOfIndexesByColumn());
        list.sort(Indexes.byColumns);
        int index = 0;
        for (Indexes i : list)
            assertEquals(i, expectedList.get(index++));
    }

    @Test
    public void TestValueMethod() {
        Integer[][] matrix = {
                {0,  1,  2,  4},
                {5,  6,  7,  8},
                {9,  10, 11, 12},
                {13, 14, 15, 16}
        };

        Indexes origin = Indexes.ORIGIN;
        Indexes twotwo = new Indexes(2, 2);
        Indexes threethree = new Indexes(3, 3);
        Indexes outOfBoundsIndex = new Indexes(10, 10);
        assertEquals(0, origin.value(matrix));
        assertEquals(11, twotwo.value(matrix));
        assertEquals(16, threethree.value(matrix));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {
            outOfBoundsIndex.value(matrix);
                });
    }

    @Test
    public void TestAreDiagonal() {
        Indexes index = Indexes.ORIGIN;
        Indexes oneone = new Indexes(1, 1);
        Indexes bigDiagonal = new Indexes(100, 100);
        Indexes notDiagonal = new Indexes(10, 5);

        assertTrue(index.areDiagonal());
        assertTrue(oneone.areDiagonal());
        assertTrue(bigDiagonal.areDiagonal());
        assertFalse(notDiagonal.areDiagonal());

    }


    public static List<Indexes> listOfIndexes() {
        List<Indexes> list = new ArrayList<>();
        list.add(new Indexes(0,0));
        list.add(new Indexes(0,1));
        list.add(new Indexes(0,2));
        list.add(new Indexes(1,0));
        list.add(new Indexes(1,1));
        list.add(new Indexes(1,2));
        return list;
    }

    private List<Indexes> listOfIndexesByColumn() {
        List<Indexes> list = new ArrayList<>();
        list.add(new Indexes(0,0));
        list.add(new Indexes(1,0));
        list.add(new Indexes(0,1));
        list.add(new Indexes(1,1));
        list.add(new Indexes(0,2));
        list.add(new Indexes(1,2));
        return list;
    }

    private List<Indexes> listOfRandomIndexes() {
        List<Indexes> list = new ArrayList<>();
        list.add(new Indexes(1,0));
        list.add(new Indexes(1,2));
        list.add(new Indexes(0,0));
        list.add(new Indexes(1,1));
        list.add(new Indexes(0,1));
        list.add(new Indexes(0,2));
        return list;
    }

}