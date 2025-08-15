def kheapsort(arr, k):
    import heapq
    if k == 0:
        # If k is 0, just yield sorted arr
        for x in sorted(arr):
            yield x
        return

    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        yield heapq.heappushpop(heap, x)
    while heap:
        yield heapq.heappop(heap)
