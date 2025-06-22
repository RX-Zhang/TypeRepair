def minimum_spanning_tree(weight_by_edge):
    group_by_node = {}
    mst_edges = set()
    for edge in sorted(weight_by_edge, key=weight_by_edge.__getitem__):
        u, v = edge
        if group_by_node.setdefault(u, {u}) != group_by_node.setdefault(v, {v}):
            mst_edges.add(edge)
            u_group = group_by_node[u]
            v_group = group_by_node[v]
            merged_group = u_group | v_group
            for node in merged_group:
                group_by_node[node] = merged_group
    return mst_edges
