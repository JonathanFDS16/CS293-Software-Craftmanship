package edu.cwru.jfd69.matrix;

import java.util.*;
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

        NavigableMap<K, V> newMap = new TreeMap<>(comparator);
        MergeParameters<K, V> parameter;

        while (itThis.hasNext() && itOther.hasNext()) {
            // Make operation
            parameter = new MergeParameters<>(origin, zero, zero);

            parameter = MergeParameters.stepParameter(itThis, itOther, comparator, parameter);

            newMap.put(parameter.index(), op.apply(parameter.x(), parameter.y()));
        }

        while (itThis.hasNext()) {
            parameter = new MergeParameters<>(origin, zero, zero);

            MergeParameters<K, V> finalParameter = parameter;
            parameter = MergeParameters.stepParameter(itThis,
                    (kvEntry) -> finalParameter.setX(itThis.next()));

            newMap.put(parameter.index(), op.apply(parameter.x(), parameter.y()));
        }

        return newMap;
    }

    private record MergeParameters<K, V>(K index, V x, V y) {

        private static <K, V> MergeParameters<K, V> stepParameter(
                PeekingIterator<Map.Entry<K, V>> itThis,
                PeekingIterator<Map.Entry<K, V>> itOther,
                Comparator<? super K> comparator,
                MergeParameters<K, V> mergeParameters
        ) {

            /*Algorithm;
            1. Get next Entry<KV> for each iterator
            2. Get the smallest index using comparator
            3. if (indexes are the same) then return. Else return the smallest (non-zero, zero)
            * */
            int compareResult = comparator.compare(itThis.element().getKey(), itOther.element().getKey());

            if (compareResult == 0)
                return new MergeParameters<>(itThis.next().getKey(), itThis.next().getValue(), itOther.next().getValue());
            else if (compareResult < 0) {
                return mergeParameters.setX(itThis.next());
            }
            else {
                return mergeParameters.setY(itOther.next());
            }
        }

        private static <K, V> MergeParameters<K, V> stepParameter(
                PeekingIterator<Map.Entry<K, V>> iterator,
                Function<Map.Entry<K, V>, MergeParameters<K, V>> parameters
        ) {
            return parameters.apply(iterator.next());
        }

        public MergeParameters<K, V> setX(Map.Entry<K, V> contents) {
            return new MergeParameters<>(contents.getKey(), contents.getValue(), y);
        }

        public MergeParameters<K, V> setY(Map.Entry<K, V> contents) {
            return new MergeParameters<>(contents.getKey(), x, contents.getValue());
        }
    }
}
