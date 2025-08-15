def get_factors(n):
    if n <= 1:  # Modified to handle n = 1 and n < 1
        return []
    factors = []  # Initialize a list to collect factors
    for i in range(2, int(n ** 0.5) + 1):
        while n % i == 0:  # Changed to while to collect all instances of factor
            factors.append(i)  # Append the factor to the list
            n //= i  # Reduce n
    if n > 1:  # If n is still greater than 1, it is a prime factor
        factors.append(n)
    return factors
