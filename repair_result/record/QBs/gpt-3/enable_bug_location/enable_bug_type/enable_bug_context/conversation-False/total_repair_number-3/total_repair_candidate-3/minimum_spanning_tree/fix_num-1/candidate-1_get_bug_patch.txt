def minimum_spanning_tree(weight_by_edge):
    group_by_node = {}
    mst_edges = set()
    for edge in sorted(weight_by_edge, key=weight_by_edge.__getitem__):
        u, v = edge
        if group_by_node.setdefault(u, {u}) != group_by_node.setdefault(v, {v}):
            mst_edges.add(edge)
            group_by_node[u].update(group_by_node[v])
            # Create a copy of the nodes to avoid modifying the set during iteration
            nodes_to_update = list(group_by_node[v])
            for node in nodes_to_update:
                group_by_node[node].update(group_by_node[u])
    return mst_edges
