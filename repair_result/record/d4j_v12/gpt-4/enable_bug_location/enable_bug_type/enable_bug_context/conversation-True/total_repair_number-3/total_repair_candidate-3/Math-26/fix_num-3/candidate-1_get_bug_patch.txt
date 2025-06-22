private Fraction(double value, double epsilon, int maxDenominator, int maxIterations)
    throws FractionConversionException
{
    final long overflow = Integer.MAX_VALUE;
    double r0 = value;
    long a0 = (long) FastMath.floor(r0);

    // Check for integer overflow on a0 (could be negative, so check abs)
    if (a0 > overflow || a0 < Integer.MIN_VALUE) {
        throw new FractionConversionException(value, a0, 1L);
    }

    // Check for (almost) integer arguments, which should not go to iterations.
    if (FastMath.abs(a0 - value) < epsilon) {
        this.numerator = (int) a0;
        this.denominator = 1;
        return;
    }

    long p0 = 1;
    long q0 = 0;
    long p1 = a0;
    long q1 = 1;

    long p2;
    long q2;

    int n = 0;
    boolean stop = false;
    do {
        ++n;
        double denom = r0 - a0;
        if (denom == 0.0) { // Prevent division by zero
            break;
        }
        double r1 = 1.0 / denom;
        long a1 = (long) FastMath.floor(r1);

        if (a1 > overflow || a1 < Integer.MIN_VALUE) {
            throw new FractionConversionException(value, a1, 1L);
        }

        p2 = (a1 * p1) + p0;
        q2 = (a1 * q1) + q0;

        // Check for overflow on p2 and q2 (handle negative cases as well)
        if (p2 > overflow || p2 < Integer.MIN_VALUE ||
            q2 > overflow || q2 < Integer.MIN_VALUE) {
            throw new FractionConversionException(value, p2, q2);
        }

        double convergent = (double) p2 / (double) q2;

        // Stop condition: either max iterations reached, close enough to value or denominator too large
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

    // Use getReducedFraction to reduce and normalize results safely
    if (q2 <= maxDenominator) {
        Fraction reduced = Fraction.getReducedFraction((int) p2, (int) q2);
        this.numerator = reduced.getNumerator();
        this.denominator = reduced.getDenominator();
    } else {
        Fraction reduced = Fraction.getReducedFraction((int) p1, (int) q1);
        this.numerator = reduced.getNumerator();
        this.denominator = reduced.getDenominator();
    }
}
