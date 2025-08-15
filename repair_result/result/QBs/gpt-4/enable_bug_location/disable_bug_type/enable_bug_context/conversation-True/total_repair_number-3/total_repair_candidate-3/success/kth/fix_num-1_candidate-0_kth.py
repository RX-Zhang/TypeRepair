def kth(arr, k):
    if not arr:
        return None  # or raise an error
    pivot = arr[0]
    below = [x for x in arr if x < pivot]
    above = [x for x in arr if x > pivot]
    num_less = len(below)
    num_equal = len(arr) - len(below) - len(above)
    if k < num_less:
        return kth(below, k)
    elif k < num_less + num_equal:
        return pivot
    else:
        return kth(above, k - num_less - num_equal)
