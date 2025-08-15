def get_factors(n):
    if n <= 1:  # Corrected condition to handle n = 1 and all numbers less than 1
        return [] if n == 1 else [n]  # Return empty list for 1, return n for numbers <= 1
    for i in range(2, int(n ** 0.5) + 1):
        while n % i == 0:  # Changed if to while to account for multiple factors
            return [i] + get_factors(n // i)
    return [n]  # Return n if it is a prime number
