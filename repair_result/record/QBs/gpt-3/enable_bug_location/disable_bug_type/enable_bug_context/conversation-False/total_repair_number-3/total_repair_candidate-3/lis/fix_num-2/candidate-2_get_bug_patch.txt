def lis(arr):
    if not arr:
        return 0
    ends = []
    for val in arr:
        # Binary search for the insertion position of val in ends
        left, right = 0, len(ends)
        while left < right:
            mid = (left + right) // 2
            if ends[mid] < val:
                left = mid + 1
            else:
                right = mid
        if left == len(ends):
            ends.append(val)
        else:
            ends[left] = val
    return len(ends)
