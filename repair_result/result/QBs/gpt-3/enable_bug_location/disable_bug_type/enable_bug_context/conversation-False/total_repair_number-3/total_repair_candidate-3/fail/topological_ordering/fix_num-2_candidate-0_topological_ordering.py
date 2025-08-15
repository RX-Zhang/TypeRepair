def topological_ordering(nodes):
    ordered_nodes = []
    no_incoming = [node for node in nodes if not node.incoming_nodes]
    
    while no_incoming:
        node = no_incoming.pop()
        ordered_nodes.append(node)
        
        for m in node.outgoing_nodes:
            m.incoming_nodes.remove(node)
            if not m.incoming_nodes:
                no_incoming.append(m)
    
    if len(ordered_nodes) != len(nodes):
        raise Exception("Graph has at least one cycle")
    
    return ordered_nodes
