def gcd(a, b):
    if b == 0:
        return a
    elif a < 0 or b < 0:
        raise ValueError("Both a and b must be non-negative integers.")
    else:
        return gcd(a % b, b)
