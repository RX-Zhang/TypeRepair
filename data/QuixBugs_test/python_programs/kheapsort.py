def kheapsort(arr, k):
    import heapq
    if k == 0:
        # If k is 0, just return the array sorted
        for x in sorted(arr):
            yield x
        return

    heap = arr[:k]
    heapq.heapify(heap)  # min-heap of first k elements
    # Process remaining elements
    for x in arr[k:]:
        if x > heap[0]:
            heapq.heapreplace(heap, x)
        else:
            yield x
    # Extract remaining from heap in sorted order
    res = sorted(heap)
    for val in res:
        yield val
