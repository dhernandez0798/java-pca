package com.github.dhernandez0798.pca;

import java.util.Arrays;

public class MatrixUtils {
    private MatrixUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Scales a matrix.
     *
     * @param matrix    the matrix that is desired to be scaled
     * @param transpose if the matrix should be transposed. Usually it's true
     * @return the scaled matrix
     */
    public static double[][] normalize(double[][] matrix, boolean transpose) {
        if (transpose) {
            matrix = transpose(matrix);
        }
        // Create a new copy of the matrix
        double[][] normalizedMatrix = new double[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            normalizedMatrix[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }

        for (int i = 0; i < matrix.length; i++) {
            double mean = MathUtils.calculateMean(matrix[i]);
            double standardDeviation = MathUtils.calculateStandardDeviation(matrix[i]);

            for (int j = 0; j < matrix[i].length; j++) {
                normalizedMatrix[i][j] = (matrix[i][j] - mean) / standardDeviation;
            }
        }
        return transpose ? transpose(normalizedMatrix) : normalizedMatrix;
    }

    /**
     * Computes the covariance matrix over a given matrix.
     *
     * @param matrix
     * @param biasCorrected true if sample, false if population. Usually, it's true. (x / (n -1))
     * @return the covariance matrix of the matrix
     */
    public static double[][] computeCovariance(double[][] matrix, boolean biasCorrected) {
        int dimension = matrix.length;
        double[][] covarianceMatrix = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                double cov = MathUtils.calculateCovariance(matrix[i], matrix[j], biasCorrected);
                covarianceMatrix[i][j] = cov;
                covarianceMatrix[j][i] = cov;
            }
        }
        return covarianceMatrix;
    }

    /**
     * Truncate the matrix on a specified number of columns.
     *
     * @param matrix
     * @param columns the final number of columns
     * @return the submatrix
     */
    public static double[][] submatrix(double[][] matrix, int columns) {
        double[][] transposedMatrix = transpose(matrix);
        if (columns > transposedMatrix.length) {
            throw new IllegalArgumentException("cannot create a submatrix bigger than the original");
        }

        double[][] newMatrix = new double[columns][transposedMatrix[0].length];
        for (int i = 0; i < columns; i++) {
            newMatrix[i] = transposedMatrix[i].clone();
        }
        return transpose(newMatrix);
    }

    /**
     * Transposes a matrix
     *
     * @param matrix
     * @return the transpose of a matrix
     */
    public static double[][] transpose(double[][] matrix) {
        double[][] newArray = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                double temp = matrix[j][i];
                newArray[i][j] = temp;
            }
        }
        return newArray;
    }

    /**
     * Multiplies two matrices
     *
     * @param matrix1
     * @param matrix2
     * @return the result of the multiplication
     */
    public static double[][] multiply(double[][] matrix1, double[][] matrix2) {
        double[][] result = new double[matrix1.length][matrix2[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = multiplyMatricesCell(matrix1, matrix2, i, j);
            }
        }
        return result;
    }

    private static double multiplyMatricesCell(double[][] matrix1, double[][] matrix2, int row, int col) {
        double result = 0;
        for (int i = 0; i < matrix2.length; i++) {
            result += matrix1[row][i] * matrix2[i][col];
        }
        return result;
    }
}
