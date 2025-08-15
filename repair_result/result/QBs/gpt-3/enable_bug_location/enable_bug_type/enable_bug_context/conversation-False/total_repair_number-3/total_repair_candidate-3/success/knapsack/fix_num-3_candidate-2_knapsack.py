def knapsack(capacity, items):
    from collections import defaultdict
    memo = defaultdict(int)
    for i in range(1, len(items) + 1):
        weight, value = items[i - 1]
        for j in range(capacity, weight - 1, -1):  # Corrected loop to go backwards
            memo[j] = max(memo[j], value + memo[j - weight])  # Correctly uses memo[j] without i
    return memo[capacity]  # Return the maximum value for the given capacity
