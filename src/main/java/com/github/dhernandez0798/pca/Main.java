package com.github.dhernandez0798.pca;

import smile.netlib.NLMatrix;

public class Main {

    public static void main(String[] args) {
        double[][] test = new double[][]{
                {0.5, 0.94, 0.76},
                {0.06, 0.5, 0.17},
                {0.24, 0.83, 0.5}
        };

        PCA pca = new PCA(test, 2, true);
        System.out.println(new NLMatrix(pca.getPrincipalComponents()));
    }
}
