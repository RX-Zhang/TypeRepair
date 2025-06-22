private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String origVal = attr.getValue();
    String value = el.absUrl(attr.getKey());
    if (!preserveRelativeLinks) {
        attr.setValue(value);
    }

    // If absUrl returned an empty string, use the original value for protocol check (relative URLs)
    if (value.isEmpty()) {
        value = origVal;
    }

    for (Protocol protocol : protocols) {
        String prot = protocol.toString().toLowerCase() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
