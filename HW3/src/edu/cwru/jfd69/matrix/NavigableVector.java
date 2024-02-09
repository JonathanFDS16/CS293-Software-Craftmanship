package edu.cwru.jfd69.matrix;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

public final class NavigableVector<T> extends AbstractMatrix<Integer, T> {

    private NavigableVector(NavigableMap<Integer, T> matrix, T zero) {
        super(matrix, zero);
    }

    /**
     * Returns a new NavigableVector without any zeros.
     * @param vector the vector input for the method
     * @param zero the zero of the function
     * @return a new NavigableVector without the zero elements.
     * @param <S> The type for the method
     * @throws NullPointerException when vector is null.
     */
    public static <S> NavigableVector<S> from(Map<Integer, S> vector, S zero) throws NullPointerException {

        Objects.requireNonNull(vector);
        Objects.requireNonNull(zero);

        Map<Integer, S> vectorCopy = Map.copyOf(vector);
        NavigableMap<Integer, S> newNavMap = new TreeMap<>();

        vectorCopy.forEach((Integer key, S value) ->
        {
            if (!value.equals(zero)) {
                newNavMap.put(key, value);
            } // Otherwise do nothing
        });
        return new NavigableVector<>(newNavMap, zero);
    }

}
