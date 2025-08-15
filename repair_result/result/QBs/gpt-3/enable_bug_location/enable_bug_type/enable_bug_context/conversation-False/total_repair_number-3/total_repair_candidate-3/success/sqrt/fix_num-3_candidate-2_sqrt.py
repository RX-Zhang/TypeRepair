def sqrt(x, epsilon):
    if x < 0:
        raise ValueError("Cannot compute square root of a negative number.")
    if epsilon <= 0:
        raise ValueError("Epsilon must be positive.")
    
    approx = x / 2
    # Using a fixed number of iterations for optimization
    for _ in range(100):
        last_approx = approx
        approx = 0.5 * (approx + x / approx)
        if abs(last_approx - approx) < epsilon:
            break
    return approx
