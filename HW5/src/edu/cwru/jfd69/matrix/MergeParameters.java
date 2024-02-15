package edu.cwru.jfd69.matrix;

import java.util.Map;

/**
 * Keep track of important parts of a merge iteration
 * @param index the index
 * @param x the element of one matrix
 * @param y the element of the second matrix
 * @param <K> the index Type
 * @param <V> the element Type
 */
record MergeParameters<K, V>(K index, V x, V y) {

    // Sets the X of the MergeParameter
    MergeParameters<K, V> setX(Map.Entry<K, V> contents) {
        assert contents != null : "Contents are null";
        return new MergeParameters<>(contents.getKey(), contents.getValue(), y());
    }

    // Sets the Y of the MergeParameter
    MergeParameters<K, V> setY(Map.Entry<K, V> contents) {
        assert contents != null : "Contents are null";
        return new MergeParameters<>(contents.getKey(), x(), contents.getValue());
    }
}