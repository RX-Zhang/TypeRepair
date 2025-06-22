def shortest_paths(source, weight_by_edge):
    weight_by_node = {
        v: float('inf') for u, v in weight_by_edge
    }
    weight_by_node[source] = 0
    nodes = set()
    for u, v in weight_by_edge:
        nodes.add(u)
        nodes.add(v)
    if source not in nodes:
        return weight_by_node
    for _ in range(len(nodes) - 1):
        for (u, v), weight in weight_by_edge.items():
            if weight_by_node[u] + weight < weight_by_node[v]:
                weight_by_node[v] = weight_by_node[u] + weight
    return weight_by_node
