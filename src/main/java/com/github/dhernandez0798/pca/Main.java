package com.github.dhernandez0798.pca;

public class Main {

    public static void main(String[] args) {
        double[][] test = new double[][]{
                {0.5, 0.94, 0.76},
                {0.06, 0.5, 0.17},
                {0.24, 0.83, 0.5}
        };

        // Usually, biasCorrected should be true.
        PCA pca = new PCA(test, 2, true);

        // The computed matrix, aka the principal components matrix
        double[][] pcaResult = pca.getPrincipalComponents();

        // Prints a beautiful matrix.
        System.out.println(pca.toString());
    }
}
