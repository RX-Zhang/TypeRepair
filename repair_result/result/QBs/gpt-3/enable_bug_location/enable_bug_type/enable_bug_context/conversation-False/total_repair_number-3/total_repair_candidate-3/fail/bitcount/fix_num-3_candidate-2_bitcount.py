def bitcount(n):
    if n < 0:  # Boundary check for negative numbers
        raise ValueError("Input must be a non-negative integer.")

    count = 0
    while n:
        n ^= n - 1
        count += 1
    return count
