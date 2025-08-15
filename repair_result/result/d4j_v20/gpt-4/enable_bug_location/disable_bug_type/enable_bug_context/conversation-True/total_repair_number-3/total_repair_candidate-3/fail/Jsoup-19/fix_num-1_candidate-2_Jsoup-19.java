private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    String value = attr.getValue();
    if (!preserveRelativeLinks) {
        value = el.absUrl(attr.getKey());
        attr.setValue(value);
    }
    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
