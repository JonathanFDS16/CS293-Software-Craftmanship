package edu.cwru.jfd69.matrix;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PeekingIteratorTest {


    @Test
    public void testPeekingIterator() {
        List<Integer> list = createIntegerList();
        Iterator<Integer> listIt = list.iterator();
        PeekingIterator<Integer> peekIterator = PeekingIterator.from(list);

        while (peekIterator.hasNext()) {
            int expected = listIt.next();

//            System.out.println(peekIterator.element());
            assertEquals(Optional.of(expected), peekIterator.peek());
            assertEquals(expected, peekIterator.element());
            assertTrue(peekIterator.hasNext());
            assertEquals(expected, peekIterator.next());

        }

        assertThrows(NoSuchElementException.class, peekIterator::element);

    }

    @Test
    public void testPeekingIteratorWithMatrix() {
        Integer[][] matrix = {
                {1,  2,  3,  4},
                {5,  6,  7,  8},
                {9,  10, 11, 12},
                {13, 14, 15, 16}
        };
        NavigableMatrix<Integer> navigableMatrix = NavigableMatrix.from(matrix, 0);
        Iterator<Map.Entry<Indexes, Integer>> it = navigableMatrix.representation().entrySet().iterator();
        PeekingIterator<Map.Entry<Indexes, Integer>> peekIterator = navigableMatrix.peekingIterator();

        while (peekIterator.hasNext()) {
            Map.Entry<Indexes, Integer> expected = it.next();

//            System.out.println(peekIterator.element());
            assertEquals(Optional.of(expected), peekIterator.peek());
            assertEquals(expected, peekIterator.element());
            assertTrue(peekIterator.hasNext());
            assertEquals(expected, peekIterator.next());

        }

        assertThrows(NoSuchElementException.class, peekIterator::element);
    }

    private List<Integer> createIntegerList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 11; i++)
            list.add(i);
        return list;
    }

}