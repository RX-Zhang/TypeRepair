public double evaluate(double x, double epsilon, int maxIterations) {
    final double small = 1e-50;
    double hPrev = getA(0, x);

    // use the value of small as epsilon criteria for zero checks
    if (Precision.equals(hPrev, 0.0, small)) {
        hPrev = small;
    }

    int n = 1;
    double dPrev = 0.0;
    double p0 = 1.0;
    double q1 = 1.0;
    double cPrev = hPrev;
    double hN = hPrev;

    while (n < maxIterations) {
        final double a = getA(n, x);
        final double b = getB(n, x);

        double cN = b + a / cPrev;
        double q2 = b + a / q1;

        if (Double.isInfinite(cN) || Double.isInfinite(q2)) {
            double scaleFactor = 1d;
            double lastScaleFactor = 1d;
            final int maxPower = 5;
            final double scale = FastMath.max(Math.abs(a), Math.abs(b));
            if (scale <= 0) {  // Can't scale
                throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, x);
            }
            for (int i = 0; i < maxPower; i++) {
                lastScaleFactor = scaleFactor;
                scaleFactor *= scale;
                if (Math.abs(a) > Math.abs(b)) {
                    cN = b / lastScaleFactor + (a / scaleFactor / cPrev);
                    q2 = b / lastScaleFactor + (a / scaleFactor / q1);
                } else if (b != 0) {
                    cN = b / scaleFactor + (a / lastScaleFactor / cPrev);
                    q2 = b / scaleFactor + (a / lastScaleFactor / q1);
                }
                if (!(Double.isInfinite(cN) || Double.isInfinite(q2))) {
                    break;
                }
            }
        }

        if (cN == 0.0) {
            cN = small;
        }
        if (q2 == 0.0) {
            q2 = small;
        }

        final double deltaN = cN / q2;
        hN = hPrev * deltaN;

        if (Double.isInfinite(hN)) {
            throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE,
                                           x);
        }
        if (Double.isNaN(hN)) {
            throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_NAN_DIVERGENCE,
                                           x);
        }

        if (FastMath.abs(deltaN - 1.0) < epsilon) {
            break;
        }

        dPrev = q1;
        cPrev = cN;
        p0 = hPrev;
        hPrev = hN;
        q1 = q2;
        n++;
    }

    if (n >= maxIterations) {
        throw new MaxCountExceededException(LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION,
                                            maxIterations, x);
    }

    return hN;
}
