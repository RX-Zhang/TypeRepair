public Complex reciprocal() {
    if (isNaN) {
        return NaN;
    }

    if (real == 0.0 && imaginary == 0.0) {
        return NaN; // This should return NaN, but we need to ensure we handle zero correctly.
    }

    if (isInfinite) {
        return ZERO; // This should return ZERO, but we need to ensure we handle infinity correctly.
    }

    if (real == 0.0) {
        return createComplex(0.0, -1.0 / imaginary); // Handle the case where real part is zero.
    }

    if (imaginary == 0.0) {
        return createComplex(1.0 / real, 0.0); // Handle the case where imaginary part is zero.
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
