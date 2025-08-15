def minimum_spanning_tree(weight_by_edge):
    group_by_node = {}
    mst_edges = set()
    for edge in sorted(weight_by_edge, key=weight_by_edge.__getitem__):
        u, v = edge
        if group_by_node.setdefault(u, {u}) != group_by_node.setdefault(v, {v}):
            mst_edges.add(edge)
            # Merge the sets safely by using a copy of group_by_node[v]
            v_group = group_by_node[v]
            u_group = group_by_node[u]
            u_group.update(v_group)
            for node in list(v_group):
                group_by_node[node] = u_group
    return mst_edges
