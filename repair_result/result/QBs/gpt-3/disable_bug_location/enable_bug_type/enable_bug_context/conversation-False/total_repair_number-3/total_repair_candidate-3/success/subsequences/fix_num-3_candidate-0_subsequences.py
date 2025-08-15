def subsequences(a, b, k):
    if k == 0:
        return [[]]
    if b - a + 1 < k:
        return []
    ret = []
    for i in range(a, b + 1 - k + 1):
        ret.extend(
            [i] + rest for rest in subsequences(i + 1, b, k - 1)
        )
    return ret
