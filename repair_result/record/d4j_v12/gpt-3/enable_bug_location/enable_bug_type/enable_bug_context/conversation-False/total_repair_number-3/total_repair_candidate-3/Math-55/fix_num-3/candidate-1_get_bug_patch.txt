public static Vector3D crossProduct(final Vector3D v1, final Vector3D v2) {

    // rescale both vectors without losing precision,
    // to ensure their norm are the same order of magnitude

    // we reduce cancellation errors by preconditioning,
    // we replace v1 by v3 = v1 - rho v2 with rho chosen in order to compute
    // v3 without loss of precision. See Kahan lecture
    // "Computing Cross-Products and Rotations in 2- and 3-Dimensional Euclidean Spaces"
    // available at http://www.cs.berkeley.edu/~wkahan/MathH110/Cross.pdf

    // compute rho as an 8 bits approximation of v1.v2 / v2.v2
    final double dot = dotProduct(v1, v2);
    final double v2NormSq = dotProduct(v2, v2);

    // 8 bits approximation of dot/v2NormSq by keeping only first 8 bits of mantissa
    long bits = Double.doubleToLongBits(dot / v2NormSq);
    // mask to keep only exponent and first 8 bits of mantissa (not sign bit)
    bits &= 0xfff0000000000000L; 
    final double rho = Double.longBitsToDouble(bits);

    // v3 = v1 - rho * v2
    final Vector3D v3 = new Vector3D(
        v1.x - rho * v2.x,
        v1.y - rho * v2.y,
        v1.z - rho * v2.z
    );

    // compute cross product from v3 and v2 instead of v1 and v2
    return new Vector3D(
        v3.y * v2.z - v3.z * v2.y,
        v3.z * v2.x - v3.x * v2.z,
        v3.x * v2.y - v3.y * v2.x
    );

}
