def bitcount(n):
    count = 0
    while n > 0:
        n &= (n - 1)  # Using bitwise AND to count set bits
        count += 1
    return count
