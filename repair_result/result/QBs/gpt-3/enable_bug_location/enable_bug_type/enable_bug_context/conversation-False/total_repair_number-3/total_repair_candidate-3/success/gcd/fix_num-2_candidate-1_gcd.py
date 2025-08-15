def gcd(a, b):
    if b == 0:
        return a
    if a < 0 or b < 0:  # Adding boundary check for negative values
        raise ValueError("Inputs must be non-negative integers")
    return gcd(b, a % b)  # Corrected the order of arguments for the recursive call
