package edu.cwru.jfd69.matrix;

import org.junit.jupiter.api.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class NavigableVectorTest {

    @Test
    public void TestValueAndFrom() {
        NavigableMap<Integer, Integer> vector = new TreeMap<>();
        vector.put(0, 0);
        vector.put(1, 4);
        vector.put(2, 0);
        vector.put(3, 6);
        vector.put(4, 0);

        NavigableVector<Integer> newVector = NavigableVector.from(vector, 0);
        assertEquals(0, newVector.value(0));
        assertEquals(4, newVector.value(1));
        assertEquals(0, newVector.value(2));
        assertEquals(6, newVector.value(3));
        assertEquals(0, newVector.value(4));

        assertThrows(NullPointerException.class,
                () -> {
            newVector.value(null);
                });

    }

    @Test
    public void TestZero() {
        NavigableMap<Integer, Integer> vector = new TreeMap<>();
        NavigableVector<Integer> newVector = NavigableVector.from(vector, 0);
        assertEquals(0, newVector.zero());
    }
}