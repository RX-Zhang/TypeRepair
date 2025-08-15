def lis(arr):
    ends = {}
    longest = 0
    for i, val in enumerate(arr):
        prefix_lengths = [j for j in range(1, longest + 1) if arr[ends[j]] < val]
        length = max(prefix_lengths) if prefix_lengths else 0
        # Fix condition: handle case when length+1 not in ends or val < arr[ends[length + 1]]
        if length == longest or (length + 1 not in ends) or val < arr[ends[length + 1]]:
            ends[length + 1] = i
            if length + 1 > longest:
                longest = length + 1
    return longest
