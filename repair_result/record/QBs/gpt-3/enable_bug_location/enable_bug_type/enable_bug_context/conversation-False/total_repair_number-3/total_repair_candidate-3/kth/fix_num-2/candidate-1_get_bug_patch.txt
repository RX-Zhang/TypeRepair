def kth(arr, k):
    if k < 0 or k >= len(arr):  # Boundary check for k
        raise IndexError("k is out of bounds")
    if not arr:  # Check for empty array
        raise ValueError("Array is empty")
    
    pivot = arr[0]
    below = [x for x in arr if x < pivot]
    above = [x for x in arr if x > pivot]
    num_less = len(below)
    num_lessoreq = len(arr) - len(above)
    
    if k < num_less:
        return kth(below, k)
    elif k >= num_lessoreq:
        return kth(above, k - num_lessoreq + len(arr))  # Adjust k for the above list
    else:
        return pivot
