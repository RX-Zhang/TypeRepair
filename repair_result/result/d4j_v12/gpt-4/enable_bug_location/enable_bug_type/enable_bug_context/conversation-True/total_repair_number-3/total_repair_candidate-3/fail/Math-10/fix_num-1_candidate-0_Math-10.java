public void atan2(final double[] y, final int yOffset,
                  final double[] x, final int xOffset,
                  final double[] result, final int resultOffset) {

    // compute r = sqrt(x^2+y^2)
    double[] tmp1 = new double[getSize()];
    multiply(x, xOffset, x, xOffset, tmp1, 0);      // x^2
    double[] tmp2 = new double[getSize()];
    multiply(y, yOffset, y, yOffset, tmp2, 0);      // y^2
    add(tmp1, 0, tmp2, 0, tmp2, 0);                 // x^2 + y^2
    rootN(tmp2, 0, 2, tmp1, 0);                     // r = sqrt(x^2 + y^2)

    // special cases fix: Handle cases where r + x or r - x might be zero
    // to avoid division by zero and preserve sign information correctly

    if (x[xOffset] >= 0) {

        // compute atan2(y, x) = 2 * atan(y / (r + x))
        add(tmp1, 0, x, xOffset, tmp2, 0);          // r + x
        // if r + x == 0, y / (r + x) should be y / +0 or -0, keep sign
        if (tmp2[0] == 0.0) {
            // directly set result to handle properly the sign of y and x
            result[resultOffset] = 2.0 * FastMath.atan2(y[yOffset], x[xOffset]);
            for (int i = 1; i < getSize(); ++i) {
                result[resultOffset + i] = 0.0; // derivatives zero in this edge case
            }
            return;
        }

        divide(y, yOffset, tmp2, 0, tmp1, 0);       // y / (r + x)
        atan(tmp1, 0, tmp2, 0);                      // atan(y / (r + x))
        for (int i = 0; i < tmp2.length; ++i) {
            result[resultOffset + i] = 2 * tmp2[i]; // 2 * atan(y / (r + x))
        }

    } else {

        // compute atan2(y, x) = +/- pi - 2 * atan(y / (r - x))
        subtract(tmp1, 0, x, xOffset, tmp2, 0);     // r - x
        // if r - x == 0, y / (r - x) should be y / +0 or -0, keep sign
        if (tmp2[0] == 0.0) {
            // directly set result to handle properly the sign of y and x
            double val = 0.0;
            if (y[yOffset] == 0.0) {
                val = (FastMath.copySign(1.0, y[yOffset]) < 0) ? -FastMath.PI : FastMath.PI;
            } else {
                val = FastMath.atan2(y[yOffset], x[xOffset]);
            }
            result[resultOffset] = val;
            for (int i = 1; i < getSize(); ++i) {
                result[resultOffset + i] = 0.0; // derivatives zero in this edge case
            }
            return;
        }

        divide(y, yOffset, tmp2, 0, tmp1, 0);       // y / (r - x)
        atan(tmp1, 0, tmp2, 0);                      // atan(y / (r - x))
        result[resultOffset] =
                ((tmp2[0] <= 0) ? -FastMath.PI : FastMath.PI) - 2 * tmp2[0]; // +/-pi - 2 * atan(y / (r - x))
        for (int i = 1; i < tmp2.length; ++i) {
            result[resultOffset + i] = -2 * tmp2[i]; // +/-pi - 2 * atan(y / (r - x))
        }

    }

}
