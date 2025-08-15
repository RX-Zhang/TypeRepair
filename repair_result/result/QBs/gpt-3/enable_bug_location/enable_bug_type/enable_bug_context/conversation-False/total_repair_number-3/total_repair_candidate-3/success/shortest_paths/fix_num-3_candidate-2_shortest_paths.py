def shortest_paths(source, weight_by_edge):
    weight_by_node = {
        u: float('inf') for u in {u for u, v in weight_by_edge} | {v for u, v in weight_by_edge}
    }
    weight_by_node[source] = 0
    for i in range(len(weight_by_node) - 1):
        for (u, v), weight in weight_by_edge.items():
            if weight_by_node[u] + weight < weight_by_node[v]:  # Corrected the logical expression here
                weight_by_node[v] = weight_by_node[u] + weight  # Update weight_by_node[v] instead
    return weight_by_node
