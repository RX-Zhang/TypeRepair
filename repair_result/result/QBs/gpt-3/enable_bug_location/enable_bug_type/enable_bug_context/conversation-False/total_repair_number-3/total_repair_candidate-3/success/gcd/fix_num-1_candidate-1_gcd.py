def gcd(a, b):
    if b == 0:
        return a
    if a < 0 or b < 0:  # Boundary check for negative inputs
        raise ValueError("Inputs must be non-negative integers.")
    return gcd(b, a % b)  # Corrected the order of parameters for the recursive call
