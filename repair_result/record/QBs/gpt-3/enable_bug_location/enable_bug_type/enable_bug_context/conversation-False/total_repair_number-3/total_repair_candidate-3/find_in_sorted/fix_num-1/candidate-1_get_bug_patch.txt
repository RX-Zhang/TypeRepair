def find_in_sorted(arr, x):
    def binsearch(start, end):
        if start >= end:  # Changed from start == end to start >= end for boundary check
            return -1
        mid = start + (end - start) // 2
        if x < arr[mid]:
            return binsearch(start, mid)
        elif x > arr[mid]:
            return binsearch(mid + 1, end)  # Changed from mid to mid + 1 to avoid infinite recursion
        else:
            return mid
    return binsearch(0, len(arr))
