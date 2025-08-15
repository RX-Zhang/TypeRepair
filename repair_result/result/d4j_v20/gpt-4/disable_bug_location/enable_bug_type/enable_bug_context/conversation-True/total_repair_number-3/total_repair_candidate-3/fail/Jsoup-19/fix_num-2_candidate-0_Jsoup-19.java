private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = attr.getValue();
    if (!preserveRelativeLinks) {
        String abs = el.absUrl(attr.getKey());
        // only set if absUrl is non-empty
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
