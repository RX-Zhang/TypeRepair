private double calculateAsymptoticPValue(final double Umin,
                                         final int n1,
                                         final int n2)
    throws ConvergenceException, MaxCountExceededException {

    final int n1n2prod = n1 * n2;

    // http://en.wikipedia.org/wiki/Mann%E2%80%93Whitney_U#Normal_approximation
    final double EU = n1n2prod / 2.0;
    final double VarU = n1n2prod * (n1 + n2 + 1) / 12.0;

    final double z = (Umin - EU) / FastMath.sqrt(VarU);

    final NormalDistribution standardNormal = new NormalDistribution(0, 1);

    // Use two-tailed p-value correctly:
    // cumulativeProbability(z) gives P(Z <= z),
    // so 2 * cumulativeProbability(z) is incorrect.
    // The correct two-tailed p-value is 2 * min(P(Z <= z), P(Z >= z))
    // = 2 * min(cumulativeProbability(z), 1 - cumulativeProbability(z))
    double p = standardNormal.cumulativeProbability(z);
    if (p > 0.5) {
        p = 1 - p;
    }
    return 2 * p;
}
