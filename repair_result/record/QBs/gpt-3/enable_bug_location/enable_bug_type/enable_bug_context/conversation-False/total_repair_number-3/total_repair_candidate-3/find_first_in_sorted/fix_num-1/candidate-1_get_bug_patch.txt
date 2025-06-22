def find_first_in_sorted(arr, x):
    lo = 0
    hi = len(arr) - 1  # Change to len(arr) - 1 to avoid index out of range
    while lo <= hi:
        mid = (lo + hi) // 2
        if mid < len(arr) and x == arr[mid] and (mid == 0 or x != arr[mid - 1]):
            return mid
        elif mid < len(arr) and x <= arr[mid]:  # Add boundary check for mid
            hi = mid - 1  # Change to mid - 1 to avoid infinite loop
        else:
            lo = mid + 1
    return -1
