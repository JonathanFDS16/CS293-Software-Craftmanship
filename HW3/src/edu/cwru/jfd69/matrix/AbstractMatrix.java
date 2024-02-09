package edu.cwru.jfd69.matrix;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

public abstract class AbstractMatrix<I, T> implements Matrix<I, T> {

    private final NavigableMap<I, T> matrix;

    private final T zero;


    protected AbstractMatrix(NavigableMap<I, T> matrix, T zero) {
        this.matrix = matrix;
        this.zero = zero;
    }

    /**
     * Return an element T at given index.
     * @param index location in the matrix
     * @return the element T at given index. Zero if the such no such index maps to an element.
     * @throws NullPointerException when the index is invalid.
     */
    public T value(I index) throws NullPointerException {
        Objects.requireNonNull(index);
        return matrix.getOrDefault(index, zero);
    }

    /**
     * Return the zero of the matrix T type
     * @return the zero T type.
     */
    public T zero() {
        return zero;
    }

    /**
     * Return a copy of the binary tree representation of the matrix
     * @return the copy of the binary tree representation of the matrix
     */
    public NavigableMap<I, T> representation() {
        return new TreeMap<>(matrix);
    }

    @Override
    public PeekingIterator<Map.Entry<I, T>> peekingIterator() {
        return PeekingIterator.from(representation().entrySet().iterator());
    }
}
