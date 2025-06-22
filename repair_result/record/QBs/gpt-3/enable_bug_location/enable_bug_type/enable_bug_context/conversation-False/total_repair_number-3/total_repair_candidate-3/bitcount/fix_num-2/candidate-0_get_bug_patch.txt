def bitcount(n):
    count = 0
    while n > 0:  # Ensure n is greater than 0 to avoid infinite loop
        n &= (n - 1)  # Use bitwise AND to clear the lowest set bit
        count += 1
    return count
