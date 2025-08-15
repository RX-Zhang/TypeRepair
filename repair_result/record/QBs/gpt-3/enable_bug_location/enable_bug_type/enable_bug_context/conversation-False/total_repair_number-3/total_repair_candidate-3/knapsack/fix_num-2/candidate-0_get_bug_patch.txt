def knapsack(capacity, items):
    from collections import defaultdict
    memo = defaultdict(int)
    for i in range(1, len(items) + 1):
        weight, value = items[i - 1]
        for j in range(capacity, weight - 1, -1):  # Change the range to go backwards
            memo[j] = max(memo[j], value + memo[j - weight])  # Update memo directly
    return memo[capacity]
