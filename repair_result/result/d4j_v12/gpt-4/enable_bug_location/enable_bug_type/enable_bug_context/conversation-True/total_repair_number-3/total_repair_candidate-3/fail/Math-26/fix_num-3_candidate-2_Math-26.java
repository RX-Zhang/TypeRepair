private Fraction(double value, double epsilon, int maxDenominator, int maxIterations)
    throws FractionConversionException
{
    long overflow = Integer.MAX_VALUE;
    double r0 = value;
    long a0 = (long)FastMath.floor(r0);
    if (a0 > overflow || a0 < -overflow) {
        throw new FractionConversionException(value, a0, 1L);
    }

    // check for (almost) integer arguments, which should not go
    // to iterations.
    if (FastMath.abs(a0 - value) < epsilon) {
        // Use getReducedFraction to handle potential overflow and properly reduce
        Fraction f = Fraction.getReducedFraction((int)a0, 1);
        this.numerator = f.getNumerator();
        this.denominator = f.getDenominator();
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
        long a1 = (long)FastMath.floor(r1);
        p2 = (a1 * p1) + p0;
        q2 = (a1 * q1) + q0;

        if ((p2 > overflow) || (p2 < -overflow) || (q2 > overflow) || (q2 < -overflow)) {
            throw new FractionConversionException(value, p2, q2);
        }

        double convergent = (double)p2 / (double)q2;
        if (n < maxIterations && FastMath.abs(convergent - value) > epsilon && q2 < maxDenominator) {
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

    // Use getReducedFraction to ensure numerator and denominator fit and are reduced
    try {
        Fraction f;
        if (q2 < maxDenominator) {
            f = Fraction.getReducedFraction((int)p2, (int)q2);
        } else {
            f = Fraction.getReducedFraction((int)p1, (int)q1);
        }
        this.numerator = f.getNumerator();
        this.denominator = f.getDenominator();
    } catch (MathArithmeticException mae) {
        throw new FractionConversionException(value, p2, q2);
    }
}
