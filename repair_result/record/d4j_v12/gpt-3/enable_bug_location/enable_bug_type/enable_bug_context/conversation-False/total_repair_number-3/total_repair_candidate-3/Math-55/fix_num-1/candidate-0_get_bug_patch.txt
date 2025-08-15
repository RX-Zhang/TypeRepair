public static Vector3D crossProduct(final Vector3D v1, final Vector3D v2) {

    // rescale both vectors without losing precision,
    // to ensure their norm are the same order of magnitude

    // we reduce cancellation errors by preconditioning,
    // we replace v1 by v3 = v1 - rho v2 with rho chosen in order to compute
    // v3 without loss of precision. See Kahan lecture
    // "Computing Cross-Products and Rotations in 2- and 3-Dimensional Euclidean Spaces"
    // available at http://www.cs.berkeley.edu/~wkahan/MathH110/Cross.pdf

    // compute rho as an 8 bits approximation of v1.v2 / v2.v2
    double v2NormSq = dotProduct(v2, v2);
    if (v2NormSq == 0) {
        // if v2 is zero vector, cross product is zero vector
        return new Vector3D(0, 0, 0);
    }
    double rho = dotProduct(v1, v2) / v2NormSq;

    // approximate rho by rounding to nearest 1/256 (8 bits approximation)
    double rhoRounded = Math.round(rho * 256.0) / 256.0;

    // v3 = v1 - rhoRounded * v2
    Vector3D v3 = v1.subtract(v2.scalarMultiply(rhoRounded));

    // compute cross product from v3 and v2 instead of v1 and v2
    return new Vector3D(
        v3.y * v2.z - v3.z * v2.y,
        v3.z * v2.x - v3.x * v2.z,
        v3.x * v2.y - v3.y * v2.x
    );

}
