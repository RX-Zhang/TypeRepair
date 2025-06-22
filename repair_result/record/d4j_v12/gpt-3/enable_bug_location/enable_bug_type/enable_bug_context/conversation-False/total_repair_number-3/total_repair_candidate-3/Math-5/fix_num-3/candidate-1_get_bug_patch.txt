public Complex reciprocal() {
    if (isNaN) {
        return NaN;
    }

    if (real == 0.0 && imaginary == 0.0) {
        return NaN; // This should return NaN as the reciprocal of zero is undefined
    }

    if (isInfinite) {
        return ZERO; // This should return ZERO as the reciprocal of infinity is zero
    }

    if (FastMath.abs(real) < FastMath.abs(imaginary)) {
        double q = real / imaginary;
        double scale = 1. / (real * q + imaginary);
        return createComplex(-scale * q, scale); // Corrected the sign for the imaginary part
    } else {
        double q = imaginary / real;
        double scale = 1. / (imaginary * q + real);
        return createComplex(-scale * q, scale); // Corrected the sign for the imaginary part
    }
}
