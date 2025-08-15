def kheapsort(arr, k):
    import heapq
    if k == 0:
        # no sorting needed if k is 0, just yield original
        for x in arr:
            yield x
        return

    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        yield heapq.heappushpop(heap, x)
    while heap:
        yield heapq.heappop(heap)
