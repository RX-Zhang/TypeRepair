def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:  # Start from the k-th element
        yield heapq.heappushpop(heap, x)
    while heap:
        yield heapq.heappop(heap)
