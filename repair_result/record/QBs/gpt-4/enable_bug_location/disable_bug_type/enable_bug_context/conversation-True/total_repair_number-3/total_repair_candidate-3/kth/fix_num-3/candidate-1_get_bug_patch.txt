def kth(arr, k):
    if not arr:
        return None
    pivot = arr[0]
    below = [x for x in arr if x < pivot]
    equal = [x for x in arr if x == pivot]
    above = [x for x in arr if x > pivot]

    if k < len(below):
        return kth(below, k)
    elif k < len(below) + len(equal):
        return pivot
    else:
        return kth(above, k - len(below) - len(equal))
