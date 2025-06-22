def find_first_in_sorted(arr, x):
    lo = 0
    hi = len(arr) - 1  # Changed to len(arr) - 1 to avoid IndexError
    while lo <= hi:
        mid = (lo + hi) // 2
        if mid < 0 or mid >= len(arr):  # Boundary check for mid
            break
        if x == arr[mid] and (mid == 0 or x != arr[mid - 1]):
            return mid
        elif x < arr[mid]:  # Changed from x <= arr[mid] to x < arr[mid]
            hi = mid - 1  # Changed to mid - 1
        else:
            lo = mid + 1
    return -1
