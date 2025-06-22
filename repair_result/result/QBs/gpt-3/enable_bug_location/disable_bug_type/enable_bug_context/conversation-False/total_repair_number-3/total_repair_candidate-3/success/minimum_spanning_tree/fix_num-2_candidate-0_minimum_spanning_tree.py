def minimum_spanning_tree(weight_by_edge):
    group_by_node = {}
    mst_edges = set()
    for edge in sorted(weight_by_edge, key=weight_by_edge.__getitem__):
        u, v = edge
        set_u = group_by_node.setdefault(u, {u})
        set_v = group_by_node.setdefault(v, {v})
        if set_u != set_v:
            mst_edges.add(edge)
            union = set_u | set_v
            for node in union:
                group_by_node[node] = union
    return mst_edges
