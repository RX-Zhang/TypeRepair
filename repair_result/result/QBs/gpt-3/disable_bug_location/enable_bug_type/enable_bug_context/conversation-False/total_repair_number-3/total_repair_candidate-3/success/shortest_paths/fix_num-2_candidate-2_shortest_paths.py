def shortest_paths(source, weight_by_edge):
    # Initialize distances to infinity for all nodes encountered in edges (both sources and targets)
    nodes = set()
    for u, v in weight_by_edge:
        nodes.add(u)
        nodes.add(v)
    weight_by_node = {node: float('inf') for node in nodes}
    
    weight_by_node[source] = 0
    
    for _ in range(len(nodes) - 1):
        updated = False
        for (u, v), weight in weight_by_edge.items():
            if weight_by_node[u] + weight < weight_by_node[v]:
                weight_by_node[v] = weight_by_node[u] + weight
                updated = True
        if not updated:
            break  # Early exit if no changes
    
    return weight_by_node
