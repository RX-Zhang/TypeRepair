def topological_ordering(nodes):
    ordered_nodes = []
    no_incoming = [node for node in nodes if not node.incoming_nodes]

    while no_incoming:
        node = no_incoming.pop()
        ordered_nodes.append(node)
        for nextnode in node.outgoing_nodes:
            nextnode.incoming_nodes.remove(node)
            if not nextnode.incoming_nodes:
                no_incoming.append(nextnode)
    if len(ordered_nodes) != len(nodes):
        raise Exception("Graph has at least one cycle")
    return ordered_nodes
