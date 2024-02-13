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
        MergeParameters<K, V> parameter = new MergeParameters<>(origin, zero, zero);

        while (itThis.hasNext() || itOther.hasNext()) {
            MergeParameters<K, V> itParameter;
            if (itThis.hasNext() && itOther.hasNext())
                itParameter = MergeParameters.stepParameter(itThis, itOther, comparator, parameter);
            else if (itThis.hasNext())
                itParameter = MergeParameters.stepParameter(itThis, parameter::setX);
            else
                itParameter = MergeParameters.stepParameter(itOther, parameter::setY);

            newMap.put(itParameter.index(), op.apply(itParameter.x(), itParameter.y()));
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

            int compareResult = comparator.compare(itThis.element().getKey(), itOther.element().getKey());

            if (compareResult == 0)
                return mergeParameters.setX(itThis.next()).setY(itOther.next());
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
