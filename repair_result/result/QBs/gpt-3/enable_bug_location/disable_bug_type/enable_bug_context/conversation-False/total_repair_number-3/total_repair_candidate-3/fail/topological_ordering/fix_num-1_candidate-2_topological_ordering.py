def topological_ordering(nodes):
    ordered_nodes = []
    no_incoming = [node for node in nodes if not node.incoming_nodes]
    # Copy the incoming_nodes lists so modifications don't affect the original graph
    incoming_count = {node: len(node.incoming_nodes) if node.incoming_nodes else 0 for node in nodes}

    while no_incoming:
        node = no_incoming.pop(0)
        ordered_nodes.append(node)
        for nextnode in node.outgoing_nodes:
            incoming_count[nextnode] -= 1
            if incoming_count[nextnode] == 0:
                no_incoming.append(nextnode)
    if len(ordered_nodes) != len(nodes):
        raise Exception("Graph has at least one cycle, topological ordering not possible")
    return ordered_nodes
