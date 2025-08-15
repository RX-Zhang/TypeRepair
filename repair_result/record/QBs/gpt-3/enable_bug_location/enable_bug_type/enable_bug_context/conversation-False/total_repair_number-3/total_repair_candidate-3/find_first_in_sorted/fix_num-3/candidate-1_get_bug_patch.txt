def find_first_in_sorted(arr, x):
    lo = 0
    hi = len(arr) - 1  # Change hi to len(arr) - 1
    while lo <= hi:
        mid = (lo + hi) // 2
        if mid < 0 or mid >= len(arr):  # Boundary check
            return -1
        if x == arr[mid] and (mid == 0 or x != arr[mid - 1]):
            return mid
        elif x < arr[mid]:  # Change x <= arr[mid] to x < arr[mid]
            hi = mid - 1  # Change hi to mid - 1
        else:
            lo = mid + 1
    return -1
