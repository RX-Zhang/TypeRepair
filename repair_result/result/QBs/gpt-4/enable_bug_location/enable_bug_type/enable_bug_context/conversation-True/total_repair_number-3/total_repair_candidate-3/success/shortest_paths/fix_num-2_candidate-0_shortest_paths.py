def shortest_paths(source, weight_by_edge):
    weight_by_node = {
        v: float('inf') for u, v in weight_by_edge
    }
    # Include source node in case it is not a target in any edge
    weight_by_node.setdefault(source, 0)
    weight_by_node[source] = 0

    nodes = set()
    for u, v in weight_by_edge:
        nodes.add(u)
        nodes.add(v)
    for i in range(len(nodes) - 1):
        for (u, v), weight in weight_by_edge.items():
            if weight_by_node[u] + weight < weight_by_node[v]:
                weight_by_node[v] = weight_by_node[u] + weight
    return weight_by_node
