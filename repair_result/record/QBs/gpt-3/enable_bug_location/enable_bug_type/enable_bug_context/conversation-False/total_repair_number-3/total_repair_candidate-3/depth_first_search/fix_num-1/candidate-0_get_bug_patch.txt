def depth_first_search(startnode, goalnode):
    nodesvisited = set()
    
    def search_from(node):
        if node in nodesvisited:
            return False
        elif node is goalnode:
            return True
        else:
            nodesvisited.add(node)  # Mark the node as visited to prevent cycles
            return any(
                search_from(nextnode) for nextnode in node.successors if nextnode is not None  # Boundary check
            )
    
    return search_from(startnode)
