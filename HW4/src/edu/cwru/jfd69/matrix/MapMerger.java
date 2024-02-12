package edu.cwru.jfd69.matrix;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;

final class MapMerger {

    public static <K, V> NavigableMap<K, V> merge(
            PeekingIterator<Map.Entry<K, V>> itThis,
            PeekingIterator<Map.Entry<K, V>> itOther,
            Comparator<? super K> comparator,
            BinaryOperator<V> op,
            K origin,
            V zero
            ) {

        // itThis has the all entries of one matrix
        // itOther has all the entries of another matrix
        // Both iterators representation are ordered by something else
        // The comparator will order the way the entries are collected
        // the Op (Operations) at matrix
        // K is origin to start
        // V is zero of the Matrix

        MergeParameters<K, V> parameter = new MergeParameters<>(
                itThis.element().getKey(),
                itThis.element().getValue(),
                itOther.element().getValue());

        while (itThis.hasNext() && itOther.hasNext()) {
            // Make operation

            MergeParameters.stepParameter(itThis, itOther, comparator, parameter);
        }

        MergeParameters.stepParameter(itThis, (kvEntry) -> null/*new Parameter*/);

        return null;
    }

    private record MergeParameters<K, V>(K index, V x, V y) {

        private static <K, V> MergeParameters<K, V> stepParameter(
                PeekingIterator<Map.Entry<K, V>> itThis,
                PeekingIterator<Map.Entry<K, V>> itOther,
                Comparator<? super K> comparator,
                MergeParameters<K, V> mergeParameters
        ) {
            return null;
        }

        private static <K, V> MergeParameters<K, V> stepParameter(
                PeekingIterator<Map.Entry<K, V>> iterator,
                Function<Map.Entry<K, V>, MergeParameters<K, V>> parameters
        ) {
            return null;
        }

        public MergeParameters<K, V> setX(Map.Entry<K, V> contents) {
            return new MergeParameters<>(contents.getKey(), contents.getValue(), y);
        }

        public MergeParameters<K, V> setY(Map.Entry<K, V> contents) {
            return new MergeParameters<>(contents.getKey(), x, contents.getValue());
        }
    }
}
