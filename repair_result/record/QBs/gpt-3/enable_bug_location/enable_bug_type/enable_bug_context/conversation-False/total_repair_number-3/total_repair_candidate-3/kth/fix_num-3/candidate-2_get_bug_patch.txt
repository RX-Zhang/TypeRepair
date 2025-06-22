def kth(arr, k):
    if k < 0 or k >= len(arr):
        raise IndexError("k is out of bounds")
    if not arr:
        raise IndexError("Array is empty")
    
    pivot = arr[0]
    below = [x for x in arr if x < pivot]
    above = [x for x in arr if x > pivot]
    num_less = len(below)
    num_lessoreq = len(arr) - len(above)
    
    if k < num_less:
        return kth(below, k)
    elif k >= num_lessoreq:
        return kth(above, k - num_lessoreq + len(above))
    else:
        return pivot
