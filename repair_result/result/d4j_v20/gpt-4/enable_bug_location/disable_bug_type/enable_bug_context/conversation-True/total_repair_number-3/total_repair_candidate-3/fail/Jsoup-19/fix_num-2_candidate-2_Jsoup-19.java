private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    String value = attr.getValue();
    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            if (!preserveRelativeLinks) {
                String abs = el.absUrl(attr.getKey());
                attr.setValue(abs);
            }
            return true;
        }
    }
    return false;
}
