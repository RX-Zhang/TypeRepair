def topological_ordering(nodes):
    ordered_nodes = []
    zero_incoming = {node for node in nodes if not node.incoming_nodes}
    
    while zero_incoming:
        node = zero_incoming.pop()
        ordered_nodes.append(node)
        
        for nextnode in node.outgoing_nodes:
            nextnode.incoming_nodes.remove(node)
            if not nextnode.incoming_nodes:
                zero_incoming.add(nextnode)
    
    return ordered_nodes
