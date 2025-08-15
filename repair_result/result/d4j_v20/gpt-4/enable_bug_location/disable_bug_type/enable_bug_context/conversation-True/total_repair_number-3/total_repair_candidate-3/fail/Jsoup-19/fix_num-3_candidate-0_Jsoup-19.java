private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    String value = attr.getValue();
    if (!preserveRelativeLinks) {
        String abs = el.absUrl(attr.getKey());
        if (!abs.isEmpty()) {
            attr.setValue(abs);
            value = abs;
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
