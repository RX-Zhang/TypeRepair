def minimum_spanning_tree(weight_by_edge):
    group_by_node = {}
    mst_edges = set()
    for edge in sorted(weight_by_edge, key=weight_by_edge.__getitem__):
        u, v = edge
        if group_by_node.setdefault(u, {u}) != group_by_node.setdefault(v, {v}):
            mst_edges.add(edge)
            # Save references to the groups before merging
            group_u = group_by_node[u]
            group_v = group_by_node[v]
            # Merge sets
            group_u.update(group_v)
            for node in list(group_v):
                group_by_node[node] = group_u
    return mst_edges
