def topological_ordering(nodes):
    ordered_nodes = [node for node in nodes if not node.incoming_nodes]
    i = 0
    while i < len(ordered_nodes):
        node = ordered_nodes[i]
        for nextnode in node.outgoing_nodes:
            # Check if all incoming nodes of nextnode are in ordered_nodes
            if all(pred in ordered_nodes for pred in nextnode.incoming_nodes) and nextnode not in ordered_nodes:
                ordered_nodes.append(nextnode)
        i += 1
    return ordered_nodes
