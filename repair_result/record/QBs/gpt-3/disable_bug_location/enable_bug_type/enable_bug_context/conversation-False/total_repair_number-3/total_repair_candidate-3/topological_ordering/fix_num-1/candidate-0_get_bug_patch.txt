def topological_ordering(nodes):
    ordered_nodes = []
    nodes_with_no_incoming = [node for node in nodes if not node.incoming_nodes]
    while nodes_with_no_incoming:
        node = nodes_with_no_incoming.pop(0)
        ordered_nodes.append(node)
        for nextnode in node.outgoing_nodes:
            nextnode.incoming_nodes.remove(node)
            if not nextnode.incoming_nodes:
                nodes_with_no_incoming.append(nextnode)
    if len(ordered_nodes) != len(nodes):
        raise Exception("Graph has at least one cycle")
    return ordered_nodes
