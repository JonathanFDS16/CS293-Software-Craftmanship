package edu.cwru.jfd69.matrix;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MapMergerTest {

    /*
    * Testing Vector:
    * Same size
    * Different size
    * Same zeros
    * Different Zeros
    *
    * Testing Matrices:
    * Same size
    * Different size
    * Same zeros
    * Different zeros*/

    @Test
    public void testVectorSameSize() {

        NavigableVector<Integer> vector = NavigableVector.from(createVector(), 0);
        NavigableVector<Integer> vector2 = NavigableVector.from(createVector(), 0);
        NavigableMap<Integer, Integer> map = vector.merge(vector2, Integer::sum);

        List<Integer> expected = createExpectedListVector();

        int index = 0;
        for (Integer i : map.values()) {
            System.out.println(i);
            assertEquals(expected.get(index++), i);
        }

    }

    @Test
    public void testVectorDifferentSize() {

        NavigableVector<Integer> vector = NavigableVector.from(createVector(), 0);
        NavigableVector<Integer> vector2 = NavigableVector.from(createVectorDifferentSize(), 0);
        NavigableMap<Integer, Integer> map = vector.merge(vector2, Integer::sum);

        List<Integer> expected = createExpectedListVector();
        expected.add(6);
        expected.add(7);

        int count = 0;
        for (Integer i : map.values()) {
            System.out.println(i);
            assertEquals(expected.get(count++), i);
        }

    }

    @Test
    public void testVectorDifferentSizeV2() {

        NavigableVector<Integer> vector = NavigableVector.from(createVectorDifferentSize(), 0);
        NavigableVector<Integer> vector2 = NavigableVector.from(createVector(), 0);
        NavigableMap<Integer, Integer> map = vector.merge(vector2, Integer::sum);

        List<Integer> expected = createExpectedListVector();
        expected.add(6);
        expected.add(7);

        int count = 0;
        for (Integer i : map.values()) {
            System.out.println(i);
            assertEquals(expected.get(count++), i);
        }

    }

    @Test
    public void testVectorDifferentZeros() {

        NavigableVector<Integer> vector = NavigableVector.from(createVector(), 0);
        NavigableVector<Integer> vector2 = NavigableVector.from(createVectorDifferentSize(), 1);
        assertThrows(IllegalArgumentException.class,() -> vector.merge(vector2, Integer::sum));

    }

    @Test
    public void testMatrixSameSize() {

        NavigableMatrix<Integer> matrix = NavigableMatrix.constant(2, 2, 1, 0);
        NavigableMatrix<Integer> matrix2 = NavigableMatrix.constant(2, 2, 1, 0);
        NavigableMap<Indexes, Integer> map = matrix.merge(matrix2, Integer::sum);
        List<Integer> expected = createExpectedListMatrix();

        int count = 0;
        for (Integer i : map.values()) {
            System.out.println(i);
            assertEquals(expected.get(count++), i);
        }

    }

    @Test
    public void testMatrixSameSizeButNoEqualIndex() {
        Integer[][] array = {
                {1,  1,  1,  1},
                {1,  0,  0,  1},
                {1,  0,  0,  1},
                {1,  1,  1,  1}
        };

        Integer[][] array2 = {
                {0,  0,  0,  0},
                {0,  1,  1,  0},
                {0,  1,  1,  0},
                {0,  0,  0,  0}
        };

        NavigableMatrix<Integer> matrix = NavigableMatrix.from(array, 0);

        NavigableMatrix<Integer> matrix2 = NavigableMatrix.from(array2, 0);


        NavigableMap<Indexes, Integer> map = matrix.merge(matrix2, Integer::sum);
        List<Integer> expected = createConstantList();

        int count = 0;
        for (Integer i : map.values()) {
            System.out.println(i);
            assertEquals(expected.get(count++), i);
        }

    }

    @Test
    public void testMatrixDifferentSize() {

        NavigableMatrix<Integer> matrix = NavigableMatrix.constant(3, 3, 1, 0);
        NavigableMatrix<Integer> matrix2 = NavigableMatrix.constant(2, 2, 1, 0);
        NavigableMap<Indexes, Integer> map = matrix.merge(matrix2, Integer::sum);

        List<Integer> expected = createExpectedListMatrixDifferentSize();

        int count = 0;
        int index = 0;
        for (Integer i : map.values()) {
            if (count == 2) {
                System.out.print(i);
                System.out.println();
                assertEquals(expected.get(index++), i);
                count = 0;
            }
            else {
                System.out.print(i);
                assertEquals(expected.get(index++), i);
                count++;
            }
        }

    }
    @Test
    public void testMatrixSameZeros() {

        NavigableMatrix<Integer> matrix = NavigableMatrix.constant(3, 3, 1, 0);
        NavigableMatrix<Integer> matrix2 = NavigableMatrix.constant(2, 2, 1, 0);
        assertDoesNotThrow(() -> matrix.merge(matrix2, Integer::sum));

    }

    @Test
    public void testMatrixDifferentZeros() {

        NavigableMatrix<Integer> matrix = NavigableMatrix.constant(3, 3, 1, 0);
        NavigableMatrix<Integer> matrix2 = NavigableMatrix.constant(2, 2, 1, 9);
        assertThrows(IllegalArgumentException.class,() -> matrix.merge(matrix2, Integer::sum));

    }

    private Map<Integer, Integer> createVector() {
        Map<Integer, Integer> map = new TreeMap<>();
        map.put(0, 0);
        map.put(1, 1); //2
        map.put(2, 2); //4
        map.put(3, 3); //6
        map.put(4, 4); //8
        map.put(5, 5); //10
        return map;
    }

    private Map<Integer, Double> createVectorDouble() {
        Map<Integer, Double> map = new TreeMap<>();
        map.put(0, 0.0);
        map.put(1, 1.0); //2
        map.put(2, 2.0); //4
        map.put(3, 3.0); //6
        map.put(4, 4.0); //8
        map.put(5, 5.0); //10
        return map;
    }

    private Map<Integer, Integer> createVectorDifferentSize() {
        Map<Integer, Integer> map = new TreeMap<>();
        map.put(0, 0);
        map.put(1, 1); //2
        map.put(2, 2); //4
        map.put(3, 3); //6
        map.put(4, 4); //8
        map.put(5, 5); //10
        map.put(6, 6); //6
        map.put(7, 7); //7
        return map;
    }

    private List<Integer> createExpectedListVector() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(6);
        list.add(8);
        list.add(10);
        return list;
    }

    private List<Integer> createExpectedListMatrix() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        return list;
    }

    private List<Integer> createExpectedListMatrixDifferentSize() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(2);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        return list;
    }

    private List<Integer> createConstantList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        return list;
    }

}