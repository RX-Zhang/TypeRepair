public double evaluate(final double[] values, final double[] weights,
                       final double mean, final int begin, final int length) {

    double var = Double.NaN;

    // Validate input arrays
    if (values == null || weights == null) {
        throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY);
    }
    if (begin < 0 || length < 1 || begin + length > values.length || begin + length > weights.length) {
        throw new ArrayIndexOutOfBoundsException("Invalid begin or length parameters.");
    }

    if (test(values, weights, begin, length)) {
        if (length == 1) {
            var = 0.0;
        } else if (length > 1) {
            double accum = 0.0;
            double dev = 0.0;
            double accum2 = 0.0;
            for (int i = begin; i < begin + length; i++) {
                dev = values[i] - mean;
                accum += weights[i] * (dev * dev);
                accum2 += weights[i] * dev;
            }

            double sumWts = 0;
            for (int i = begin; i < begin + length; i++) {
                sumWts += weights[i];
            }

            if (sumWts > 0) { // Ensure sumWts is greater than 0 to avoid division by zero
                if (isBiasCorrected) {
                    var = (accum - (accum2 * accum2 / sumWts)) / (sumWts - 1.0);
                } else {
                    var = (accum - (accum2 * accum2 / sumWts)) / sumWts;
                }
            } else {
                var = Double.NaN; // Handle case where sum of weights is zero
            }
        }
    }
    return var;
}
