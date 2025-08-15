def kheapsort(arr, k):
    import heapq
    if k == 0:
        # If k is 0, just yield the sorted array
        for x in sorted(arr):
            yield x
        return
    heap = arr[:k+1]
    heapq.heapify(heap)
    n = len(arr)
    for i in range(k+1, n):
        yield heapq.heappushpop(heap, arr[i])
    while heap:
        yield heapq.heappop(heap)
