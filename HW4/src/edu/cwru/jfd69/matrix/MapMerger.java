package edu.cwru.jfd69.matrix;

import java.util.*;
import java.util.function.BinaryOperator;

/**
 * Class that implements the merge component to matrices
 */
final class MapMerger {

    /**
     * Merge all entries of one matrix with another matrix's entries
     * @param itThis the peeking iterator from the first matrix
     * @param itOther the peeking iterator from the second matrix
     * @param comparator the comparator determines if an idex precedes another one
     * @param op the Operation applied to merge each element
     * @param origin the origin of the matrix
     * @param zero the zero element
     * @return a NavigableMap with the elements merged.
     * @param <K> the index Type
     * @param <V> the element Type
     */
    static <K, V> NavigableMap<K, V> merge(
            PeekingIterator<Map.Entry<K, V>> itThis,
            PeekingIterator<Map.Entry<K, V>> itOther,
            Comparator<? super K> comparator,
            BinaryOperator<V> op,
            K origin,
            V zero
            ) {
        Objects.requireNonNull(itThis);
        Objects.requireNonNull(itOther);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(op);
        Objects.requireNonNull(origin);
        Objects.requireNonNull(zero);

        NavigableMap<K, V> newMap = new TreeMap<>(comparator);
        MergeParameters<K, V> baseParameter = new MergeParameters<>(origin, zero, zero);

        while (itThis.hasNext() || itOther.hasNext()) {
            MergeParameters<K, V> itParameter;
            if (itThis.hasNext() && itOther.hasNext())
                itParameter = MergeParameters.stepParameter(itThis, itOther, comparator, baseParameter);
            else if (itThis.hasNext())
                itParameter = MergeParameters.stepParameter(itThis, baseParameter::setX);
            else
                itParameter = MergeParameters.stepParameter(itOther, baseParameter::setY);

            newMap.put(itParameter.index(), op.apply(itParameter.x(), itParameter.y()));
        }

        return newMap;
    }
}
