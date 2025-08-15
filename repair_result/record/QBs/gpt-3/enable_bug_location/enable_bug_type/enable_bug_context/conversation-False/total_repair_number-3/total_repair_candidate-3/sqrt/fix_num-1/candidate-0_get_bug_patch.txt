def sqrt(x, epsilon):
    if x < 0:
        raise ValueError("Cannot compute square root of a negative number.")
    if x == 0:
        return 0
    approx = x / 2
    while abs(x - approx * approx) > epsilon:
        approx = 0.5 * (approx + x / approx)
    return approx
