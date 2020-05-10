package com.github.dhernandez0798.pca;

import smile.math.matrix.EVD;
import smile.netlib.NLMatrix;

public class PCA {
    private final int componentsNumber;
    private final boolean biasCorrected;
    private final double[][] matrix;

    private double[][] normalizedMatrix;
    private double[][] covarianceMatrix;

    private double[] eigenvalues;
    private double[][] eigenvectorMatrix;
    private double[][] computedMatrix;

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

    public boolean isBiasCorrected() {
        return biasCorrected;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[][] getNormalizedMatrix() {
        return normalizedMatrix;
    }

    public double[][] getCovarianceMatrix() {
        return covarianceMatrix;
    }

    public double[] getEigenvalues() {
        return eigenvalues;
    }

    public double[][] getEigenvectorMatrix() {
        return eigenvectorMatrix;
    }

    public double[][] getPrincipalComponents() {
        return computedMatrix;
    }
}
