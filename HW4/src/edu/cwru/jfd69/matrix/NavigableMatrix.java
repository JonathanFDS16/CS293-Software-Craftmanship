package edu.cwru.jfd69.matrix;

import edu.cwru.jfd69.matrixExceptions.InconsistentZeroException;

import java.io.Serial;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static edu.cwru.jfd69.matrix.NavigableMatrix.InvalidLengthException.*;

public class NavigableMatrix<T> extends AbstractMatrix<Indexes, T> {

    /**
     * Instantiate a NavigableMatrix
     * @param matrix a NavigableMap representing the sparse matrix
     * @param zero the zero representation of T type.
     */
    private NavigableMatrix(NavigableMap<Indexes, T> matrix, T zero) {
        super(matrix, zero);
    }

    /**
     * Flexible way to create a new instance of a NavigableMatrix.
     * @param rows amount of rows in matrix
     * @param columns amount of columns in matrix
     * @param valueMapper Function that returns a value for each indexes in Matrix
     * @param zero the zero representation of type S
     * @return a new instance of NavigableMatrix
     * @param <S> the element stored in the NavigableMatrix
     */
    public static <S> NavigableMatrix<S> instance(int rows, int columns,
                                                  Function<Indexes, S> valueMapper,
                                                  S zero) {
        Objects.requireNonNull(valueMapper);
        Objects.requireNonNull(zero);

        int maxRow = requireNonEmpty(Cause.ROW, rows);
        int maxCol = requireNonEmpty(Cause.COLUMN, columns);
        NavigableMap<Indexes, S> matrix = new TreeMap<>();

        //Indexes.stream
        for (int row = 0; row < maxRow; row++) {

            for (int col = 0; col < maxCol; col++) {

                Indexes index = new Indexes(row, col);
                S value = valueMapper.apply(index);
                if (value != zero)
                    matrix.put(index, value);

            }
        }

        matrix = Collections.unmodifiableNavigableMap(matrix);
        return new NavigableMatrix<>(matrix, zero);
    }

    /**
     * Creates a new instance of a constant NavigableMatrix
     * @param rows amount of rows in NavigableMatrix
     * @param columns amount of columns in NavigableMatrix
     * @param value the constant value to populate the NavigableMatrix
     * @param zero the zero representation of S type
     * @return a new instance of a constant NavigableMatrix
     * @param <S> the element type stored in the matrix
     */
    public static <S> NavigableMatrix<S> constant(int rows, int columns, S value, S zero) {
        return instance(rows, columns, (indexes -> value), zero);
    }

    /**
     * Creates a new instance of an identity NavigableMatrix
     * @param size the size of a square matrix. Size x Size
     * @param zero the zero representation of  c
     * @param identity the value to be placed in the diagonal Indexes
     * @return a new instance of a NavigableMatrix
     * @param <S> the element type stored in the matrix
     */
    public static <S> NavigableMatrix<S> identity(int size, S zero, S identity) {
        return instance(size, size, (index -> index.areDiagonal() ? identity : zero), zero);
    }

    /**
     * Creates a new instance of a NavigableMatrix from a NavigableMap.
     * @param matrix the matrix representation
     * @param zero the zero representation element type S
     * @return a new instance of a NavigableMap
     * @param <S> the element type store in the matrix
     */
    public static <S> NavigableMatrix<S> from(NavigableMap<Indexes, S> matrix, S zero) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(zero);
        return new NavigableMatrix<>(matrix, zero);
    }

    /**
     * Creates a new instance of a NavigableMatrix from a NavigableMap.
     * @param matrix the matrix representation in array form
     * @param zero the zero representation element type S
     * @return a new instance of a NavigableMap
     * @param <S> the element type store in the matrix
     */
    public static <S> NavigableMatrix<S> from(S[][] matrix, S zero) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(zero);
        return instance(matrix.length, matrix[0].length, (indexes -> indexes.value(matrix)), zero);
    }

    @Override
    public NavigableMap<Indexes, T> merge(Matrix<Indexes, T> other, BinaryOperator<T> op) {
        return MapMerger.merge(this.peekingIterator(), other.peekingIterator(), Indexes.byRows, op, Indexes.ORIGIN,
                InconsistentZeroException.requireMatching(this, other));
    }

    /**
     * InvalidLengthException
     * Represent an exception for length <= 0.
     */
    static class InvalidLengthException extends Exception {

        @Serial
        private static final long serialVersionUID = 1234781234176235416L;

        private final Cause cause;

        private final int length;

        /**
         * Creates an instance of an InvalidLengthException
         * @param cause the cause of such exception
         * @param length the input to be checked.
         */
        public InvalidLengthException(Cause cause, int length) {
            this.cause = cause;
            this.length = length;
        }

        /**
         * Checks and return the length if not <= 0.
         * @param cause The cause for an exception
         * @param length the length input to be checked.
         * @return the length if valid.
         */
        public static int requireNonEmpty(Cause cause, int length) {
            if (length <= 0)
                throw new IllegalArgumentException(new InvalidLengthException(cause, length));

            else return length;
        }

        public Cause getTheCause() {
            return cause;
        }

        public int getLength() {
            return length;
        }

         enum Cause {
            ROW, COLUMN
        }

    }
}
