private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    String value = attr.getValue();
    if (!preserveRelativeLinks) {
        String absUrl = el.absUrl(attr.getKey());
        if (!absUrl.isEmpty())
            attr.setValue(absUrl);
        value = attr.getValue();
    }
    
    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
