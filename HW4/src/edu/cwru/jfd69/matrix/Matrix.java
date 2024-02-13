package edu.cwru.jfd69.matrix;

import java.util.Map;
import java.util.NavigableMap;
import java.util.function.BinaryOperator;

/**
 * An object that represents a matrix.
 * @param <I> the index of the matrix
 * @param <T> the type of values stored in matrix
 */
public interface Matrix<I, T> {

    /**
     * Returns an element from a given index
     * @param index location in the matrix
     * @return element at given index
     */
    T value(I index);

    /**
     * Returns the zero of the type T.
     * @return The zero of the type T
     */
    T zero();

    /**
     * Return a copy of the binary tree representation of the Matrix
     * @return the copy representation of the matrix.
     */
    NavigableMap<I, T> representation();

    /**
     * Return the peekingIterator for this matrix
     * @return the PeekingIterator for this matrix
     */
    PeekingIterator<Map.Entry<I, T>> peekingIterator();

    /**
     * Return a NavigableMap with indexes merged by the operation.
     * @param other the other matrix to be merged
     * @param op the operation to be applied to the merge
     * @return the NavigableMap
     */
    NavigableMap<I, T> merge(Matrix<I, T> other, BinaryOperator<T> op);

}
