public static double[] bracket(UnivariateRealFunction function,
        double initial, double lowerBound, double upperBound, 
        int maximumIterations) throws ConvergenceException, 
        FunctionEvaluationException {
    
    if (function == null) {
        throw MathRuntimeException.createIllegalArgumentException("function is null");
    }
    if (maximumIterations <= 0)  {
        throw MathRuntimeException.createIllegalArgumentException(
              "bad value for maximum iterations number: {0}", maximumIterations);
    }
    if (initial < lowerBound || initial > upperBound || lowerBound >= upperBound) {
        throw MathRuntimeException.createIllegalArgumentException(
              "invalid bracketing parameters:  lower bound={0},  initial={1}, upper bound={2}",
              lowerBound, initial, upperBound);
    }
    double a = initial;
    double b = initial;
    double fa = function.value(a);
    double fb = function.value(b);
    int numIterations = 0;

    // Expand interval until a root is bracketed or bounds are reached or max iterations exceeded
    while (fa * fb > 0.0 && numIterations < maximumIterations) {
        if (a > lowerBound) {
            double nextA = Math.max(a - 1.0, lowerBound);
            fa = function.value(nextA);
            a = nextA;
        }
        if (fb * fa > 0.0 && b < upperBound) {  // Only expand b if no root bracket yet
            double nextB = Math.min(b + 1.0, upperBound);
            fb = function.value(nextB);
            b = nextB;
        }
        numIterations++;
        // If cannot move a or b further, break to avoid infinite loop
        if ((a == lowerBound && fa * fb > 0.0) && (b == upperBound && fa * fb > 0.0)) {
            break;
        }
    }

    if (fa * fb >= 0.0) {
        throw new ConvergenceException(
                  "number of iterations={0}, maximum iterations={1}, " +
                  "initial={2}, lower bound={3}, upper bound={4}, final a value={5}, " +
                  "final b value={6}, f(a)={7}, f(b)={8}",
                  numIterations, maximumIterations, initial,
                  lowerBound, upperBound, a, b, fa, fb);
    }

    return new double[]{a, b};
}
