def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:
        if x > heap[0]:
            heapq.heapreplace(heap, x)
    heap.sort()
    for x in heap:
        yield x
