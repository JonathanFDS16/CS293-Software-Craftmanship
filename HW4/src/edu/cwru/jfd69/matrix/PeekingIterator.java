package edu.cwru.jfd69.matrix;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

/**
 * PeekingIterator
 * Class provides same functionality as an Iterator, but with the possibility to peek at the next element
 * without advancing the iterator.
 * @param <T> The type to be iterated.
 */
public final class PeekingIterator<T> implements Iterator<T> {

    private final Iterator<T> it;

    private Optional<T> next;

    private PeekingIterator(Iterator<T> it) {
        this.it = it;
        next = checkNextNotEmpty();
    }

    /**
     * Creates a new instance of PeekingIterator from an iterator.
     * @param iterator the iterator of S
     * @return a new instance of PeekingIterator
     * @param <S> the element to be iterated by the PeekingIterator
     */
    public static <S> PeekingIterator<S> from(Iterator<S> iterator) {
        Objects.requireNonNull(iterator);

        return new PeekingIterator<>(iterator);
    }

    /**
     * Creates a new instance of PeekingIterator from an iterator.
     * @param iterable the iterable instance S
     * @return a new instance of PeekingIterator
     * @param <S> the element to be iterated by the PeekingIterator
     */
    public static <S> PeekingIterator<S> from(Iterable<S> iterable) {
        Objects.requireNonNull(iterable);

        return new PeekingIterator<>(iterable.iterator());
    }

    /**
     * Peek to next element of the iterator without advancing the iterator.
     * @return Return an Optional of the next element.
     */
    public Optional<T> peek() {
        return next;
    }

    /**
     * Return the next element without advancing the iterator.
     * @return Return the next element
     */
    public T element() {
        return next.get();
    }


    @Override
    public boolean hasNext() {
        return next.isPresent();
    }

    @Override
    public T next() {
        T save = element();
        next = checkNextNotEmpty();
        return save;
    }

    private Optional<T> checkNextNotEmpty() {
        return it.hasNext() ?  Optional.of(it.next()) : Optional.empty();
    }


}
