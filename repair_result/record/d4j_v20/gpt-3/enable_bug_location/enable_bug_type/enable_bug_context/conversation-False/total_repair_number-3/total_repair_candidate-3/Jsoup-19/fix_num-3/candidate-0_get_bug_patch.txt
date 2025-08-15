private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = el.absUrl(attr.getKey());
    if (value.isEmpty()) {
        // no base URI to resolve relative URL, so value is empty
        if (!preserveRelativeLinks) {
            attr.setValue(value);
        }
        return preserveRelativeLinks; // if we preserve relative links, keep attribute; else remove
    }
    if (!preserveRelativeLinks) {
        attr.setValue(value);
    }
    String lowerVal = value.toLowerCase();
    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (lowerVal.startsWith(prot)) {
            return true;
        }
    }
    return false;
}
