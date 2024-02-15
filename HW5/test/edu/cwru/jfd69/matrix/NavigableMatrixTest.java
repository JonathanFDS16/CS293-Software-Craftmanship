package edu.cwru.jfd69.matrix;

import org.junit.jupiter.api.Test;

import java.util.*;

import static edu.cwru.jfd69.matrix.IndexesTest.listOfIndexes;
import static org.junit.jupiter.api.Assertions.*;

class NavigableMatrixTest {

    @Test
    public void testInstance() {
        NavigableMatrix<Integer> navMatrix = NavigableMatrix.instance(10, 10, (i) -> 2, 0);
        Collection<Integer> collection = navMatrix.representation().values();
//        printMatrix(collection, 10);

        for (Integer i : collection)
            assertEquals(2, i);

        assertThrows(IllegalArgumentException.class, () -> {
            NavigableMatrix.instance(-1, -1, (i) -> 2, 0);
            NavigableMatrix.instance(0,0,(i) -> 2,0);
        });
    }

    @Test
    public void testConstant() {
        NavigableMatrix<Integer> navMatrix = NavigableMatrix.constant(10, 10, 1, 0);
        Collection<Integer> collection = navMatrix.representation().values();
//        printMatrix(collection, 10);

        for (Integer i : collection)
            assertEquals(i, 1);
    }

    @Test
    public void testIdentity() {
        // Use variable
        NavigableMatrix<Integer> navMatrix = NavigableMatrix.identity(10, 0, 1);
        Collection<Integer> collection = navMatrix.representation().values();
//        printMatrix(collection, 10);

        for (int r = 0; r < 10; r++) {
            for (int col = 0; col < 10; col++) {
                Indexes index = new Indexes(r, col);
                if (index.areDiagonal())
                    assertEquals(navMatrix.value(index), 1);

                else assertEquals(navMatrix.value(index), 0);
            }
        }
    }

    @Test
    public void testFrom() {
        NavigableMatrix<Integer> navMatrix = NavigableMatrix.from(createMatrix(), 0);
        Collection<Integer> collection = navMatrix.representation().values();
//        printMatrix(collection, 3);

        int expectedValue = 0;
        for (Integer i : collection) {
            assertEquals(i, expectedValue++);
        }
    }

    @Test
    public void testFrom2() {
        Integer[][] matrix = {
                {1,  2,  3,  4},
                {5,  6,  7,  8},
                {9,  10, 11, 12},
                {13, 14, 15, 16}
        };

        NavigableMatrix<Integer> navMatrix = NavigableMatrix.from(matrix, 0);
        Collection<Integer> collection = navMatrix.representation().values();
//        printMatrix(collection, 4);

        List<Indexes> indexes = create4x4Indexes();
        Iterator<Indexes> it = indexes.iterator();
        for (Integer integer : collection) {
                assertEquals(it.next().value(matrix), integer);
        }

    }

    private void printMatrix(Collection<Integer> col, int size) {
        int count = 0;
        for (Integer i : col) {
            System.out.print(i + " ");
            count++;
            if (count == size) {
                System.out.println();
                count = 0;
            }
        }
        System.out.println();
    }

    private NavigableMap<Indexes, Integer> createMatrix() {
        NavigableMap<Indexes, Integer> matrix = new TreeMap<>();
        List<Indexes> indexesList = listOfIndexes();
        int value = 0;
        for (Indexes i : indexesList) {
            matrix.put(i, value++);
        }
        return matrix;
    }

    private List<Indexes> create4x4Indexes() {
        List<Indexes> list = new ArrayList<>();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                list.add(new Indexes(r, c));
            }
        }
        return list;
    }
}