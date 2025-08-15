def subsequences(a, b, k):
    if k == 0:
        return [[]]  # Return a list containing an empty list for k == 0
    if a > b or k < 0:  # Check for invalid ranges or negative k
        return []
    ret = []
    for i in range(a, b + 1 - k + 1):  # Adjust the range to include valid combinations
        ret.extend(
            [[i] + rest for rest in subsequences(i + 1, b, k - 1)]
        )
    return ret
