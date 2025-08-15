from collections import deque

def topological_ordering(nodes):
    in_degree = {node: len(node.incoming_nodes) for node in nodes}
    queue = deque([node for node in nodes if in_degree[node] == 0])
    ordered_nodes = []

    while queue:
        node = queue.popleft()
        ordered_nodes.append(node)
        for nextnode in node.outgoing_nodes:
            in_degree[nextnode] -= 1
            if in_degree[nextnode] == 0:
                queue.append(nextnode)

    if len(ordered_nodes) != len(nodes):
        raise Exception("Graph has at least one cycle.")

    return ordered_nodes
