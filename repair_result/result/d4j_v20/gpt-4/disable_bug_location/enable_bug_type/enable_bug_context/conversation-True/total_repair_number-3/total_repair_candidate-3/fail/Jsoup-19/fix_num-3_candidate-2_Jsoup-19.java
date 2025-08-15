private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = attr.getValue();
    if (!preserveRelativeLinks) {
        String absValue = el.absUrl(attr.getKey());
        if (absValue.length() > 0)
            attr.setValue(absValue);
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
