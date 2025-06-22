def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        heapq.heappushpop(heap, x)
    result = []
    while heap:
        result.append(heapq.heappop(heap))
    return result
