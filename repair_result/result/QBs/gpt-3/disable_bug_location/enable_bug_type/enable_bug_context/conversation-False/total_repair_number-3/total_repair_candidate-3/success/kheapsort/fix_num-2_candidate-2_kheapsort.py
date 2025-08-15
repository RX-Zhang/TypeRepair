def kheapsort(arr, k):
    import heapq
    if k == 0:
        # If k is zero, just yield sorted elements (or original if no heap needed)
        for x in sorted(arr):
            yield x
        return

    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        if x > heap[0]:
            yield heapq.heapreplace(heap, x)
        else:
            yield x
    while heap:
        yield heapq.heappop(heap)
