def kheapsort(arr, k):
    import heapq
    if k == 0:
        # If k=0, just yield all elements sorted
        yield from sorted(arr)
        return

    heap = arr[:k+1]
    heapq.heapify(heap)
    for x in arr[k+1:]:
        yield heapq.heappop(heap)
        heapq.heappush(heap, x)
    while heap:
        yield heapq.heappop(heap)
