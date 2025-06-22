def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        if x > heap[0]:
            heapq.heappushpop(heap, x)
    result = []
    while heap:
        result.append(heapq.heappop(heap))
    result.sort()
    for val in result:
        yield val
