private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    String value = attr.getValue();
    if (!preserveRelativeLinks) {
        String absValue = el.absUrl(attr.getKey());
        if (!absValue.isEmpty()) {
            attr.setValue(absValue);
            value = absValue;
        }
    }
    
    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
