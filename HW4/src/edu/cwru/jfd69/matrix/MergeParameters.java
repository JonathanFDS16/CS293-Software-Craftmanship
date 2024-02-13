package edu.cwru.jfd69.matrix;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

record MergeParameters<K, V>(K index, V x, V y) {

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

    static <K, V> MergeParameters<K, V> stepParameter(
            PeekingIterator<Map.Entry<K, V>> iterator,
            Function<Map.Entry<K, V>, MergeParameters<K, V>> parameters
    ) {
        return parameters.apply(iterator.next());
    }

    MergeParameters<K, V> setX(Map.Entry<K, V> contents) {
        return new MergeParameters<>(contents.getKey(), contents.getValue(), y);
    }

    MergeParameters<K, V> setY(Map.Entry<K, V> contents) {
        return new MergeParameters<>(contents.getKey(), x, contents.getValue());
    }
}