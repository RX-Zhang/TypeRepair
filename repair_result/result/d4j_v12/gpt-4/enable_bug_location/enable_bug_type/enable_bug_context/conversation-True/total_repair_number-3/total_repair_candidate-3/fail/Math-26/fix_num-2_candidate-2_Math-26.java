private Fraction(double value, double epsilon, int maxDenominator, int maxIterations)
    throws FractionConversionException
{
    // Validate input parameters
    if (maxDenominator <= 0) {
        throw new IllegalArgumentException("maxDenominator must be positive");
    }
    if (epsilon <= 0.0) {
        throw new IllegalArgumentException("epsilon must be positive");
    }
    if (maxIterations <= 0) {
        throw new IllegalArgumentException("maxIterations must be positive");
    }

    final long max = Integer.MAX_VALUE;
    double r0 = value;
    long a0 = (long) FastMath.floor(r0);

    if (a0 > max || a0 < Integer.MIN_VALUE) {
        throw new FractionConversionException(value, a0, 1L);
    }

    // Handle (almost) integer values
    if (FastMath.abs(a0 - value) < epsilon) {
        this.numerator = (int) a0;
        this.denominator = 1;
        return;
    }

    long p0 = 1, q0 = 0;
    long p1 = a0, q1 = 1;
    long p2 = 0, q2 = 1;

    int n = 0;
    boolean stop = false;
    do {
        n++;
        double r1 = 1.0 / (r0 - a0);

        if (Double.isInfinite(r1)) {
            // Prevent division by zero in next iteration
            break;
        }

        long a1 = (long) FastMath.floor(r1);

        // Check for possible overflow before computing next convergents
        if (a1 > (max - p0) / p1 || a1 > (max - q0) / q1) {
            throw new FractionConversionException(value, p1, q1);
        }

        p2 = a1 * p1 + p0;
        q2 = a1 * q1 + q0;

        if (p2 > max || q2 > max || p2 < Integer.MIN_VALUE || q2 < 1) {
            throw new FractionConversionException(value, p2, q2);
        }

        double convergent = (double) p2 / (double) q2;

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

    if (q2 <= maxDenominator) {
        Fraction fraction = Fraction.getReducedFraction((int) p2, (int) q2);
        this.numerator = fraction.getNumerator();
        this.denominator = fraction.getDenominator();
    } else {
        Fraction fraction = Fraction.getReducedFraction((int) p1, (int) q1);
        this.numerator = fraction.getNumerator();
        this.denominator = fraction.getDenominator();
    }
}
