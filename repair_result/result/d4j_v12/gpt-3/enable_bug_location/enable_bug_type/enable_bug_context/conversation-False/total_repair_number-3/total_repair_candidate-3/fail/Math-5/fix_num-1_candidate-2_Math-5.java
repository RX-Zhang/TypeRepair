public Complex reciprocal() {
    if (isNaN) {
        return NaN;
    }

    if (real == 0.0 && imaginary == 0.0) {
        return NaN; // Return NaN for zero input
    }

    if (isInfinite) {
        return ZERO; // Return zero for infinite input
    }

    if (FastMath.abs(real) < FastMath.abs(imaginary)) {
        double q = real / imaginary;
        double scale = 1. / (real * q + imaginary);
        return createComplex(-scale * q, scale); // Corrected the order of parameters
    } else {
        double q = imaginary / real;
        double scale = 1. / (imaginary * q + real);
        return createComplex(-scale * q, scale); // Corrected the order of parameters
    }
}
