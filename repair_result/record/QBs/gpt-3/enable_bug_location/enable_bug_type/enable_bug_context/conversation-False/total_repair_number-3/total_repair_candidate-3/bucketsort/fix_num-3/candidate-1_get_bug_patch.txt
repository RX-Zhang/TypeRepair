def bucketsort(arr, k):
    counts = [0] * k
    for x in arr:
        if x < k:  # Ensure we only count elements that are less than k
            counts[x] += 1
    sorted_arr = []
    for i, count in enumerate(counts):  # Iterate over counts instead of arr
        sorted_arr.extend([i] * count)
    return sorted_arr
