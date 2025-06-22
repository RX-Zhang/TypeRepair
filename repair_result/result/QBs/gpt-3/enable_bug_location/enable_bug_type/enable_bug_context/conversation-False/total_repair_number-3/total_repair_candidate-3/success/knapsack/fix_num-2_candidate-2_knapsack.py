def knapsack(capacity, items):
    from collections import defaultdict
    memo = defaultdict(int)
    for i in range(1, len(items) + 1):
        weight, value = items[i - 1]
        for j in range(capacity, weight - 1, -1):  # Fix the loop to iterate backwards
            memo[j] = max(memo[j], value + memo[j - weight])  # Update the memoization dictionary directly
    return memo[capacity]  # Return the correct value for the given capacity
