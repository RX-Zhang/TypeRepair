def max_sublist_sum(arr):
    max_ending_here = arr[0]  # Start with the first element
    max_so_far = arr[0]  # Start with the first element as well
    for x in arr[1:]:  # Loop from the second element
        max_ending_here = max(x, max_ending_here + x)  # Reset if the current element is greater
        max_so_far = max(max_so_far, max_ending_here)
    return max_so_far
