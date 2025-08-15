def max_sublist_sum(arr):
    if not arr:
        return 0
    max_ending_here = arr[0]
    max_so_far = arr[0]
    for x in arr[1:]:
        max_ending_here = max(x, max_ending_here + x)
        max_so_far = max(max_so_far, max_ending_here)
    return max_so_far if max_so_far > 0 else 0
