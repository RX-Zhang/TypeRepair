def subsequences(a, b, k):
    if k == 0:
        return [[]]  # Return a list containing an empty list when k is 0
    if a > b or b - a + 1 < k:  # Check if the range is valid
        return []  # Return an empty list if the range is invalid
    ret = []
    for i in range(a, b + 1 - k + 1):  # Adjust the range to include the last valid starting point
        ret.extend(
            [[i] + rest for rest in subsequences(i + 1, b, k - 1)]
        )
    return ret
