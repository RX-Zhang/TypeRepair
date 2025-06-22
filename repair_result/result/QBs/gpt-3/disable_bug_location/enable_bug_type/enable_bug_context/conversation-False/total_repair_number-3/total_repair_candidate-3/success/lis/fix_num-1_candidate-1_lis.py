def lis(arr):
    if not arr:
        return 0
    ends = {}
    longest = 0
    for i, val in enumerate(arr):
        prefix_lengths = [j for j in range(1, longest + 1) if arr[ends[j]] < val]
        length = max(prefix_lengths) if prefix_lengths else 0
        # fix: update ends[length+1] only if it doesn't exist or val is smaller than current end
        if length + 1 not in ends or val < arr[ends[length + 1]]:
            ends[length + 1] = i
            if length + 1 > longest:
                longest = length + 1
    return longest
