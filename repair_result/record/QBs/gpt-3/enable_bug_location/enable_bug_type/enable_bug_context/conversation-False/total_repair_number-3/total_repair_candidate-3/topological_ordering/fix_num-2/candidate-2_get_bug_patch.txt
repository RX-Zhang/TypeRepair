def topological_ordering(nodes):
    ordered_nodes = []
    no_incoming = [node for node in nodes if not node.incoming_nodes]

    while no_incoming:
        current = no_incoming.pop()
        ordered_nodes.append(current)
        
        for nextnode in current.outgoing_nodes:
            nextnode.incoming_nodes.remove(current)
            if not nextnode.incoming_nodes:
                no_incoming.append(nextnode)

    return ordered_nodes
