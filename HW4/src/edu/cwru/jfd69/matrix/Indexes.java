package edu.cwru.jfd69.matrix;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.stream.Stream;

public record Indexes(int row, int column) implements Comparable<Indexes> {

    /**
     * ORIGIN (0, 0)
     */
    public static final Indexes ORIGIN = new Indexes(0, 0);

    /**
     * Comparator by Rows:
     * First index1 < index2 if row1 < row2.
     * If index1.row == index2.row. Then index1 < index2 if column1 < column2
     */
    public static final Comparator<Indexes> byRows = (index1, index2) ->
            compareByRowOrByColumn(index1, index2, true);

    /**
     * Comparator by Column:
     * First index1 < index2 if column1 < column2.
     * If index1.column == index2.column. Then index1 < index2 if row1 < row2
     */
    public static final Comparator<Indexes> byColumns = (index1, index2) ->
            compareByRowOrByColumn(index1, index2, false);

    private static int compareByRowOrByColumn(Indexes index1, Indexes index2, boolean byRows) {
        int byRow = Integer.compare(index1.row(), index2.row());
        int byColumn = Integer.compare(index1.column(), index2.column());
        if (byRows)
            return ifZeroReturn(byRow, byColumn);
        return ifZeroReturn(byColumn, byRow);
    }

    private static int ifZeroReturn(int checkValue, int returnValue) {
        return checkValue != 0 ? checkValue : returnValue;
    }

    /**
     * Returns the Indexes with the given row and this column
     * @param row the row index
     * @return an Indexes with row and this column
     */
    public Indexes withRow(int row) {
        if (row < 0) throw new IllegalArgumentException("Negative row invalid");
        else {
            return new Indexes(row, this.column());
        }
    }

    /**
     * Returns the Indexes with the given column and this row
     * @param column the column index
     * @return an Indexes with this row and column
     */
    public Indexes withColumn(int column) {
        if (column < 0 ) throw new IllegalArgumentException("Negative column invalid");
        else {
            return new Indexes(this.row(), column);
        }
    }

    /**
     * Return a Stream of Indexes with a sequence of Indexes from --> to, excluding the latter.
     * @param from the starting index including
     * @param to the end index excluding
     * @return a Stream of Indexes with the sequence of Indexes in order.
     * @throws IllegalArgumentException if from or to are null objects.
     */
    public static Stream<Indexes> stream(Indexes from, Indexes to) throws IllegalArgumentException {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        return Stream.iterate(from,
                (indexTest) -> indexTest.row() != to.row() &&
                        indexTest.column() != to.column(),
                (index) -> getNextIndex(index, to));
    }

    private static Indexes getNextIndex(Indexes currentIndex, Indexes lastIndex) {
        assert currentIndex != null : "Invalid null currentIndex argument";
        assert lastIndex != null : "Invalid null lastIndex argument";

        return  currentIndex.column() + 1 == lastIndex.column() ?
                new Indexes(currentIndex.row() + 1, 0) :
                new Indexes(currentIndex.row(), currentIndex.column() + 1);
    }

    /**
     * Return a Stream of Indexes with a sequence of Indexes 0,0 --> to, excluding the latter
     * @param size the end index excluding.
     * @return A Stream with Indexes from 0, 0 --> size;
     */
    public static Stream<Indexes> stream(Indexes size) {
        return stream(ORIGIN, size);
    }

    /**
     * Return a Stream of Indexes with a sequence of Indexes from (0, 0) --> (row, column) excluding the latter
     * @param rows the row index of the last index, excluding
     * @param column the column index of the last index, excluding
     * @return a Stream with Indexes from (0, 0) --> (row, column)
     */
    public static Stream<Indexes> stream(int rows, int column) {
        return stream(ORIGIN, new Indexes(rows, column));
    }

    /**
     * Return the value of given matrix according to this Indexes
     * @param matrix the given matrix
     * @return the value at a given index.
     * @param <S> the type of the Matrix
     * @throws IllegalArgumentException if matrix is a null object
     */
    public <S> S value(S[][] matrix) {
        Objects.requireNonNull(matrix);

        if (matrix.length - 1 < row() || matrix[0].length - 1 < column())
            throw new ArrayIndexOutOfBoundsException("Indexes are out of bounds");

        return matrix[row()][column()];
    }

    public <S> S value(Matrix<Indexes, S> matrix) {
        Objects.requireNonNull(matrix);

        return matrix.value(this);
    }

    /**
     * Return if these Indexes are diagonal.
     * @return true if Indexes are diagonal and false otherwise.
     */
    public boolean areDiagonal() {
        return row() == column();
    }


    @Override
    public int compareTo(Indexes indexes) {
        Objects.requireNonNull(indexes);
        return byRows.compare(this, indexes);
    }

    @Override
    public String toString() {
        return "(" + row() + ", " + column() + ")";
    }

}
