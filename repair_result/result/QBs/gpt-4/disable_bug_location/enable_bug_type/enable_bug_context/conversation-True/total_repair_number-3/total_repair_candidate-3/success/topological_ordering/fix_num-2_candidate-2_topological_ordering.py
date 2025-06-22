def topological_ordering(nodes):
    ordered_nodes = [node for node in nodes if not node.incoming_nodes]
    i = 0
    while i < len(ordered_nodes):
        node = ordered_nodes[i]
        for nextnode in node.outgoing_nodes:
            if nextnode not in ordered_nodes and all(parent in ordered_nodes for parent in nextnode.incoming_nodes):
                ordered_nodes.append(nextnode)
        i += 1
    return ordered_nodes
