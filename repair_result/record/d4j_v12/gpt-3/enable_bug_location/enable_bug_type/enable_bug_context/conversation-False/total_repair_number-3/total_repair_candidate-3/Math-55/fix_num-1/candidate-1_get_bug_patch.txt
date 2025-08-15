public static Vector3D crossProduct(final Vector3D v1, final Vector3D v2) {

    // rescale both vectors without losing precision,
    // to ensure their norm are the same order of magnitude
    double v1Norm = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
    double v2Norm = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);

    if (v1Norm == 0 || v2Norm == 0) {
        // At least one vector is zero
        return new Vector3D(0, 0, 0);
    }

    // scale vectors so their norms are comparable
    Vector3D v1Scaled = v1.scalarMultiply(1.0 / v1Norm);
    Vector3D v2Scaled = v2.scalarMultiply(1.0 / v2Norm);

    // compute rho as an 8 bits approximation of v1.v2 / v2.v2 (v2.v2 = 1 here)
    double dot = Vector3D.dotProduct(v1Scaled, v2Scaled);
    // approximate dot to 8 bits
    double rho = Math.round(dot * 256.0) / 256.0;

    // compute v3 = v1Scaled - rho * v2Scaled
    Vector3D v3 = v1Scaled.subtract(v2Scaled.scalarMultiply(rho));

    // cross product of v3 and v2Scaled
    Vector3D cross = new Vector3D(
        v3.y * v2Scaled.z - v3.z * v2Scaled.y,
        v3.z * v2Scaled.x - v3.x * v2Scaled.z,
        v3.x * v2Scaled.y - v3.y * v2Scaled.x
    );

    // scale result back by ||v1|| * ||v2||
    return cross.scalarMultiply(v1Norm * v2Norm);
}
