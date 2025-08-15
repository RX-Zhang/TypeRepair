def lis(arr):
    ends = {}
    longest = 0
    for i, val in enumerate(arr):
        prefix_lengths = [j for j in range(1, longest + 1) if arr[ends[j]] < val]
        length = max(prefix_lengths) if prefix_lengths else 0
        # Fix the condition to handle key errors and comparisons properly
        if length == longest:
            ends[length + 1] = i
            longest = length + 1
        elif length + 1 in ends and val < arr[ends[length + 1]]:
            ends[length + 1] = i
        elif length + 1 not in ends:
            ends[length + 1] = i
            if longest < length + 1:
                longest = length + 1
    return longest
