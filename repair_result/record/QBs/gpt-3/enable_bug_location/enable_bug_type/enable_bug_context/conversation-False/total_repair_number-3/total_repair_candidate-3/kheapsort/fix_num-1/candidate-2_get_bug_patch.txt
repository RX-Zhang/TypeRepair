def kheapsort(arr, k):
    import heapq
    heap = arr[:k]
    heapq.heapify(heap)
    for x in arr[k:]:  # Start iterating from index k instead of including all elements
        heapq.heappush(heap, x)  # Push the new element onto the heap
        if len(heap) > k:  # Ensure that we only keep k elements in the heap
            heapq.heappop(heap)  # Pop the smallest element if heap exceeds size k
    while heap:
        yield heapq.heappop(heap)
