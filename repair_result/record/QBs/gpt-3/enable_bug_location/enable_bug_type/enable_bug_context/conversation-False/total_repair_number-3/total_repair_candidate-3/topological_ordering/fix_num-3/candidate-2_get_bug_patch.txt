def topological_ordering(nodes):
    ordered_nodes = []
    incoming_count = {node: len(node.incoming_nodes) for node in nodes}
    zero_incoming = [node for node in nodes if incoming_count[node] == 0]

    while zero_incoming:
        node = zero_incoming.pop()
        ordered_nodes.append(node)
        
        for nextnode in node.outgoing_nodes:
            incoming_count[nextnode] -= 1
            if incoming_count[nextnode] == 0:
                zero_incoming.append(nextnode)

    if len(ordered_nodes) != len(nodes):
        raise ValueError("Graph has at least one cycle.")

    return ordered_nodes
