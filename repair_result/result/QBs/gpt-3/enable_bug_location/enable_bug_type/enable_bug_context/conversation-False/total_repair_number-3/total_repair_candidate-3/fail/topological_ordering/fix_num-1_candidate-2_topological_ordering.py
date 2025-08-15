def topological_ordering(nodes):
    ordered_nodes = []
    # Add nodes with no incoming edges to the ordered list
    no_incoming = [node for node in nodes if not node.incoming_nodes]
    
    while no_incoming:
        node = no_incoming.pop()
        ordered_nodes.append(node)

        # For each outgoing node, reduce the incoming count
        for nextnode in node.outgoing_nodes:
            nextnode.incoming_nodes.remove(node)
            # If nextnode has no more incoming edges, add it to no_incoming
            if not nextnode.incoming_nodes:
                no_incoming.append(nextnode)

    return ordered_nodes
