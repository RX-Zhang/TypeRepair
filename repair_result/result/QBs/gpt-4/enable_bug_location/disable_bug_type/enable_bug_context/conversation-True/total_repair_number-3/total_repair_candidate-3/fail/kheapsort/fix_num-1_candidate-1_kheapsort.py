def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        if x > heap[0]:
            heapq.heapreplace(heap, x)
    sorted_heap = []
    while heap:
        sorted_heap.append(heapq.heappop(heap))
    return sorted_heap
