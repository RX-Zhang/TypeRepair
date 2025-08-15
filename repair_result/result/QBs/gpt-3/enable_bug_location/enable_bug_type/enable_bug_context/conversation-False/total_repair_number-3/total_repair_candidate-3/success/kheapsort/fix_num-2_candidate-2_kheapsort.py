def kheapsort(arr, k):
    import heapq
    # Ensure we only consider the first k elements or the entire array if k is greater
    heap = arr[:k]  
    heapq.heapify(heap)
    for x in arr[k:]:  # Start from the k-th element
        yield heapq.heappushpop(heap, x)
    while heap:
        yield heapq.heappop(heap)
