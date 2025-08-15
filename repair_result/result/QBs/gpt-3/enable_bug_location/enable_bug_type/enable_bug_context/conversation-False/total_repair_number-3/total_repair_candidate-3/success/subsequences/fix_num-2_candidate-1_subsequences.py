def subsequences(a, b, k):
    if k == 0:
        return [[]]  # Return a list containing an empty list
    if a > b or k < 0:  # Check for invalid range or negative k
        return []
    ret = []
    for i in range(a, b + 1 - k + 1):  # Adjusted to include b in the range
        ret.extend(
            [[i] + rest for rest in subsequences(i + 1, b, k - 1)]
        )
    return ret
