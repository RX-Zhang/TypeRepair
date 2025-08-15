def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:  # Start from k to avoid pushing the first k elements again
        yield heapq.heappushpop(heap, x)
    while heap:
        yield heapq.heappop(heap)
