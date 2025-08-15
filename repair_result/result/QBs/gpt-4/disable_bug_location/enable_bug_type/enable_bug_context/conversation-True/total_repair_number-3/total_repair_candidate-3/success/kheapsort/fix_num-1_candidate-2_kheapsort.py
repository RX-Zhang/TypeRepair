def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        if x > heap[0]:
            heapq.heapreplace(heap, x)
    sorted_part = sorted(heap)
    rest = sorted(arr[:k]) + sorted(arr[k:])
    return sorted(arr)
