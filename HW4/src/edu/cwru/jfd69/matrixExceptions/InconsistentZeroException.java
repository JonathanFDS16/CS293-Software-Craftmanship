package edu.cwru.jfd69.matrixExceptions;

import edu.cwru.jfd69.matrix.Matrix;

public class InconsistentZeroException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 198763598712634L;

    private final String thisZero;

    private final String otherZero;

    public String getThisZero() {
        return thisZero;
    }

    public String getOtherZero() {
        return otherZero;
    }

    public InconsistentZeroException(String thisZero, String otherZero) {
        super("This Zero --> " + thisZero + "  Other Zero --> " + otherZero);
        this.thisZero = thisZero;
        this.otherZero = otherZero;
    }

    public static <I, T> T requireMatching(Matrix<I, T> thisMatrix, Matrix<I, T> otherMatrix) {
        if (!thisMatrix.zero().equals(otherMatrix.zero()))
            throw new IllegalArgumentException(new InconsistentZeroException(thisMatrix.zero().toString(),
                                                                            otherMatrix.zero().toString()));
        return thisMatrix.zero();
    }

}
