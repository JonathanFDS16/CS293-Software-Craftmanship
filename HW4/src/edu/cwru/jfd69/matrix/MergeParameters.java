package edu.cwru.jfd69.matrix;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

/**
 * Keep track of important parts of a merge iteration
 * @param index the index
 * @param x the element of one matrix
 * @param y the element of the second matrix
 * @param <K> the index Type
 * @param <V> the element Type
 */
record MergeParameters<K, V>(K index, V x, V y) {

    /**
     * Steps the parameter according to the iterators indexes
     * @param itThis the first matrix peekingIterator
     * @param itOther the second matrix peekingIterator
     * @param comparator the comparator that defines the order of indexes
     * @param mergeParameters the base parameter
     * @return Return a new MergeParameter with the new parameters
     * @param <K> the index Type
     * @param <V> the element Type
     */
    static <K, V> MergeParameters<K, V> stepParameter(
            PeekingIterator<Map.Entry<K, V>> itThis,
            PeekingIterator<Map.Entry<K, V>> itOther,
            Comparator<? super K> comparator,
            MergeParameters<K, V> mergeParameters
    ) {
        int compareEntries = comparator.compare(itThis.element().getKey(), itOther.element().getKey());
        if (compareEntries == 0)
            return mergeParameters.setX(itThis.next()).setY(itOther.next());
        else if (compareEntries < 0)
            return mergeParameters.setX(itThis.next());
        else
            return mergeParameters.setY(itOther.next());
    }

    /**
     * Steps the parameter
     * @param iterator the iterator with elements
     * @param parameters the base parameter
     * @return a new MergeParameter with the new parameters
     * @param <K> the index Type
     * @param <V> the element Type
     */
    static <K, V> MergeParameters<K, V> stepParameter(
            PeekingIterator<Map.Entry<K, V>> iterator,
            Function<Map.Entry<K, V>, MergeParameters<K, V>> parameters
    ) {
        return parameters.apply(iterator.next());
    }

    // Sets the X of the MergeParameter
    MergeParameters<K, V> setX(Map.Entry<K, V> contents) {
        return new MergeParameters<>(contents.getKey(), contents.getValue(), y);
    }

    // Sets the Y of the MergeParameter
    MergeParameters<K, V> setY(Map.Entry<K, V> contents) {
        return new MergeParameters<>(contents.getKey(), x, contents.getValue());
    }
}