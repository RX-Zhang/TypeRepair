public Complex reciprocal() {
    if (isNaN) {
        return NaN;
    }

    if (real == 0.0 && imaginary == 0.0) {
        return NaN; // It should return NaN to indicate undefined behavior
    }

    if (isInfinite) {
        return ZERO; // Return ZERO when the number is infinite
    }

    // Adjusting the logic to handle the reciprocal calculation correctly
    if (real == 0.0) {
        return createComplex(0.0, -1.0 / imaginary); // Handle purely imaginary case
    }

    if (imaginary == 0.0) {
        return createComplex(1.0 / real, 0.0); // Handle purely real case
    }

    if (FastMath.abs(real) < FastMath.abs(imaginary)) {
        double q = real / imaginary;
        double scale = 1. / (real * q + imaginary);
        return createComplex(scale * q, -scale);
    } else {
        double q = imaginary / real;
        double scale = 1. / (imaginary * q + real);
        return createComplex(scale, -scale * q);
    }
}
