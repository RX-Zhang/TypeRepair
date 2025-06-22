def kheapsort(arr, k):
    import heapq
    if k == 0:
        # If k is 0, just yield the array as is
        for x in arr:
            yield x
        return
    heap = arr[:k+1]
    heapq.heapify(heap)
    for x in arr[k+1:]:
        yield heapq.heappop(heap)
        heapq.heappush(heap, x)
    while heap:
        yield heapq.heappop(heap)
