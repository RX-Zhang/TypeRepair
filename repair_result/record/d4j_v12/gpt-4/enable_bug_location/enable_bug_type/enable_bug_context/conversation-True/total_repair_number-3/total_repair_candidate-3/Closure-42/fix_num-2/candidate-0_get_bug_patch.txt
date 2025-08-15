Node processForInLoop(ForInLoop loopNode) {

    // Use the helper methods to transform the iterator, iterated object, and the body block
    return newNode(
        Token.FOR,
        transform(loopNode.getIterator()),
        transform(loopNode.getIteratedObject()),
        transformBlock(loopNode.getBody()));
}
