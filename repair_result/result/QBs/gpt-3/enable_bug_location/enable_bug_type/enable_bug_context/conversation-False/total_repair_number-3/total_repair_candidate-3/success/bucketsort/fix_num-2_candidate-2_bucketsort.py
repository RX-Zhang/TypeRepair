def bucketsort(arr, k):
    counts = [0] * (k + 1)  # Fix to avoid index out of range
    for x in arr:
        if x < len(counts):  # Ensure x is within valid range
            counts[x] += 1
    sorted_arr = []
    for i, count in enumerate(counts):  # Corrected to iterate over counts
        sorted_arr.extend([i] * count)
    return sorted_arr
