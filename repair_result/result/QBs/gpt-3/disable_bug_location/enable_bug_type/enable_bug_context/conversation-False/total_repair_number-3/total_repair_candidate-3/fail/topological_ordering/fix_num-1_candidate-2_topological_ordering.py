def topological_ordering(nodes):
    ordered_nodes = []
    no_incoming = [node for node in nodes if not node.incoming_nodes]

    while no_incoming:
        node = no_incoming.pop(0)
        ordered_nodes.append(node)
        for nextnode in node.outgoing_nodes:
            # Remove this node from the incoming_nodes of nextnode
            if node in nextnode.incoming_nodes:
                nextnode.incoming_nodes.remove(node)
            # If no more incoming edges, add to no_incoming
            if not nextnode.incoming_nodes:
                no_incoming.append(nextnode)

    if len(ordered_nodes) != len(nodes):
        raise Exception("Graph has at least one cycle or disconnected nodes")

    return ordered_nodes
