package com.github.dhernandez0798.pca;

import smile.math.matrix.EVD;
import smile.netlib.NLMatrix;

/**
 * Class that given a matrix, a components number and if the results
 * are desired to be bias corrected or not, computes the Principal
 * Analysis Technique by using the covariance matrix and Eigenvalues
 * decomposition method.
 */
public class PCA {
    private final int componentsNumber;
    private final boolean biasCorrected;
    private final double[][] matrix;

    private double[][] normalizedMatrix;
    private double[][] covarianceMatrix;

    private double[] eigenvalues;
    private double[][] eigenvectorMatrix;
    private double[][] computedMatrix;

    /**
     * Constructor for the class. Set the given values and computes the
     * given matrix.
     *
     * @param matrix           the matrix that want to be reduced dimensionally
     * @param componentsNumber the final number of componentes (2/3 recommended)
     * @param biasCorrected    if the results must be bias corrected or not ( / n or / (1 - n))
     *                         If the matrix is a sample, then this parameter should be true.
     *                         If it's the population of our data, then should be false.
     */
    public PCA(double[][] matrix, int componentsNumber, boolean biasCorrected) {
        this.componentsNumber = componentsNumber;
        this.matrix = matrix;
        this.biasCorrected = biasCorrected;
        compute();
    }

    private void compute() {
        this.normalizedMatrix = MatrixUtils.normalize(matrix, true);
        this.covarianceMatrix = MatrixUtils.computeCovariance(
                MatrixUtils.transpose(this.normalizedMatrix), biasCorrected);


        EVD eigen = new NLMatrix(covarianceMatrix).eigen();
        this.eigenvalues = eigen.getEigenValues();
        this.eigenvectorMatrix = MatrixUtils.submatrix(eigen.getEigenVectors().toArray(), componentsNumber);

        this.computedMatrix = MatrixUtils.multiply(
                this.normalizedMatrix,
                this.eigenvectorMatrix
        );

    }

    public int getComponentsNumber() {
        return componentsNumber;
    }


    /**
     * @return true if the data is a sample, false if not.
     */
    public boolean isBiasCorrected() {
        return biasCorrected;
    }

    /**
     * @return the original, unscaled matrix
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * @return the scaled matrix
     */
    public double[][] getNormalizedMatrix() {
        return normalizedMatrix;
    }

    /**
     * @return the covariance matrix over the scaled matrix
     */
    public double[][] getCovarianceMatrix() {
        return covarianceMatrix;
    }

    /**
     * @return eigenvalues over the covariance matrix. Ordered from bigger to smaller.
     */
    public double[] getEigenvalues() {
        return eigenvalues;
    }

    /**
     * @return eigenvector matrix ordered by the eigenvalues.
     */
    public double[][] getEigenvectorMatrix() {
        return eigenvectorMatrix;
    }

    /**
     * @return the computed PCA over the original matrix
     */
    public double[][] getPrincipalComponents() {
        return computedMatrix;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(computedMatrix.length + " x " + computedMatrix[0].length);
        for (int i = 0; i < computedMatrix.length; i++) {
            result.append("\n");
            for (int j = 0; j < computedMatrix[i].length; j++) {
                result.append(computedMatrix[i][j]).append("\t");
            }
        }
        return result.toString();
    }
}
