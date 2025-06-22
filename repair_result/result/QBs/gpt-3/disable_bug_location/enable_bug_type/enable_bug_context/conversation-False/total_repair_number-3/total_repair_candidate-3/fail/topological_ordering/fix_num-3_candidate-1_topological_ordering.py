def topological_ordering(nodes):
    ordered_nodes = []
    no_incoming = [node for node in nodes if not node.incoming_nodes]

    while no_incoming:
        node = no_incoming.pop(0)
        ordered_nodes.append(node)

        for nextnode in node.outgoing_nodes:
            # Remove edge from node to nextnode by removing node from nextnode.incoming_nodes
            nextnode.incoming_nodes.remove(node)
            if not nextnode.incoming_nodes:
                no_incoming.append(nextnode)

    if len(ordered_nodes) != len(nodes):
        raise ValueError("Graph has at least one cycle")

    return ordered_nodes
