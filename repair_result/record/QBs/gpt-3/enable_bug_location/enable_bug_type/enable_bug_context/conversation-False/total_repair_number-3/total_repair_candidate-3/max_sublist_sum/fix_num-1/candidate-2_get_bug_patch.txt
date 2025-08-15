def max_sublist_sum(arr):
    max_ending_here = 0
    max_so_far = float('-inf')  # Initialize to negative infinity to handle cases with all negative numbers
    for x in arr:
        max_ending_here = max(0, max_ending_here + x)  # Reset to 0 if it becomes negative
        max_so_far = max(max_so_far, max_ending_here)
    return max_so_far
