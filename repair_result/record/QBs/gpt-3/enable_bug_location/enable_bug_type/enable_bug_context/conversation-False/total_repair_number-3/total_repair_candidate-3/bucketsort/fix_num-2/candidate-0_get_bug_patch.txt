def bucketsort(arr, k):
    counts = [0] * k
    for x in arr:
        if 0 <= x < k:  # Ensure x is within the valid range
            counts[x] += 1
    sorted_arr = []
    for i, count in enumerate(counts):  # Change arr to counts
        sorted_arr.extend([i] * count)
    return sorted_arr
