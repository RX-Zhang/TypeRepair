def sqrt(x, epsilon):
    if x < 0:
        raise ValueError("Cannot compute square root of negative number")
    approx = x / 2
    while True:
        new_approx = 0.5 * (approx + x / approx)
        if abs(new_approx - approx) < epsilon:
            break
        approx = new_approx
    return approx
