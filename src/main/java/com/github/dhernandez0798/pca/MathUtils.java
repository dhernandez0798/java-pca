package com.github.dhernandez0798.pca;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
    private MathUtils() {
        throw new UnsupportedOperationException();
    }

    public static double calculateStandardDeviation(double[] array) {
        double deviation = 0;
        double mean = calculateMean(array);

        for (int i = 0; i < array.length; i++) {
            deviation += Math.pow(array[i] - mean, 2);
        }
        return Math.sqrt(deviation / array.length);
    }

    public static double calculateMean(double[] array) {
        double result = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        double length = array.length;
        return round(result / length, 6);
    }

    public static double calculateVariance(double[] array) {
        double variance = 0;
        double mean = calculateMean(array);
        for (int i = 0; i < array.length; i++) {
            variance += Math.pow(array[i] - mean, 2);
        }
        return variance / array.length;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // From http://commons.apache.org/proper/commons-math/javadocs/api-2.2/src-html/org/apache/commons/math/stat/correlation/Covariance.html#line.220
    public static double calculateCovariance(double[] xArray, double[] yArray, boolean biasCorrected) {
        double result = 0;
        int length = xArray.length;
        // check length == yArray.length or throw not square matrix exception
        if (length < 2) {
            // check if length is < 2, throw insufficient dimensions exceptions too
        }
        else {
            double xMean = calculateMean(xArray);
            double yMean = calculateMean(yArray);
            for (int i = 0; i < length; i++) {
                double xDev = xArray[i] - xMean;
                double yDev = yArray[i] - yMean;
                result += (xDev * yDev - result) / (i + 1);
                }
            }
        return biasCorrected ? result * ((double) length / (double)(length - 1)) : result;
    }
}
