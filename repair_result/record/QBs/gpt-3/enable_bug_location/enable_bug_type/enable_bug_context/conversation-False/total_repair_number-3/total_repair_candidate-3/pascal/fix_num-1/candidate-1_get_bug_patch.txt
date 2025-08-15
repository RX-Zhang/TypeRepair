def pascal(n):
    if n <= 0:  # Boundary check for non-positive input
        return []
    rows = [[1]]
    for r in range(1, n):
        row = []
        for c in range(0, r + 1):  # Changed condition to r + 1
            upleft = rows[r - 1][c - 1] if c > 0 else 0
            upright = rows[r - 1][c] if c < len(rows[r - 1]) else 0  # Fixed boundary check
            row.append(upleft + upright)
        rows.append(row)
    return rows
