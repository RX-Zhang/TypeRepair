private Fraction(double value, double epsilon, int maxDenominator, int maxIterations)
    throws FractionConversionException
{
    long overflow = Integer.MAX_VALUE;
    double r0 = value;
    long a0 = (long)FastMath.floor(r0);
    if (a0 > overflow || a0 < Integer.MIN_VALUE) {
        throw new FractionConversionException(value, a0, 1L);
    }

    // check for (almost) integer arguments, which should not go
    // to iterations.
    if (FastMath.abs(a0 - value) < epsilon) {
        if (a0 > Integer.MAX_VALUE || a0 < Integer.MIN_VALUE) {
            throw new FractionConversionException(value, a0, 1L);
        }
        this.numerator = (int) a0;
        this.denominator = 1;
        return;
    }

    long p0 = 1;
    long q0 = 0;
    long p1 = a0;
    long q1 = 1;

    long p2 = 0;
    long q2 = 1;

    int n = 0;
    boolean stop = false;
    do {
        ++n;
        double r1 = 1.0 / (r0 - a0);
        if (Double.isInfinite(r1) || Double.isNaN(r1)) {
            // Possibly division by zero or invalid, stop iterations
            break;
        }
        long a1 = (long)FastMath.floor(r1);

        // Check for possible overflow before computing p2 and q2
        // Use longs carefully to avoid overflow in multiplication
        if (a1 > 0 && (p1 > overflow / a1 || q1 > overflow / a1)) {
            throw new FractionConversionException(value, p2, q2);
        }

        p2 = (a1 * p1) + p0;
        q2 = (a1 * q1) + q0;

        if (p2 > overflow || p2 < Integer.MIN_VALUE || q2 > overflow || q2 < 1) {
            throw new FractionConversionException(value, p2, q2);
        }

        double convergent = (double)p2 / (double)q2;
        if (n < maxIterations && FastMath.abs(convergent - value) > epsilon && q2 <= maxDenominator) {
            p0 = p1;
            p1 = p2;
            q0 = q1;
            q1 = q2;
            a0 = a1;
            r0 = r1;
        } else {
            stop = true;
        }
    } while (!stop);

    if (n >= maxIterations) {
        throw new FractionConversionException(value, maxIterations);
    }

    // Pick the best approximation that fits in int range
    if (q2 <= maxDenominator && p2 <= Integer.MAX_VALUE && p2 >= Integer.MIN_VALUE && q2 > 0) {
        this.numerator = (int) p2;
        this.denominator = (int) q2;
    } else if (q1 <= maxDenominator && p1 <= Integer.MAX_VALUE && p1 >= Integer.MIN_VALUE && q1 > 0) {
        this.numerator = (int) p1;
        this.denominator = (int) q1;
    } else {
        throw new FractionConversionException(value, p1, q1);
    }
}
