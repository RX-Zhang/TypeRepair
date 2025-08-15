def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:  # Start from index k to ensure we are pushing only new elements
        yield heapq.heappushpop(heap, x)
    while heap:
        yield heapq.heappop(heap)
