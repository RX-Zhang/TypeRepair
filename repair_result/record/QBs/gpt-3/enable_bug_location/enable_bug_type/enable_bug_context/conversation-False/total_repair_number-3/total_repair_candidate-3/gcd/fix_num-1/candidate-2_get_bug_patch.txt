def gcd(a, b):
    if b < 0:
        raise ValueError("b must be non-negative")
    if b == 0:
        return a
    else:
        return gcd(a % b, b)
